package com.greedy0110.hagomandal.ui.onboarding

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.ActionButton
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t16
import com.greedy0110.hagomandal.ui.theme.t24
import kotlinx.coroutines.launch

@OptIn(ExperimentalPagerApi::class)
@Composable
fun GuideScreen(
    onNext: () -> Unit = {}
) {
    val messages = stringArrayResource(id = R.array.guide_messages)
    val guideDrawables = listOf(
        R.drawable.guide1,
        R.drawable.guide2,
        R.drawable.guide3,
    )

    val pagerState = PagerState(0)
    var shownAllGuide by remember { mutableStateOf(false) }

    if (shownAllGuide.not()) {
        Column(
            modifier = Modifier
                .background(HagoMandalTheme.colors.background)
                .padding(top = 60.dp)
                .fillMaxSize(),
        ) {
            val coroutineScope = rememberCoroutineScope()
            val interactionSource = remember { MutableInteractionSource() }
            HorizontalPager(
                modifier = Modifier
                    .clickable(interactionSource = interactionSource, indication = null) {
                        coroutineScope.launch {
                            if (pagerState.currentPage != messages.lastIndex)
                                pagerState.scrollToPage(pagerState.currentPage + 1)
                            else shownAllGuide = true
                        }
                    },
                count = messages.size,
                state = pagerState,
            ) { page ->
                GuideContent(
                    modifier = Modifier
                        .fillMaxWidth(),
                    message = messages[page],
                    guideImage = guideDrawables[page]
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            ConstraintLayout(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                val (indicator, skipButton) = createRefs()

                DotIndicatorRows(
                    modifier = Modifier
                        .constrainAs(indicator) {
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom, margin = 80.dp)
                        }
                        .wrapContentSize(),
                    size = messages.size,
                    selectedInt = pagerState.currentPage
                )
                HagoMandalText(
                    modifier = Modifier
                        .constrainAs(skipButton) {
                            end.linkTo(parent.end, margin = 20.dp)
                            bottom.linkTo(parent.bottom, margin = 74.dp)
                        }
                        .clickable { shownAllGuide = true },
                    text = "SKIP",
                    style = t16,
                    color = HagoMandalTheme.colors.primary
                )
                Spacer(modifier = Modifier.height(76.dp))
            }
        }
    } else {
        GuideEndScreen(onClick = onNext)
    }
}

// @Preview(widthDp = 320, heightDp = 640)
// @Composable
// fun PreviewGuideScreen() {
//     HagoMandalTheme {
//         GuideScreen()
//     }
// }

@Composable
fun DotIndicatorRows(modifier: Modifier = Modifier, size: Int, selectedInt: Int) {
    Row(modifier) {
        repeat(size) { index ->
            DotIndicator(selected = index == selectedInt)
            if (index != size - 1) Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

// @Preview
// @Composable
// fun PreviewDotIndicatorRows() {
//     HagoMandalTheme {
//         DotIndicatorRows(size = 3, selectedInt = 1)
//     }
// }

@Composable
fun DotIndicator(selected: Boolean) {
    val dotColor = when {
        selected -> HagoMandalTheme.colors.primary
        else -> Color.White.copy(alpha = 0.4f)
    }

    Canvas(modifier = Modifier.size(6.dp)) {
        drawCircle(dotColor)
    }
}

// @Preview
// @Composable
// fun PreviewDotIndicator() {
//     Row {
//         DotIndicator(true)
//         DotIndicator(false)
//     }
//
// }

@Composable
private fun GuideContent(
    modifier: Modifier = Modifier,
    message: String = "만다라트는 목표 구체화에\n효과적인 발상기법이야!",
    @DrawableRes guideImage: Int = R.drawable.guide2
) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        HagoMandalText(text = message, style = t24)
        Image(
            modifier = Modifier.fillMaxWidth(),
            painter = painterResource(id = guideImage),
            contentDescription = null,
        )
    }
}

// @Preview
// @Composable
// private fun PreviewGuideContent() {
//     HagoMandalTheme {
//         GuideContent()
//     }
// }

@Composable
private fun GuideEndScreen(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(modifier.background(HagoMandalTheme.colors.background), horizontalAlignment = Alignment.CenterHorizontally) {
        Image(
            modifier = Modifier
                .padding(top = 241.dp)
                .size(112.dp, 95.dp),
            painter = painterResource(id = R.drawable.gp_happy_mandal),
            contentDescription = null
        )
        Spacer(modifier = Modifier.height(32.dp))
        HagoMandalText(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(id = R.string.go_with_mandalart),
            style = t24,
            textAlign = TextAlign.Center,
        )
        Spacer(modifier = Modifier.weight(1f))
        ActionButton(
            text = stringResource(R.string.ok_let_us_go),
            onClick = onClick
        )
        Spacer(modifier = Modifier.height(74.dp))
    }
}

@Preview
@Composable
private fun PreviewGuideEndScreen() {
    HagoMandalTheme {
        GuideEndScreen()
    }
}
