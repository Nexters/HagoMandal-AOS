package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t16

// TODO: ripple effect 넣고 싶음.
@Composable
fun ActionButton(
    modifier: Modifier = Modifier,
    text: String,
    emphasize: Boolean = true,
    onClick: () -> Unit = {}
) {
    val backgroundColor = when {
        emphasize -> Color(0xff3388ff)
        else -> Color(0xff2d3949)
    }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(48.dp)
            .clickable { onClick() }
            .background(backgroundColor, RoundedCornerShape(8.dp))
    ) {
        Text(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(),
            text = text,
            style = t16
        )
    }
}

@Preview
@Composable
fun PreviewActionButton() {
    HagoMandalTheme {
        Column {
            ActionButton(text = "만다라트가 뭔데?")
            Spacer(modifier = Modifier.size(6.dp))
            ActionButton(text = "다른 직업은 없어?", emphasize = false)
        }
    }
}
