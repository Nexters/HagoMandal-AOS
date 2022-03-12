package com.greedy0110.hagomandal.util

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Environment
import android.view.View
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
            val externalDir: File = context.getExternalFilesDir(null)
                ?: throw IOException("can't save because external dir is not available right now.")
            val filename = getRandomFileName("jpg")
            val file = File(externalDir, filename)

            FileOutputStream(file).use { outStream ->
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outStream)
            }
            Timber.d("beanbean saved > ${file.absolutePath}")
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
