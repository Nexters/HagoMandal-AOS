package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.Helper
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun GetStartedScreen(
    onNext: () -> Unit,
) {
    val helperMessages = listOf(
        "여기야 여기! 나를 눌러봐!",
        "잘했어, 도움이 필요할 때 언제든 날 눌러!"
    )

    var messageIndex by remember { mutableStateOf(0) }
    val helperMessage = helperMessages[messageIndex]

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xff202632), Color(0xff131b2b))
                )
            ),
        horizontalAlignment = Alignment.End
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            text = "아 맞다! 그러고 보니 \n" +
                "내 이름이... 뭐더라..?",
            style = t24.copy(textAlign = TextAlign.Start)
        )
        Spacer(modifier = Modifier.weight(1f))
        Helper(
            modifier = Modifier.padding(horizontal = 20.dp),
            message = helperMessage,
            onClick = { if (messageIndex != helperMessages.lastIndex) messageIndex++ }
        )
        Spacer(modifier = Modifier.height(20.dp))
        ActionButton(text = "다음으로", onClick = onNext)
        Spacer(modifier = Modifier.height(80.dp))
    }
}

@Preview
@Composable
fun PreviewGetStartedScreen() {
    HagoMandalTheme {
        GetStartedScreen({})
    }
}
