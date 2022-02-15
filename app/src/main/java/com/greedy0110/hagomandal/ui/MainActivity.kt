package com.greedy0110.hagomandal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.greedy0110.hagomandal.ui.maingoal.MainGoalScreen
import com.greedy0110.hagomandal.ui.subgoal.SubGaolScreen
import com.greedy0110.hagomandal.ui.subgoal.SubGoal
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // OnBoardingApp()
            HagoMandalTheme {
                GoalScreen()
            }
        }
    }
}

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
fun GoalScreen() {
    val (mainGoal, setMainGoal) = remember { mutableStateOf("") }
    val subGoals: SnapshotStateList<SubGoal> = remember {
        val subGoals = IntRange(0, 3)
            .map { SubGoal(title = "", colorIndex = it) }
            .toTypedArray()
        mutableStateListOf(*subGoals)
    }

    val pages = listOf(
        TabItem("핵심목표", if (mainGoal.isNotBlank()) 1 else 0, 1, false),
        TabItem("세부목표", subGoals.count { it.title.isNotBlank() }, subGoals.size, false),
        TabItem("실천목표", 3, 4, false),
    )
    val backgroundColor = Color(0xff202532)

    val pagerState = rememberPagerState()
    // TODO: 이게 뭐람
    val coroutineScope = rememberCoroutineScope()

    val moveToNextPageIfPossible = suspend {
        val currentPage = pagerState.currentPage
        if (currentPage != pages.lastIndex) {
            pagerState.animateScrollToPage(currentPage + 1)
        }
    }

    val tabHeight = 56.dp
    HorizontalPager(
        modifier = Modifier.background(Color.Red),
        count = pages.size,
        state = pagerState,
        contentPadding = PaddingValues(top = tabHeight),
        userScrollEnabled = false // 이렇게 세팅할 수 있어야함...
    ) { page ->
        // TODO: 패딩은?
        when (page) {
            0 -> MainGoalScreen(
                mainGoal = mainGoal,
                setMainGoal = setMainGoal,
                onDone = {
                    coroutineScope.launch { moveToNextPageIfPossible() }
                }
            )
            1 -> SubGaolScreen(
                userName = "신승민",
                mainGoal = mainGoal,
                subGoals = subGoals
            ) // TODO: 이름은 userRepository에서 긁어오기
            2 -> DetailGoalScreen()
            else -> throw UnsupportedOperationException()
        }
    }

    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        backgroundColor = backgroundColor,
        indicator = { tabPositions ->
            GoalIndicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        // TODO: 도대체 클릭이 왜 안돼냐;
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
