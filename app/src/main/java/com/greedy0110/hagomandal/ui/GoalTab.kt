package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

private val defaultFontFamily: FontFamily = FontFamily.SansSerif
private val t12 = TextStyle(
    fontWeight = FontWeight.W400,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 14.sp,
    fontFamily = defaultFontFamily,
    lineHeight = 1.2.sp,
    letterSpacing = (-0.3).sp
)

private val t10 = TextStyle(
    fontWeight = FontWeight.W500,
    color = Color.White,
    fontSize = 10.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

@Composable
fun GoalTabBadge(
    completed: Boolean,
    selected: Boolean,
    text: String
) {
    val badgeTextColor = Color(0xff202532)
    val badgeColor = when {
        completed -> Color(0xff3174d6)
        selected -> Color(0xffffffff)
        else -> Color(0xff40495c)
    }

    Text(
        modifier = Modifier
            .background(
                color = badgeColor,
                shape = RoundedCornerShape(60.dp)
            )
            .padding(horizontal = 4.dp, vertical = 2.dp),
        text = text,
        style = t10.copy(badgeTextColor)
    )
}

@Preview
@Composable
fun PreviewGoalTabBadge() {
    HagoMandalTheme {
        Row {
            val completed = listOf(false, true)
            val selected = listOf(false, true)

            completed
                .map { c ->
                    selected.map { s ->
                        GoalTabBadge(completed = c, selected = s, text = "1/4")
                    }
                }
        }
    }
}

@Composable
fun GoalTab(
    modifier: Modifier = Modifier,
    completed: Boolean,
    selected: Boolean,
    title: String,
    badge: String,
    onClick: () -> Unit = {},
) {
    val textColor = when {
        selected -> Color(0xffffffff)
        else -> Color(0xff40485c)
    }
    val indicatorHeight = 2.dp
    val tabHeight = 56.dp

    val indicatorColor = when {
        completed -> Color(0xff3174d6)
        else -> Color(0xff619FFF).copy(alpha = 0.2f)
    }

    Column(
        modifier = modifier
            .clickable { onClick() }
            .fillMaxWidth()
            .height(tabHeight)
            .padding(top = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(bottom = 12.dp)
                .weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = title, style = t12.copy(color = textColor))
            Spacer(Modifier.size(4.dp))
            GoalTabBadge(completed = completed, selected = selected, text = badge)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .background(indicatorColor)
        )
    }
}

@Preview
@Composable
fun PreviewGoalTab() {
    Column {
        GoalTab(completed = true, selected = false, title = "핵심목표", badge = "1/1")
        GoalTab(completed = true, selected = true, title = "핵심목표", badge = "4/4")
        GoalTab(completed = false, selected = false, title = "핵심목표", badge = "2/4")
        GoalTab(completed = false, selected = true, title = "핵심목표", badge = "2/4")
    }
}

@Composable
fun GoalIndicator(modifier: Modifier = Modifier) {
    val indicatorHeight = 2.dp
    val indicatorColor = Color.White.copy(alpha = 0.8f)

    Box(
        modifier
            .fillMaxWidth()
            .height(indicatorHeight)
            .background(indicatorColor, RoundedCornerShape(4.dp))
    )
}

@Preview
@Composable
fun PreviewGoalIndicator() {
    GoalIndicator()
}
