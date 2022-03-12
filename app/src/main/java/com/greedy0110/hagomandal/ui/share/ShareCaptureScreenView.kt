package com.greedy0110.hagomandal.ui.share

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import com.greedy0110.hagomandal.ui.DetailGoal

// BEAN: 아래 포스팅 방법대로 viewModel을 받아오려 했지만, ViewModel의 Scope가 다른 Activity라서...
// https://medium.com/@nicholas.rose/level-up-your-custom-views-d0c6f69fd1ec

class ShareCaptureScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    var userName: String = ""
    var mainGoal: String = ""
    var detailGoals: List<DetailGoal> = emptyList()

    @Composable
    override fun Content() {
        ShareCaptureScreen(
            userName = userName,
            mainGoal = mainGoal,
            detailGoals = detailGoals
        )
    }
}
