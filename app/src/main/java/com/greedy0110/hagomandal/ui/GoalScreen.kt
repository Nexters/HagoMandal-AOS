package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GoalScreen(
    onSubmit: () -> Unit = {},
    goalViewModel: GoalViewModel = viewModel()
) {
    val userName by goalViewModel.userName.collectAsState()
    val mainGoal by goalViewModel.mainGoal.collectAsState()
    val subGoals by goalViewModel.subGoals.collectAsState()
    val detailGoals by goalViewModel.detailGoal.collectAsState()

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
    val tabBackgroundColor = Color(0xff202532)

    val pagerState = rememberPagerState()
    val coroutineScope = rememberCoroutineScope()

    val moveToNextPageIfPossible = suspend {
        val currentPage = pagerState.currentPage
        if (currentPage != pages.lastIndex) {
            // FIXME: 애니메이션으로 동작하면 request focus 등과 함께 사용하기 어렵다.
            pagerState.animateScrollToPage(currentPage + 1)
            // pagerState.scrollToPage(currentPage + 1)
        }
    }

    val tabHeight = 56.dp
    HorizontalPager(
        modifier = Modifier.background(Color.Red),
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
                onDone = { coroutineScope.launch { moveToNextPageIfPossible() } }
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

    TabRow(
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = tabBackgroundColor,
        indicator = { tabPositions ->
            GoalIndicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
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

@Preview(showBackground = true)
@Composable
fun PreviewGoalScreen() {
    HagoMandalTheme {
        GoalScreen()
    }
}
