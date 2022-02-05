package com.greedy0110.hagomandal.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun SubGaolScreen() {
    Text(text = "Sub")
}

@Preview
@Composable
fun PreviewSubGaolScreen() {
    HagoMandalTheme {
        SubGaolScreen()
    }
}
