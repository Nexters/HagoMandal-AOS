package com.greedy0110.hagomandal.ui.detail

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.greedy0110.hagomandal.ui.DetailGoal
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t14
import com.greedy0110.hagomandal.ui.theme.t24
import timber.log.Timber

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun DetailGoalScreen(
    modifier: Modifier = Modifier,
    onDetailFixed: (subGoalIndex: Int, index: Int, raw: String) -> Unit = { _, _, _ -> },
    userName: String = "",
    mainGoal: String = "",
    detailGoals: List<DetailGoal> = emptyList(),
    expanded: MutableState<Boolean> = rememberSaveable { mutableStateOf(false) },
    selectedIndex: MutableState<Int> = rememberSaveable { mutableStateOf(0) },
    onSubmit: () -> Unit = { Timber.d("beanbean on submit") },
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var isSubmitVisible by remember { mutableStateOf(false) }

    val onDone = {
        keyboardController?.hide()
        isSubmitVisible = true
    }

    Scaffold(
        modifier = modifier,
        backgroundColor = HagoMandalTheme.colors.background
    ) {
        ConstraintLayout(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            val (mainGoalSlot, cardList) = createRefs()

            DetailGoalCardList(
                modifier = Modifier
                    .constrainAs(cardList) { centerTo(parent) },
                detailGoals = detailGoals,
                onDetailFixed = onDetailFixed,
                selectedIndex = selectedIndex.value,
                setSelectedIndex = { selectedIndex.value = it },
                expanded = expanded.value,
                setExpanded = { expanded.value = it },
                onNext = { index -> selectedIndex.value = index + 1 },
                onDone = { onDone() },
                isSubmitButtonShow = isSubmitVisible,
                onSubmit = onSubmit
            )

            AnimatedVisibility(
                visible = expanded.value.not() || selectedIndex.value == 0,
                enter = fadeIn(animationSpec = tween(300)),
                exit = fadeOut(animationSpec = tween(300))
            ) {
                Column(
                    modifier = Modifier
                        .constrainAs(mainGoalSlot) {
                            top.linkTo(parent.top)
                            start.linkTo(parent.start)
                        }
                        .padding(top = 32.dp)
                ) {
                    Text(
                        text = "${userName}님의 핵심목표",
                        style = t14,
                        color = Color.White.copy(alpha = 0.5f)
                    )
                    Spacer(modifier = Modifier.size(4.dp))
                    Text(
                        text = mainGoal,
                        style = t24,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetailGoalScreen() {
    val details = remember {
        mutableStateListOf(
            DetailGoal("주식 공부하기", listOf("주식 책 6권 읽기", "주식 투자 포트폴리오 만들기", "가진 종목 스토리 점검하기", ""), 0),
            DetailGoal("세금 공부하기", listOf("세금 책 2권 읽기", "", "", ""), 1),
            DetailGoal("부동산 공부하기", listOf("나만의 집 기준 만들기", "자취방 체크 리스트 만들기", "", ""), 2),
            DetailGoal("저축하기", listOf("용돈 통장 만들어서 쓰기", "", "", ""), 3)
        )
    }

    HagoMandalTheme {
        DetailGoalScreen(
            userName = "신승민",
            mainGoal = "부자가 될거야.",
            detailGoals = details,
            expanded = remember { mutableStateOf(true) },
            selectedIndex = remember { mutableStateOf(2) },
        )
    }
}
