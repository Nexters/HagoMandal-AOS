package com.greedy0110.hagomandal.ui

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
            backgroundColor = color
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


    //TODO: 전 selected 를 알아야해.

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

//        val layoutHeight = if (selectedIndex == placeables.lastIndex) {
//            (placeables.size - 1) * gap.roundToPx() + placeables.last().height
//        } else {
//            selectedIndex * gap.roundToPx() +
//                placeables[selectedIndex].height +
//                partitionGap.roundToPx() +
//                (placeables.lastIndex - selectedIndex - 1) * gap.roundToPx() +
//                placeables.last().height
//        }.coerceAtMost(constraints.maxHeight)
        val layoutHeight = constraints.maxHeight

        layout(constraints.maxWidth, layoutHeight) {
            var yPosition = 0

            placeables.forEachIndexed { index, placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
//                yPosition +=
//                    if (index != selectedIndex) gap.roundToPx()
//                    else placeable.height + partitionGap.roundToPx() + anana.value.roundToPx()
                yPosition += gap.roundToPx() +
                    if (index == selectedIndex) ((placeable.height + partitionGap.roundToPx() - gap.roundToPx()) * yDeltaAnimation.value).toInt() else 0
            }
        }
    }
}
