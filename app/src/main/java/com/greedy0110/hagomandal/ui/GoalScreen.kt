package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.greedy0110.hagomandal.ui.detail.DetailGoalScreen
import com.greedy0110.hagomandal.ui.maingoal.MainGoalScreen
import com.greedy0110.hagomandal.ui.subgoal.SubGaolScreen
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import kotlinx.coroutines.launch

data class TabItem(
    val title: String,
    val doneCount: Int,
    val totalCount: Int,
    val selected: Boolean
) {
    val completed: Boolean get() = doneCount >= totalCount
}

// TODO: 수정 하는 모드로 들어왔나...?
@OptIn(ExperimentalPagerApi::class, androidx.compose.ui.ExperimentalComposeUiApi::class)
@Composable
fun GoalScreen(
    onSubmit: () -> Unit = {},
    goalViewModel: GoalViewModel = viewModel()
) {
    val userName by goalViewModel.userName.collectAsState()
    val mainGoal by goalViewModel.mainGoal.collectAsState()
    val subGoals by goalViewModel.subGoals.collectAsState()
    val detailGoals by goalViewModel.detailGoal.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    val pages = listOf(
        TabItem(
            title = "핵심목표",
            doneCount = if (mainGoal.isNotBlank()) 1 else 0,
            totalCount = 1,
            selected = false
        ),
        TabItem(
            title = "세부목표",
            doneCount = subGoals.count { it.title.isNotBlank() },
            totalCount = 4,
            selected = false
        ),
        TabItem(
            title = "실천목표",
            doneCount = detailGoals.sumOf { list -> list.details.count { it.isNotBlank() } },
            totalCount = 16,
            selected = false
        ),
    )

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()
    val focusManager = LocalFocusManager.current

    val moveToNextPageIfPossible = suspend {
        keyboardController?.hide()
        focusManager.clearFocus(true)

        val currentPage = pagerState.currentPage
        if (currentPage != pages.lastIndex) {
            pagerState.animateScrollToPage(currentPage + 1)
        }
    }

    val tabHeight = 56.dp
    Box(
        modifier = Modifier.background(HagoMandalTheme.colors.background),
    ) {
        HorizontalPager(
            count = pages.size,
            state = pagerState,
            contentPadding = PaddingValues(top = tabHeight),
            userScrollEnabled = false
        ) { page ->
            when (page) {
                0 -> MainGoalScreen(
                    mainGoal = mainGoal,
                    setMainGoal = goalViewModel::setMainGoal,
                    onDone = { coroutineScope.launch { moveToNextPageIfPossible() } }
                )
                1 -> SubGaolScreen(
                    userName = userName,
                    mainGoal = mainGoal,
                    subGoals = subGoals,
                    setSubGoal = goalViewModel::setSubGoal,
                    setSubGoalColor = goalViewModel::setSubGoal,
                    onDone = { coroutineScope.launch { moveToNextPageIfPossible() } },
                    isEndScroll = pagerState.isScrollInProgress.not() && currentPage == 1
                )
                2 -> DetailGoalScreen(
                    userName = userName,
                    mainGoal = mainGoal,
                    onDetailFixed = goalViewModel::setDetailGoal,
                    detailGoals = detailGoals,
                    onSubmit = onSubmit
                )
                else -> throw UnsupportedOperationException()
            }
        }

        Helper(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(bottom = 74.dp, end = 20.dp),
            message = "이직, 취업을 원하는 회사나\n평소 존경하는 사람을 떠올려봐!"
        )

        TabRow(
            selectedTabIndex = pagerState.currentPage,
            backgroundColor = Color.Transparent,
            indicator = { tabPositions ->
                GoalIndicator(
                    Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
                )
            },
        ) {
            // 도대체 클릭이 왜 안돼냐;
            //  ... Pager가 TabRow를 가리고 있었음. github 문서 내용이 좀 잘못된 거 아닌가?
            //  컨트리뷰션 해야할듯
            pages.forEachIndexed { index, item ->
                GoalTab(
                    completed = item.completed,
                    selected = pagerState.currentPage == index,
                    title = item.title,
                    badge = "${item.doneCount}/${item.totalCount}",
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewGoalScreen() {
    HagoMandalTheme {
        GoalScreen()
    }
}
