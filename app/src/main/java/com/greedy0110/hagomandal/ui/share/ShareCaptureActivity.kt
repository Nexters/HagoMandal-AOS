package com.greedy0110.hagomandal.ui.share

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.view.View
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.lifecycleScope
import com.greedy0110.hagomandal.ui.DetailGoal
import com.greedy0110.hagomandal.util.saveAsImage
import com.greedy0110.hagomandal.util.takeScreenShot
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize

/**
 * 이미지 저장을 위한 activity
 * 이미지를 저장하려면 startActivity 하면된다.
 * 이미지 저장 후 알아서 종료한다.
 */
@AndroidEntryPoint
class ShareCaptureActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val startArgs = intent.getParcelableExtra<StartArgs>(START_ARGS)
            ?: run {
                throw IllegalStateException("why don't you open it with ShareCaptureActivity.intent?")
            }

        setContent {
            AndroidView(factory = { context ->
                ShareCaptureScreenView(context)
                    .apply {
                        this.userName = startArgs.userName
                        this.mainGoal = startArgs.mainGoal
                        this.detailGoals = startArgs.detailGoals
                    }
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

    companion object {
        private const val START_ARGS = "start_args"

        fun intent(
            context: Context,
            userName: String = "",
            mainGoal: String = "",
            detailGoals: List<DetailGoal> = emptyList(),
        ): Intent {
            return Intent(context, ShareCaptureActivity::class.java).apply {
                putExtra(START_ARGS, StartArgs(userName, mainGoal, detailGoals))
            }
        }
    }

    @Parcelize
    data class StartArgs(
        var userName: String = "",
        var mainGoal: String = "",
        var detailGoals: List<DetailGoal> = emptyList(),
    ) : Parcelable
}
