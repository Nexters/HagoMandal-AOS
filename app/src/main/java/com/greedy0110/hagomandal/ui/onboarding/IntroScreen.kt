package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t24
import com.greedy0110.hagomandal.util.`if`

@Composable
fun IntroScreen(
    onWhatIsMandalartClick: () -> Unit = {},
    onNext: () -> Unit = {},
) {
    val messages = stringArrayResource(id = R.array.intro_messages)

    var currentPage by remember { mutableStateOf(0) }
    val message = messages[currentPage]
    val isLastPage = currentPage == messages.lastIndex

    ConstraintLayout(
        modifier = Modifier
            .`if`(isLastPage.not()) {
                clickable { currentPage++ }
            }
            .fillMaxSize()
            .background(HagoMandalTheme.colors.background)
    ) {
        val (text, buttons) = createRefs()

        HagoMandalText(
            modifier = Modifier
                .constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start, margin = 20.dp)
                    end.linkTo(parent.end, margin = 20.dp)
                },
            text = message,
            style = t24
        )
        if (isLastPage) {
            Column(
                modifier = Modifier.constrainAs(buttons) {
                    bottom.linkTo(parent.bottom, margin = 70.dp)
                }
            ) {
                ActionButton(
                    text = stringResource(R.string.what_is_mandalart),
                    onClick = onWhatIsMandalartClick
                )
                Spacer(modifier = Modifier.size(16.dp))
                HagoMandalText(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .clickable { onNext() },
                    text = stringResource(R.string.ok_let_us_do_it),
                    style = t16
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewIntroScreen() {
    HagoMandalTheme {
        IntroScreen()
    }
}
