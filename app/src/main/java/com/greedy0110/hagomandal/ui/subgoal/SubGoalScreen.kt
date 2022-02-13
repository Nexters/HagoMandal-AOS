package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
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
private val t24 = TextStyle(
    fontWeight = FontWeight.W700,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 24.sp,
    fontFamily = defaultFontFamily,
    lineHeight = 1.5.sp,
    letterSpacing = (-0.3).sp
)

private val t14 = TextStyle(
    fontWeight = FontWeight.W400,
    color = Color.White,
    fontSize = 14.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

@Composable
fun SubGaolScreen(
    modifier: Modifier = Modifier
) {
    val nickname = "박만달"
    val mainGoal = "8구단 드래프트 1순위"

    val backgroundColor = Color(0xff19202e)

    Scaffold(
        modifier = modifier,
        backgroundColor = backgroundColor
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(top = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = "${nickname}님의 핵심목표",
                    style = t14,
                    color = Color.White.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = mainGoal,
                    style = t24,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(22.dp))
                SubGoalCardList()
            }
            // TODO: 키보드 위에 붙어있어야함.
            ColorChooser(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(backgroundColor),
                selectedIndex = null,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp)
            )
        }
    }
}

@Preview
@Composable
fun PreviewSubGaolScreen() {
    HagoMandalTheme {
        SubGaolScreen()
    }
}
