package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t12
import com.greedy0110.hagomandal.ui.theme.t16

@Composable
fun SingleTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChanged: (String) -> Unit,
    trailingText: String? = null,
    hint: String = "",
    maxLength: Int = 8,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions.Default
) {
    val backgroundColor = HagoMandalTheme.colors.surface

    Row(
        modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(start = 16.dp, end = 16.dp, top = 14.dp, bottom = 15.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        GoalTextField(
            modifier = Modifier.weight(1f),
            value = text,
            onValueChange = onTextChanged,
            singleLine = true,
            textStyle = t16.copy(color = HagoMandalTheme.colors.onSurface),
            maxLength = maxLength,
            hint = hint,
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions
        )
        if (trailingText != null) {
            HagoMandalText(text = trailingText, style = t12.copy(Color(0xff999999)))
        }
    }
}

@Preview
@Composable
fun PreviewSingleTextField() {
    HagoMandalTheme {
        SingleTextField(
            text = "박만달",
            onTextChanged = {},
            trailingText = "최대 8자"
        )
    }
}
