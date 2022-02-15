package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material.Icon
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t14
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t24

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
            .fillMaxSize()
            // TODO: 임의의 각도를 어떻게 지정하지?
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xff202632), Color(0xff131b2b))
                )
            )
    ) {
        val (text, nudge, buttons) = createRefs()

        Text(
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
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .wrapContentSize()
                        .clickable { onNext() },
                    text = stringResource(R.string.ok_let_us_do_it),
                    style = t16
                )
            }
        } else {
            TouchNudge(
                modifier = Modifier
                    .constrainAs(nudge) {
                        start.linkTo(parent.start, margin = 20.dp)
                        end.linkTo(parent.end, margin = 20.dp)
                        bottom.linkTo(parent.bottom, margin = 70.dp)
                    }
                    .clickable { currentPage += 1 }
            )
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

@Composable
fun TouchNudge(modifier: Modifier = Modifier) {

    Column(modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = stringResource(R.string.touch_to_next), style = t14)
        Spacer(modifier = Modifier.size(7.dp))
        Icon(
            painter = painterResource(id = R.drawable.ic_down_arrow),
            contentDescription = null,
            tint = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewTouchNudge() {
    HagoMandalTheme {
        TouchNudge()
    }
}
