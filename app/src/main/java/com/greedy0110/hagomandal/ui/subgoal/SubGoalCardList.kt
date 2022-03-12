package com.greedy0110.hagomandal.ui.subgoal

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.SubGoal
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.util.rememberPrevious
import timber.log.Timber

@Composable
fun SubGoalCardList(
    subGoals: List<SubGoal>,
    setSubGoal: (Int, String) -> Unit = { _, _ -> },
    selectedIndex: Int = 0,
    setSelectedIndex: (Int) -> Unit = {},
    onNext: (Int) -> Unit = {},
    onDone: () -> Unit = {},
    isEndScroll: Boolean = true,
) {
    val focusRequesters = Array(subGoals.size) { FocusRequester() }

    @Composable
    fun getCard(index: Int, subGoal: SubGoal) {
        SubGoalCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { setSelectedIndex(index) },
            brushColorIndex = subGoal.colorIndex,
            selected = selectedIndex == index,
            title = subGoal.title,
            setTitle = { title -> setSubGoal(index, title) },
            isDoneable = index == subGoals.lastIndex,
            onNext = { onNext(index) },
            onDone = { onDone() },
            focusRequester = focusRequesters[index],
        )
    }

    Column {
        SubGoalLayout(
            modifier = Modifier,
            selectedIndex = selectedIndex,
            partitionGap = 10.dp
        ) {
            subGoals
                .forEachIndexed { index, subGoal -> getCard(index, subGoal) }
        }
    }

    // Scroll 중간에 포커스가 넘어가면, 스크롤이 중단되는 버그가 있다.
    LaunchedEffect(key1 = selectedIndex, key2 = isEndScroll) {
        Timber.d("launched effect trigger -> $selectedIndex, $isEndScroll")
        if (!isEndScroll) return@LaunchedEffect
        focusRequesters[selectedIndex].requestFocus()
    }
}

@Preview
@Composable
fun PreviewSubGoalCardList() {
    val subGoals: SnapshotStateList<SubGoal> = remember {
        val subGoals = IntRange(0, 3)
            .map { SubGoal(title = "", colorIndex = it) }
            .toTypedArray()
        mutableStateListOf(*subGoals)
    }

    HagoMandalTheme {
        SubGoalCardList(subGoals)
    }
}

@Composable
private fun SubGoalLayout(
    modifier: Modifier = Modifier,
    selectedIndex: Int,
    partitionGap: Dp,
    content: @Composable () -> Unit
) {
    val gap = 40.dp

    // selectedIndex 별로 애니메이션이 실행되고 싶어.
    val yDeltaAnimation = remember(selectedIndex) { Animatable(0f) }

    // TODO: rememberPrevious 동작 이해하기
    val previousSelectedIndex = rememberPrevious(current = selectedIndex)

    LaunchedEffect(yDeltaAnimation) {
        yDeltaAnimation.animateTo(
            targetValue = 1f,
            animationSpec = tween(1000)
        )
    }

    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        fun getLayoutHeight(selectedIndex: Int): Int {
            return if (selectedIndex == placeables.lastIndex) {
                (placeables.size - 1) * gap.roundToPx() + placeables.last().height
            } else {
                selectedIndex * gap.roundToPx() +
                    placeables[selectedIndex].height +
                    partitionGap.roundToPx() +
                    (placeables.lastIndex - selectedIndex - 1) * gap.roundToPx() +
                    placeables.last().height
            }.coerceAtMost(constraints.maxHeight)
        }

        fun getYPosition(index: Int, selectedIndex: Int): Int {
            return when {
                index == 0 -> 0.dp
                index > selectedIndex -> gap * (index - 1) + (placeables[index - 1].height.toDp() + partitionGap)
                else -> gap * index
            }.roundToPx()
        }

        val layoutHeight =
            if (previousSelectedIndex == null) getLayoutHeight(selectedIndex)
            else {
                val fromLayoutHeight = getLayoutHeight(previousSelectedIndex)
                val toLayoutHeight = getLayoutHeight(selectedIndex)
                interpolateValue(fromLayoutHeight, toLayoutHeight, yDeltaAnimation.value)
            }

        layout(constraints.maxWidth, layoutHeight) {
            placeables.forEachIndexed { index, placeable ->
                if (previousSelectedIndex == null) placeable.placeRelative(
                    x = 0,
                    y = getYPosition(index, selectedIndex)
                )
                else {
                    val fromY = getYPosition(index, previousSelectedIndex)
                    val toY = getYPosition(index, selectedIndex)
                    placeable.placeRelative(
                        x = 0,
                        y = interpolateValue(fromY, toY, yDeltaAnimation.value)
                    )
                }
            }
        }
    }
}

fun interpolateValue(from: Int, to: Int, @FloatRange(from = 0.0, to = 1.0) factor: Float): Int {
    return (from + ((to - from) * factor)).toInt()
}
