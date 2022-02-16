package com.greedy0110.hagomandal.ui.detail

import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun DetailGoalScreen() {
    var isCardCollplased by remember { mutableStateOf(false) }
    DetailGoalList(isCollapsed = isCardCollplased) {
        if (!isCardCollplased) isCardCollplased = true
    }
}

@Composable
fun DetailGoalList(isCollapsed: Boolean, onCardClick: () -> Unit) {
    val spaceSize: Int by animateIntAsState(if (isCollapsed) 24 else -92)
    val cardColors =
        listOf(Color(0xff5535c0), Color(0xff37aae4), Color(0xff09c6a6), Color(0xfff5c400))

    LazyColumn(verticalArrangement = Arrangement.spacedBy(spaceSize.dp)) {
        items(4) { index ->
            DetailGoalCard(cardColors[index], onCardClick)
        }
    }
}

@Composable
fun DetailGoalCard(color: Color, onCardClick: () -> Unit) {
    Surface(
        color = color,
        shape = RoundedCornerShape(16.dp),
        modifier = Modifier.clickable { onCardClick() }
    ) {
        Column(
            modifier = Modifier
                .padding(bottom = 30.dp, top = 20.dp, start = 20.dp, end = 20.dp),
        ) {
            Text(text = "문제 해결 능력 기르기", color = Color.White)
            Row(verticalAlignment = Alignment.CenterVertically) {
                DetailGoalTextField()
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                DetailGoalTextField()
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                DetailGoalTextField()
            }
        }
    }
}

@Composable
fun DetailGoalTextField() {
    Text(text = ".", color = Color.White)
    TextField(
        value = "예제 텍스트입니다", onValueChange = { },
        colors = TextFieldDefaults.textFieldColors(
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent,
            backgroundColor = Color.Transparent
        )
    )
}

@Preview
@Composable
fun PreviewDetailGoalScreen() {
    var isCardCollplased by remember { mutableStateOf(false) }
    DetailGoalList(isCollapsed = isCardCollplased) {
        if (!isCardCollplased) isCardCollplased = true
    }
}
