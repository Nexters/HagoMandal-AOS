package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.SingleTextField
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun NameScreen(
    onNext: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = viewModel(),
    isUpdating: Boolean = false,
) {
    val text = onBoardingViewModel.userName.collectAsState()
    val title = when {
        isUpdating -> "어쩐지..\n뭔가 익숙하지 않더라고..."
        else -> "아 맞다! 그러고 보니 \n내 이름이... 뭐더라..?"
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HagoMandalTheme.colors.background)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
        ) {
            Spacer(modifier = Modifier.height(60.dp))
            HagoMandalText(
                text = title,
                style = t24,
                textAlign = TextAlign.Start
            )
            Spacer(modifier = Modifier.height(48.dp))
            SingleTextField(
                modifier = Modifier.fillMaxWidth(),
                text = text.value,
                onTextChanged = { onBoardingViewModel.setUserName(it) },
                trailingText = "최대 8자",
                maxLength = 8,
                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(onDone = {
                    if (text.value.isNotBlank()) {
                        onBoardingViewModel.setUserName(text.value)
                        onNext()
                    }
                })
            )
        }
        if (isUpdating) {
            Spacer(modifier = Modifier.weight(1f))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(HagoMandalTheme.colors.primary)
                    .clickable {
                        if (text.value.isNotBlank()) {
                            onBoardingViewModel.setUserName(text.value)
                            onNext()
                        }
                    },
                contentAlignment = Alignment.Center
            ) {
                HagoMandalText(
                    modifier = Modifier.padding(vertical = 14.dp),
                    text = "이름 바꾸기",
                    style = t16
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewNameScreen() {
    HagoMandalTheme {
        NameScreen({})
    }
}
