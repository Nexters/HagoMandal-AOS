package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t12
import com.greedy0110.hagomandal.ui.theme.t20
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun TypeScreen(
    onClickCareer: () -> Unit,
    onClickFree: () -> Unit,
    onBoardingViewModel: OnBoardingViewModel = viewModel()
) {
    val name = onBoardingViewModel.userName.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.linearGradient(
                    listOf(Color(0xff202632), Color(0xff131b2b))
                )
            )
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Text(
            text = "좋아, ${name.value}!\n" +
                "그럼 어떤 목표를 세워볼까?",
            style = t24.copy(textAlign = TextAlign.Start)
        )
        Spacer(modifier = Modifier.height(80.dp))
        Row {
            val modifier = Modifier
                .weight(1f)
            TypeCard(modifier, forCareer = true, onClick = onClickCareer)
            Spacer(modifier = Modifier.width(15.dp))
            TypeCard(modifier, forCareer = false, onClick = onClickFree)
        }
    }
}

@Preview
@Composable
fun PreviewTypeScreen() {
    HagoMandalTheme {
        TypeScreen({}, {})
    }
}

@Composable
private fun TypeCard(
    modifier: Modifier = Modifier,
    forCareer: Boolean,
    onClick: () -> Unit = {},
) {
    val backgroundColor = when {
        forCareer -> Color(0xff3388ff)
        else -> Color(0xff00c2a2)
    }

    val title = when {
        forCareer -> "커리어"
        else -> "자유"
    }

    val desc = when {
        forCareer ->
            "지금은 디자이너, 개발자만\n" +
                "추천 목표를 받을 수 있어!"
        else ->
            "추천 목표는 없지만\n" +
                "대략적인 조언을 받을 수 있어!"
    }

    val iconId = when {
        forCareer -> R.drawable.gp_career
        else -> R.drawable.gp_rocket
    }

    Column(
        modifier = modifier
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .padding(vertical = 24.dp, horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(painter = painterResource(id = iconId), contentDescription = null)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = title, style = t20)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = desc, style = t12.copy(textAlign = TextAlign.Center))
    }
}

@Preview
@Composable
private fun PreviewTypeCard() {
    Row {
        TypeCard(forCareer = true)
        Spacer(modifier = Modifier.width(2.dp))
        TypeCard(forCareer = false)
    }
}
