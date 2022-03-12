package com.greedy0110.hagomandal.ui.share

import android.os.Bundle
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.greedy0110.hagomandal.util.saveAsImage
import com.greedy0110.hagomandal.util.takeScreenShot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ShareCaptureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AndroidView(factory = { context ->
                ShareCaptureScreenView(context)
                    .also { view ->
                        view.post { takeScreenShot(view) }
                    }
            })
        }
    }

    private fun takeScreenShot(view: View) {
        lifecycleScope.launch {
            val bitmap = view.takeScreenShot()
            bitmap.saveAsImage(applicationContext)
            finish()
        }
    }
}
