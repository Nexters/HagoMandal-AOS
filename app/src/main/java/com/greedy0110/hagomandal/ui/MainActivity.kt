package com.greedy0110.hagomandal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material.TabRow
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.greedy0110.hagomandal.ui.maingoal.MainGoalScreen
import com.greedy0110.hagomandal.ui.onboarding.OnBoardingApp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            OnBoardingApp()
            // HagoMandalTheme {
            //     GoalScreen()
            // }
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
    val pages = listOf(
        TabItem("핵심목표", 1, 1, false),
        TabItem("세부목표", 0, 4, false),
        TabItem("실천목표", 3, 4, false),
    )
    val backgroundColor = Color(0xff202532)

    val pagerState = rememberPagerState()
    // TODO: 이게 뭐람
    val coroutineScope = rememberCoroutineScope()

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
        pages.forEachIndexed { index, item ->
            GoalTab(
                completed = item.completed,
                selected = pagerState.currentPage == index,
                title = item.title,
                badge = "${item.doneCount}/${item.totalCount}",
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                }
            )
        }
    }

    val tabHeight = 56.dp
    HorizontalPager(
        count = pages.size,
        state = pagerState,
        contentPadding = PaddingValues(top = tabHeight),
        // userScrollEnabled = false // 이렇게 세팅할 수 있어야함...
    ) { page ->
        // TODO: 패딩은?
        when (page) {
            0 -> MainGoalScreen()
            1 -> SubGaolScreen()
            2 -> DetailGoalScreen()
            else -> throw UnsupportedOperationException()
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
