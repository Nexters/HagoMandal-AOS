package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.greedy0110.hagomandal.ui.theme.t20

// TODO: 다른 화면도 이거 사용해야할 듯.
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
) {
    BasicTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        keyboardOptions = keyboardOptions,
        keyboardActions = keyboardActions,
        textStyle = textStyle,
        // cursorBrush = SolidColor(Color.White),
        decorationBox = { innerTextField ->
            Row(horizontalArrangement = Arrangement.Start) {
                prefix()
                if (value.isEmpty()) Text(text = hint, style = textStyle)
                else innerTextField()
            }
        }
    )
}

@Preview
@Composable
fun PreviewGoalTextField() {
    Column {
        GoalTextField(value = "안녕", hint = "플레이스 홀더")
        GoalTextField(value = "", hint = "플레이스 홀더")
        GoalTextField(value = "신승민", prefix = { Text(text = "• ", style = t20) })
    }
}
