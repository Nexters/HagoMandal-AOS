package com.greedy0110.hagomandal.ui.share

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.DetailGoal
import com.greedy0110.hagomandal.ui.GoalViewModel
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.backgroundColor
import com.greedy0110.hagomandal.ui.theme.t14
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun ShareScreen(
    goalViewModel: GoalViewModel = viewModel(),
    onModifyNameClick: () -> Unit = {},
    onModifyGoalsClick: () -> Unit = {},
    onDeleteGoalsClick: () -> Unit = {},
    emphasize: Boolean = false,
) {
    // TODO: emphasize에 따라서 overlaySlot에 채워넣을 내용이 달라진다. (공유하기는 이 overlaySlot에서 처리될 것)
    val details = listOf(
        DetailGoal(
            "주식 공부하기",
            listOf("주식 책 6권 읽기", "주식 투자 포트폴리오 만들기", "가진 종목 스토리 점검하기", "연간 수익율 분석하고 공유하는 스터디 하기"),
            0
        ),
        DetailGoal("세금 공부하기", listOf("세금 책 2권 읽기", "", "", ""), 1),
        DetailGoal("부동산 공부하기", listOf("나만의 집 기준 만들기", "자취방 체크 리스트 만들기", "", ""), 2),
        DetailGoal("저축하기", listOf("용돈 통장 만들어서 쓰기", "", "", ""), 3)
    )
    val userName = "신승민"
    val mainGoal = "8구단 드래프트 1순위"
    val duration = "D-53" // TODO: 어떻게 ... 처리할까.

    ShareScreen(
        // userName = goalViewModel.userName.collectAsState().value,
        // mainGoal = goalViewModel.mainGoal.collectAsState().value,
        // detailGoals = goalViewModel.detailGoal.collectAsState().value,
        userName = userName,
        mainGoal = mainGoal,
        detailGoals = details,
        duration = duration,
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
    modifier: Modifier = Modifier,
    userName: String = "",
    duration: String = "",
    mainGoal: String = "",
    detailGoals: List<DetailGoal> = emptyList(),
    onModifyNameClick: () -> Unit = {},
    onModifyGoalsClick: () -> Unit = {},
    onDeleteGoalsClick: () -> Unit = {},
    overlaySlot: @Composable () -> Unit = {},
) {
    val actions = listOf(
        ShareAction(iconRes = R.drawable.ic_user, title = "이름 수정", action = onModifyNameClick),
        ShareAction(iconRes = R.drawable.ic_edit_2, title = "목표 수정", action = onModifyGoalsClick),
        ShareAction(iconRes = R.drawable.ic_trash, title = "목표 삭제", action = onDeleteGoalsClick),
        ShareAction(iconRes = R.drawable.ic_image, title = "이미지로 저장"), // TODO: 콜백으로 뭐?
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(backgroundColor)
    ) {
        ShareTopBar(
            modifier = Modifier.padding(top = 24.dp),
            title = duration,
            actions = actions
        )
        Column(
            modifier = Modifier
                .padding(top = 141.dp)
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Text(text = "${userName}님의 핵심목표", style = t14, color = Color.White.copy(alpha = 0.5f))
            Spacer(modifier = Modifier.height(4.dp))
            Text(text = mainGoal, style = t24)
            Spacer(modifier = Modifier.height(24.dp))
            ShareCardList(details = detailGoals)
        }
    }
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
