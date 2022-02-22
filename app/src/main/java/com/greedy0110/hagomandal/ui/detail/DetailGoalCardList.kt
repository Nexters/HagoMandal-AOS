package com.greedy0110.hagomandal.ui.detail

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.DetailGoal
import dev.chrisbanes.snapper.ExperimentalSnapperApi
import dev.chrisbanes.snapper.SnapOffsets
import dev.chrisbanes.snapper.rememberSnapperFlingBehavior
import kotlinx.coroutines.launch

@OptIn(ExperimentalSnapperApi::class)
@Composable
fun DetailGoalCardList(
    modifier: Modifier = Modifier,
    detailGoals: List<DetailGoal> = emptyList(),
    onDetailFixed: (subGoalIndex: Int, index: Int, raw: String) -> Unit = { _, _, _ -> Unit },
    selectedIndex: Int = 0,
    setSelectedIndex: (Int) -> Unit = {},
    onNext: (Int) -> Unit = {},
    onDone: () -> Unit = {},
    expanded: Boolean = false,
    setExpanded: (Boolean) -> Unit = {},
    isSubmitButtonShow: Boolean = false,
    onSubmit: () -> Unit = {},
) {
    val coroutineScope = rememberCoroutineScope()
    val spaceSize: Int by animateIntAsState(if (expanded) 24 else -92)
    val lazyListState = rememberLazyListState(initialFirstVisibleItemIndex = selectedIndex)
    //바텀 패딩을 임의로 개많이줌. (아래 것도 잘 스크롤 해서 위로 적절히 위치 시킬 수 있도록)
    val contentPadding =
        PaddingValues(top = 122.dp, bottom = if (isSubmitButtonShow) 40.dp else 600.dp)
    val snapper = rememberSnapperFlingBehavior(
        lazyListState = lazyListState,
        snapOffsetForItem = SnapOffsets.Start,
        endContentPadding = contentPadding.calculateBottomPadding()
    )

    LaunchedEffect(key1 = isSubmitButtonShow) {
        if (isSubmitButtonShow) lazyListState.scrollToItem(detailGoals.size)
    }

    if (lazyListState.isScrollInProgress && selectedIndex != lazyListState.firstVisibleItemIndex) {
        setSelectedIndex(lazyListState.firstVisibleItemIndex)
    }

    val focusManager = LocalFocusManager.current
    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(spaceSize.dp),
        flingBehavior = snapper,
        contentPadding = contentPadding
        // userScrollEnabled = false
    ) {
        itemsIndexed(detailGoals) { index, goal ->
            DetailGoalCard(
                modifier = Modifier.fillMaxWidth(),
                brushColorIndex = goal.colorIndex,
                expanded = expanded,
                title = goal.title,
                onCardClick = {
                    if (expanded.not()) {
                        setExpanded(true)
                    }
                    coroutineScope.launch {
                        // TODO: expanded 가 완전이 완료된 이후 스크롤 되어야한다
                        lazyListState.animateScrollToItem(index)
                        setSelectedIndex(index)
                    }
                },
                details = goal.details,
                onDetailFixed = { detailIndex, raw -> onDetailFixed(index, detailIndex, raw) },
                isLastCard = index == detailGoals.lastIndex,
                onNext = {
                    onNext(index)
                    focusManager.moveFocus(FocusDirection.Down)
                },
                onDone = { onDone() }
            )
        }
        if (isSubmitButtonShow) {
            item {
                ActionButton(text = "만다라트 완성", onClick = onSubmit)
            }
        }
    }
}

private val details =
    mutableStateListOf(
        DetailGoal("주식 공부하기", listOf("주식 책 6권 읽기", "주식 투자 포트폴리오 만들기", "가진 종목 스토리 점검하기", ""), 0),
        DetailGoal("세금 공부하기", listOf("세금 책 2권 읽기", "", "", ""), 1),
        DetailGoal("부동산 공부하기", listOf("나만의 집 기준 만들기", "자취방 체크 리스트 만들기", "", ""), 2),
        DetailGoal("저축하기", listOf("용돈 통장 만들어서 쓰기", "", "", ""), 3)
    )

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewDetailGoalListExpanded() {
    DetailGoalCardList(expanded = true, detailGoals = details)
}

@Preview
@Composable
fun PreviewDetailGoalList() {
    DetailGoalCardList(detailGoals = details)
}
