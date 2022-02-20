package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.background
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.SingleTextField
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun NameScreen(
    onNext: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = viewModel(),
) {
    val text = onBoardingViewModel.userName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HagoMandalTheme.colors.background)
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        HagoMandalText(
            text = "아 맞다! 그러고 보니 \n내 이름이... 뭐더라..?",
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
}

@Preview
@Composable
fun PreviewNameScreen() {
    HagoMandalTheme {
        NameScreen({})
    }
}
