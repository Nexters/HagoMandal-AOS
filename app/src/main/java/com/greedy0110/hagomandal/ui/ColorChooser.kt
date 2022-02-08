package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.util.`if`

// TODO: start - end - selected 가 하나의 color 에서 파생되도록 작업될 예정.
private val cardColorPairs = listOf(
    listOf(Color(0xffff4942), Color(0xffff7a75)),
    listOf(Color(0xffff5e00), Color(0xffFF9747)),
    listOf(Color(0xffF59D05), Color(0xffFFCB47)),
    listOf(Color(0xff44AD1A), Color(0xffD2E255)),
    listOf(Color(0xff02C570), Color(0xff03A086)),
    listOf(Color(0xff4abefc), Color(0xff0A89EB)),
    listOf(Color(0xff57a0ff), Color(0xff215EE4)),
    listOf(Color(0xff9373fc), Color(0xff6449d1)),
    listOf(Color(0xffbe6eed), Color(0xff8C27AA)),
    listOf(Color(0xffff8aab), Color(0xffFF2E89)),
)

val cardColorBrushes = cardColorPairs
    .map { colors ->
        Brush.linearGradient(colors = colors, start = Offset.Zero, end = Offset.Infinite)
    }

@Composable
fun ColorChooserUnit(
    modifier: Modifier = Modifier,
    index: Int,
    selected: Boolean
) {
    Box(
        modifier = modifier
            .size(28.dp)
            .`if`(selected) {
                // TODO: 은은히 나오는 컬러는 무엇? 명확히 알아야할 듯
                background(cardColorPairs[index][0].copy(alpha = 0.4f), CircleShape)
                // background(Color.Black, CircleShape)
            }
            .padding(4.dp)
            .background(cardColorBrushes[index], CircleShape)
    ) {
    }
}

@Preview
@Composable
fun PreviewColorChooserUnit() {
    HagoMandalTheme {
        Row {
            repeat(cardColorBrushes.size) {
                ColorChooserUnit(index = it, selected = it == 2)
            }
        }
    }
}

@Composable
fun ColorChooser(
    modifier: Modifier = Modifier,
    selectedIndex: Int?
) {
    LazyRow(
        modifier = modifier.padding(vertical = 6.dp, horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        items(cardColorBrushes.size) { index ->
            ColorChooserUnit(
                modifier = Modifier.padding(horizontal = 8.dp),
                index = index,
                selected = selectedIndex == index
            )
        }
    }
}

@Preview
@Composable
fun PreviewColorChooser() {
    HagoMandalTheme {
        ColorChooser(Modifier, 5)
    }
}
