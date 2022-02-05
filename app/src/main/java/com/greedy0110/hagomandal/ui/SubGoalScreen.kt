package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
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
            SubGoalCardList()
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
