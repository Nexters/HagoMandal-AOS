package com.greedy0110.hagomandal.ui.share

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.ui.DetailGoal
import com.greedy0110.hagomandal.ui.GoalViewModel
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun ShareScreen(
    goalViewModel: GoalViewModel = viewModel(),
    onModifyNameClick: (String) -> Unit = {},
    onModifyGoalsClick: () -> Unit = {},
    onDeleteGoalsClick: () -> Unit = {},
    emphasize: Boolean = false,
) {
    // TODO: emphasize에 따라서 overlaySlot에 채워넣을 내용이 달라진다. (공유하기는 이 overlaySlot에서 처리될 것)

    ShareScreen(
        userName = goalViewModel.userName.collectAsState().value,
        duration = "", // TODO: 어떻게 ... 처리할까.
        mainGoal = goalViewModel.mainGoal.collectAsState().value,
        detailGoals = goalViewModel.detailGoal.collectAsState().value,
        onModifyNameClick = onModifyNameClick,
        onModifyGoalsClick = onModifyGoalsClick,
        onDeleteGoalsClick = onDeleteGoalsClick
    )
}

// TODO: 외부에서 처리해야할 기능 정리
// - 이름 수정
// - 목표 수정

// TODO: ShareScreen이 자체적으로 처리할 기능 정리
// - 이미지로 저장. (어떻게 하지?)

@Composable
private fun ShareScreen(
    userName: String = "",
    duration: String = "",
    mainGoal: String = "",
    detailGoals: List<DetailGoal> = emptyList(),
    onModifyNameClick: (String) -> Unit = {},
    onModifyGoalsClick: () -> Unit = {},
    onDeleteGoalsClick: () -> Unit = {},
    overlaySlot: @Composable () -> Unit = {},
) {
}

@Preview
@Composable
private fun PreviewShareScreen() {
    val details = listOf(
        DetailGoal(
            "주식 공부하기",
            listOf("주식 책 6권 읽기", "주식 투자 포트폴리오 만들기", "가진 종목 스토리 점검하기", ""),
            0
        ),
        DetailGoal("세금 공부하기", listOf("세금 책 2권 읽기", "", "", ""), 1),
        DetailGoal("부동산 공부하기", listOf("나만의 집 기준 만들기", "자취방 체크 리스트 만들기", "", ""), 2),
        DetailGoal("저축하기", listOf("용돈 통장 만들어서 쓰기", "", "", ""), 3)
    )

    HagoMandalTheme {
        ShareScreen(
            userName = "신승민",
            duration = "D-53", // TODO: 다른 포맷으로 고려할 것.
            mainGoal = "부자가 되겠어",
            detailGoals = details,
        )
    }
}
