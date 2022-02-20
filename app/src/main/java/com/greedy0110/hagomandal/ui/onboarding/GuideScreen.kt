package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
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

// TODO: 디자인 고도화 필요.
@OptIn(ExperimentalPagerApi::class)
@Composable
fun GuideScreen(
    onNext: () -> Unit = {}
) {
    val messages = stringArrayResource(id = R.array.guide_messages)

    val pagerState = PagerState(0)
    var shownAllGuide by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .background(HagoMandalTheme.colors.surface)
            .fillMaxSize(),
        verticalArrangement = Arrangement.Bottom,
    ) {
        val coroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .height(332.dp)
                .background(HagoMandalTheme.colors.background),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (shownAllGuide.not()) {
                HorizontalPager(
                    modifier = Modifier,
                    count = messages.size,
                    state = pagerState,
                ) {
                    val interactionSource = remember { MutableInteractionSource() }
                    Text(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(56.dp)
                            .clickable(interactionSource = interactionSource, indication = null) {
                                coroutineScope.launch {
                                    if (currentPage != messages.lastIndex)
                                        pagerState.scrollToPage(currentPage + 1)
                                }
                            },
                        text = messages[pagerState.currentPage],
                        style = t24
                    )
                }
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
                }
            } else {
                Column {
                    HagoMandalText(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(56.dp),
                        text = stringResource(id = R.string.go_with_mandalart),
                        style = t24,
                    )
                    Spacer(modifier = Modifier.weight(1f))
                    ActionButton(
                        modifier = Modifier.padding(bottom = 74.dp),
                        text = stringResource(R.string.ok_let_us_go),
                        onClick = onNext
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewGuideScreen() {
    HagoMandalTheme {
        GuideScreen()
    }
}

@Composable
fun DotIndicatorRows(modifier: Modifier = Modifier, size: Int, selectedInt: Int) {
    Row(modifier) {
        repeat(size) { index ->
            DotIndicator(selected = index == selectedInt)
            if (index != size - 1) Spacer(modifier = Modifier.width(8.dp))
        }
    }
}

@Preview
@Composable
fun PreviewDotIndicatorRows() {
    HagoMandalTheme {
        DotIndicatorRows(size = 3, selectedInt = 1)
    }
}

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

@Preview
@Composable
fun PreviewDotIndicator() {
    Row {
        DotIndicator(true)
        DotIndicator(false)
    }
}
