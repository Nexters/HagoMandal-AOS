package com.greedy0110.hagomandal.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.Tab
import androidx.compose.material.TabRow
import androidx.compose.material.TabRowDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.pagerTabIndicatorOffset
import com.google.accompanist.pager.rememberPagerState
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // TODO: Activity 는 하나의 Scenario 를 의미해야한다.
            HagoMandalTheme {
                GoalScreen()
            }
        }
    }
}

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GoalScreen() {
    val pages = listOf("a", "b", "c")

    val pagerState = rememberPagerState()
    // TODO: 이게 뭐람
    val coroutineScope = rememberCoroutineScope()

    TabRow(
        // Our selected tab is our current page
        selectedTabIndex = pagerState.currentPage,
        //TODO: indicator 만들기
        indicator = { tabPositions ->
            TabRowDefaults.Indicator(
                Modifier.pagerTabIndicatorOffset(pagerState, tabPositions)
            )
        }
    ) {
        // Add tabs for all of our pages
        pages.forEachIndexed { index, title ->
            //TODO: tab 만들기
            Tab(
                text = { Text(title) },
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.scrollToPage(index)
                    }
                },
            )
        }
    }

    HorizontalPager(
        count = pages.size,
        state = pagerState,
    ) { page ->
        //TODO: 패딩은?
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

@Composable
fun MainGoalScreen() {
    Text(text = "MainGaolScreen")
}

@Preview
@Composable
fun PreviewMainGoalScreen() {

}

@Composable
fun SubGaolScreen() {
    Text(text = "Sub")
}

@Preview
@Composable
fun PreviewSubGaolScreen() {

}

@Composable
fun DetailGoalScreen() {
    Text(text = "Detail")
}

@Preview
@Composable
fun PreviewDetailGoalScreen() {

}
