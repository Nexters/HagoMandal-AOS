package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun SubGoalLayout(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val gap = 40.dp

    Layout(
        modifier = modifier,
        content = content
    ) { measurables, constraints ->
        val placeables = measurables.map { measurable ->
            measurable.measure(constraints)
        }

        // TODO: height를 미리 계산할 수 있잖아?
        layout(constraints.maxWidth, constraints.maxHeight) {
            var yPosition = 0

            placeables.forEach { placeable ->
                placeable.placeRelative(x = 0, y = yPosition)
                yPosition += gap.roundToPx()
            }
        }
    }
}

@Composable
fun SubGoalCardList() {
    SubGoalLayout(modifier = Modifier) {
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff5533c0))
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff35ace4))
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff09c6a6))
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xfff3c403))
    }
}

@Preview
@Composable
fun PreviewSubGoalCardList() {
    HagoMandalTheme {
        SubGoalCardList()
    }
}
