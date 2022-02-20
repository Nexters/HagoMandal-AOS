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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.SingleTextField
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun OtherJobScreen(
    onNext: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = viewModel()
) {
    val (text, setText) = remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HagoMandalTheme.colors.background)
    ) {
        val defaultModifier = Modifier.padding(horizontal = 20.dp)

        Spacer(modifier = Modifier.height(60.dp))
        HagoMandalText(
            modifier = defaultModifier,
            text = "아직은 없지만\n알려준다면 열심히 준비해볼게!",
            style = t24,
            textAlign = TextAlign.Start
        )
        Spacer(modifier = Modifier.height(48.dp))
        SingleTextField(
            modifier = defaultModifier
                .fillMaxWidth(),
            text = text,
            onTextChanged = setText,
            hint = "예시) 유튜버",
            maxLength = 20,
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onBoardingViewModel.setJob(text, "")
                onNext()
            })
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButton(text = "꼭 준비해줘!", onClick = onNext)
    }
}

@Preview
@Composable
fun PreviewOtherJobScreen() {
    HagoMandalTheme {
        OtherJobScreen({})
    }
}
