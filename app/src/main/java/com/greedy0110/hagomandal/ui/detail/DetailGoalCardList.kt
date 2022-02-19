package com.greedy0110.hagomandal.ui.detail

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import timber.log.Timber

data class DetailGoal(
    val title: String,
    val details: List<String>,
    val colorIndex: Int,
)

@Composable
fun DetailGoalCardList(
    modifier: Modifier = Modifier,
    detailGoals: SnapshotStateList<DetailGoal> = mutableStateListOf(),
    titles: List<String> = emptyList(),
    detailss: SnapshotStateList<SnapshotStateList<String>> = mutableStateListOf(),
    selectedIndex: Int = 0,
    setSelectedIndex: (Int) -> Unit = {},
    onNext: (Int) -> Unit = {},
    onDone: () -> Unit = {},
    expanded: MutableState<Boolean> = remember { mutableStateOf(false) },
) {

    // TODO: snap 되어야 한다. (select 에 따라서 우리가, offset 설정하면 된다. 스크롤 노노?)
    val spaceSize: Int by animateIntAsState(if (expanded.value) 24 else -92)

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    //TODO: goals
    // - 사용자가 스크롤 하다가 멈추면, 가까운 item이 특정 위치에 보이도록 snap (터치 끝나면!)
    //   1. 사용자가 스크롤 멈춤 판단
    //   2. listState 상에서 해당 위치에 가까운 index를 추출
    //   3. 해당 index를 특정 위치까지 이동시킴.

    val draggedState = lazyListState.interactionSource.collectIsDraggedAsState()

    // draggedState가 끝났으면서 스크롤도 멈췄다면.
    if (draggedState.value.not() && lazyListState.isScrollInProgress.not()) {
        Timber.d("beanbean dragged state - ${lazyListState.isScrollInProgress} ${lazyListState.firstVisibleItemIndex} ${lazyListState.firstVisibleItemScrollOffset}")

        //TODO: Unit을 키로 써도 괜찮나...?
        // key 기준으로 LaunchedEffect가 시작될 거야.
        LaunchedEffect(key1 = draggedState) {
            lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
        }
        //TODO: launch 시키는 코드는 왜 비권장인가?
        // coroutineScope.launch {
        //     lazyListState.animateScrollToItem(lazyListState.firstVisibleItemIndex)
        // }
    }


    LazyColumn(
        modifier = modifier,
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(spaceSize.dp),
        // userScrollEnabled = false
    ) {
        itemsIndexed(detailGoals) { index, goal ->
            DetailGoalCard(
                modifier = Modifier.fillMaxWidth(),
                brushColorIndex = goal.colorIndex,
                title = goal.title,
                details2 = goal.details,
                setDetails2 = {
                    detailGoals[index] = detailGoals[index].copy(details = it)
                },
                expanded = expanded.value,
                onCardClick = {
                    // expanded 상태가 되어야한다.
                    expanded.value = true

                    // TODO: expanded 가 완료된 이후 index 까지 스크롤이 되어야한다.
                    coroutineScope.launch {
                        lazyListState.animateScrollToItem(index)
                    }
                }
            )
        }
    }
}

private val titles: List<String> = listOf(
    "많이 읽고 많이 쓰기",
    "체력 향상",
    "서비스 보는 눈 키우기",
    "문제 해결 능력 기르기"
)
private val detailss = mutableStateListOf(
    mutableStateListOf<String>("", "", "", ""),
    mutableStateListOf<String>("", "", "", ""),
    mutableStateListOf<String>("", "", "", ""),
    mutableStateListOf<String>("", "", "", ""),
)

@SuppressLint("UnrememberedMutableState")
@Preview
@Composable
fun PreviewDetailGoalListExpanded() {
    DetailGoalCardList(titles = titles, detailss = detailss, expanded = mutableStateOf(true))
}

@Preview
@Composable
fun PreviewDetailGoalList() {
    DetailGoalCardList(titles = titles, detailss = detailss)
}
