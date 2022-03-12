package com.greedy0110.hagomandal.util

import android.content.ContentValues
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import android.view.View
import androidx.annotation.RequiresApi
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

private fun getRandomFileName(extension: String): String {
    return "beanbean-${System.currentTimeMillis()}.$extension"
}

// TODO: 이건 View 대상이고... 생각해보니 Compose 기반에선 어떻게 함? <- 이건 별도 프로젝트로 파서 동작하는지 알아보고 publish 하자.
suspend fun View.takeScreenShot(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
): Bitmap {
    val view = this
    return withContext(ioDispatcher) {
        val bitmap = Bitmap.createBitmap(
            view.width,
            view.height,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.layout(
            view.left,
            view.top,
            view.right,
            view.bottom
        )
        view.draw(canvas)

        bitmap
    }
}

// TODO: 이거, media type 으로 다뤄야하겠다. 일단 external에는 저장할 수 있음을 확읺마.
suspend fun Bitmap.saveAsImage(
    context: Context,
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO
) {
    val bitmap = this

    withContext(ioDispatcher) {
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                saveAsImageInMediaStore(bitmap, context)
            } else {
                TODO("뭐 해야하지?")
            }
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}

// 쓰기 가능한 상태인지 체크
private fun isExternalStorageWritable(): Boolean {
    return Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
}

// 읽기 가능한 상태인지 체크
private fun isExternalStorageReadable(): Boolean {
    return Environment.getExternalStorageState() in
        setOf(Environment.MEDIA_MOUNTED, Environment.MEDIA_MOUNTED_READ_ONLY)
}

private fun saveAsImageInExternalDir(bitmap: Bitmap, context: Context) {
    val externalDir: File = context.getExternalFilesDir(null)
        ?: throw IOException("can't save because external dir is not available right now.")
    val filename = getRandomFileName("jpg")
    val file = File(externalDir, filename)

    FileOutputStream(file).use { outStream ->
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
    }
    Timber.d("beanbean saved > ${file.absolutePath}")
}

// https://codechacha.com/ko/android-mediastore-insert-media-files/
@RequiresApi(Build.VERSION_CODES.Q)
private fun saveAsImageInMediaStore(bitmap: Bitmap, context: Context) {
    val contentResolver =
        context.contentResolver ?: throw IllegalStateException("contentResolver can't be null")
    val filename = getRandomFileName("jpg")

    val imageValues = ContentValues().apply {
        put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/Hagomandal")
        put(MediaStore.Images.Media.DISPLAY_NAME, filename)
        put(MediaStore.Images.Media.MIME_TYPE, "image/jpg")
        put(MediaStore.Images.Media.IS_PENDING, 1)
    }

    val collection = MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)
    val item = contentResolver.insert(collection, imageValues)
        ?: throw IOException("error while inserting a image values")

    val imageFileDescriptor = contentResolver.openFileDescriptor(item, "w", null)
        ?: throw IOException("can't open file descriptor")

    imageFileDescriptor.use {
        FileOutputStream(it.fileDescriptor).use { outStream ->
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
        }
    }

    imageValues.clear()
    imageValues.put(MediaStore.Images.Media.IS_PENDING, 0)
    contentResolver.update(item, imageValues, null, null)
}
