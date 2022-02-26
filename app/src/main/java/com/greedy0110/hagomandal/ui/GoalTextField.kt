package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.greedy0110.hagomandal.ui.theme.t20

const val GOAL_TEXT_FIELD_MAX_LEN = Int.MAX_VALUE

@Composable
fun GoalTextField(
    modifier: Modifier = Modifier,
    value: String = "",
    onValueChange: (String) -> Unit = {},
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default,
    textStyle: TextStyle = TextStyle.Default,
    hint: String = "",
    prefix: @Composable () -> Unit = {},
    singleLine: Boolean = true,
    maxLength: Int = GOAL_TEXT_FIELD_MAX_LEN,
    enabled: Boolean = true,
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = { newValue ->
            if (newValue.length > maxLength) return@BasicTextField
            onValueChange(newValue)
        },
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        singleLine = singleLine,
        textStyle = textStyle.copy(textAlign = TextAlign.Start),
        cursorBrush = SolidColor(Color.White),
        decorationBox = { innerTextField ->
            Row(horizontalArrangement = Arrangement.Start) {
                prefix()
                if (value.isEmpty()) {
                    Text(
                        text = hint,
                        style = textStyle.copy(textAlign = TextAlign.Start)
                    )
                } else innerTextField()
            }
        },
        enabled = enabled,
    )
}

@Preview
@Composable
fun PreviewGoalTextField() {
    Column {
        GoalTextField(value = "안녕", hint = "플레이스 홀더")
        GoalTextField(value = "", hint = "플레이스 홀더")
        GoalTextField(
            modifier = Modifier.fillMaxWidth(),
            value = "신승민", prefix = { Text(text = "• ", style = t20) }
        )
    }
}
