package com.greedy0110.hagomandal.ui

import androidx.annotation.FloatRange
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.util.rememberPrevious

@Composable
fun SubGoalCardList() {
    val cardColors = listOf(
        Color(0xff5533c0),
        Color(0xff35ace4),
        Color(0xff09c6a6),
        Color(0xfff3c403),
    )
    var selectedIndex by remember { mutableStateOf(0) }

    @Composable
    fun getCard(color: Color, index: Int) {
        SubGoalCard(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { selectedIndex = index },
            // backgroundColor = color,
            brushColorIndex = index, // TODO: index 에 따라 선택된 brushColorIndex 제공하기.
            selected = selectedIndex == index
        )
    }

    Column {
        SubGoalLayout(
            modifier = Modifier,
            selectedIndex = selectedIndex,
            partitionGap = 10.dp
        ) {
            cardColors
                .forEachIndexed { index, color -> getCard(color, index) }
        }
    }
}

@Preview
@Composable
fun PreviewSubGoalCardList() {
    HagoMandalTheme {
        SubGoalCardList()
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
