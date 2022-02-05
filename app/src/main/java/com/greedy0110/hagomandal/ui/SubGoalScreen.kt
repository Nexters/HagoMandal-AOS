package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun SubGaolScreen() {
    val nickname = "박만달"
    val mainGoal = "8구단 드래프트 1순위"

    Scaffold {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 20.dp)
        ) {
            Text(text = "${nickname}님의 핵심목표")
            Spacer(modifier = Modifier.size(4.dp))
            Text(text = mainGoal)
            Spacer(modifier = Modifier.size(22.dp))
            Column(modifier = Modifier) {
                SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff5533c0))
                SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff35ace4))
                SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff09c6a6))
                SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xfff3c403))
            }
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
