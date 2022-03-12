package com.greedy0110.hagomandal.ui.share

import android.content.Context
import android.util.AttributeSet
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AbstractComposeView
import com.greedy0110.hagomandal.ui.DetailGoal

class ShareCaptureScreenView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AbstractComposeView(context, attrs, defStyleAttr) {
    var userName: String = ""
    var mainGoal: String = ""
    var detailGoals: List<DetailGoal> = emptyList()

    // https://medium.com/@nicholas.rose/level-up-your-custom-views-d0c6f69fd1ec
    // TODO: 이렇게 받아올 수 있나?
    // private val goalViewModel: GoalViewModel by lazy {
    //     ViewModelProvider(findViewTreeViewModelStoreOwner()!!)[GoalViewModel::class.java]
    // }

    @Composable
    override fun Content() {
        // ShareCaptureScreen(goalViewModel = goalViewModel)
        ShareCaptureScreen(
            userName = "신승민",
            mainGoal = "부자가 되겠어",
            detailGoals = listOf(
                DetailGoal(
                    "주식 공부하기",
                    listOf("주식 책 6권 읽기", "주식 투자 포트폴리오 만들기", "가진 종목 스토리 점검하기", ""),
                    0
                ),
                DetailGoal("세금 공부하기", listOf("세금 책 2권 읽기", "", "", ""), 1),
                DetailGoal("부동산 공부하기", listOf("나만의 집 기준 만들기", "자취방 체크 리스트 만들기", "", ""), 2),
                DetailGoal("저축하기", listOf("용돈 통장 만들어서 쓰기", "", "", ""), 3)
            )
        )
    }
}
