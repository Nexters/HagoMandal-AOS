package com.greedy0110.hagomandal.ui.share

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.DetailGoal
import com.greedy0110.hagomandal.ui.GoalViewModel
import com.greedy0110.hagomandal.ui.HagoMandalText
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t14
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun ShareCaptureScreen(
    modifier: Modifier = Modifier,
    goalViewModel: GoalViewModel = viewModel(),
) {

    ShareCaptureScreen(
        modifier = modifier,
        userName = goalViewModel.userName.collectAsState().value,
        mainGoal = goalViewModel.mainGoal.collectAsState().value,
        detailGoals = goalViewModel.detailGoal.collectAsState().value
    )
}

@Composable
fun ShareCaptureScreen(
    modifier: Modifier = Modifier,
    userName: String = "",
    mainGoal: String = "",
    detailGoals: List<DetailGoal> = emptyList(),
) {
    ConstraintLayout(modifier = modifier) {
        val (background, content) = createRefs()

        Image(
            modifier = Modifier.constrainAs(background) {
                centerTo(content)
                width = Dimension.fillToConstraints
                height = Dimension.fillToConstraints
            },
            painter = painterResource(id = R.drawable.background),
            contentDescription = null,
            contentScale = ContentScale.Crop
        )
        Column(
            modifier = Modifier
                .constrainAs(content) { centerTo(parent) }
                .wrapContentHeight()
                .padding(horizontal = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(64.dp))
            HagoMandalText(
                text = "${userName}?????? ????????????",
                style = t14,
                color = HagoMandalTheme.colors.onBackground.copy(alpha = 0.5f)
            )
            Spacer(modifier = Modifier.height(4.dp))
            HagoMandalText(text = mainGoal, style = t24)
            Spacer(modifier = Modifier.height(24.dp))
            ShareCardList(details = detailGoals)
            Spacer(modifier = Modifier.height(118.dp))
            Image(

                modifier = Modifier.width(76.dp),
                painter = painterResource(id = R.drawable.splash),
                contentDescription = null
            )
            Spacer(modifier = Modifier.height(64.dp))
        }
    }
}

@Preview
@Composable
fun PreviewShareCaptureScreen() {
    val details = listOf(
        DetailGoal(
            "?????? ????????????",
            listOf("?????? ??? 6??? ??????", "?????? ?????? ??????????????? ?????????", "?????? ?????? ????????? ????????????", ""),
            0
        ),
        DetailGoal("?????? ????????????", listOf("?????? ??? 2??? ??????", "", "", ""), 1),
        DetailGoal("????????? ????????????", listOf("????????? ??? ?????? ?????????", "????????? ?????? ????????? ?????????", "", ""), 2),
        DetailGoal("????????????", listOf("?????? ?????? ???????????? ??????", "", "", ""), 3)
    )

    HagoMandalTheme {
        ShareCaptureScreen(
            userName = "?????????",
            mainGoal = "8?????? ???????????? 1??????",
            detailGoals = details
        )
    }
}
