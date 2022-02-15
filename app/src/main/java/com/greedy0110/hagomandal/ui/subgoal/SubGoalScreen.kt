package com.greedy0110.hagomandal.ui.subgoal

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.accompanist.insets.imePadding
import com.greedy0110.hagomandal.ui.ColorChooser
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.backgroundColor

private val defaultFontFamily: FontFamily = FontFamily.SansSerif
private val t24 = TextStyle(
    fontWeight = FontWeight.W700,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 24.sp,
    fontFamily = defaultFontFamily,
    lineHeight = 1.5.sp,
    letterSpacing = (-0.3).sp
)

private val t14 = TextStyle(
    fontWeight = FontWeight.W400,
    color = Color.White,
    fontSize = 14.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

@Composable
fun SubGaolScreen(
    modifier: Modifier = Modifier,
    userName: String,
    mainGoal: String,
    subGoals: SnapshotStateList<SubGoal> = mutableStateListOf()
) {
    val (selectedIndex, setSelectedIndex) = remember { mutableStateOf(0) }

    Scaffold(
        modifier = modifier,
        backgroundColor = backgroundColor
    ) {
        Column(
            Modifier
                .padding(it)
                .padding(top = 32.dp)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 20.dp, end = 20.dp)
            ) {
                Text(
                    text = "${userName}님의 핵심목표",
                    style = t14,
                    color = Color.White.copy(alpha = 0.5f)
                )
                Spacer(modifier = Modifier.size(4.dp))
                Text(
                    text = mainGoal,
                    style = t24,
                    color = Color.White
                )
                Spacer(modifier = Modifier.size(22.dp))
                SubGoalCardList(
                    subGoals = subGoals,
                    selectedIndex = selectedIndex,
                    setSelectedIndex = setSelectedIndex
                )
            }
            ColorChooser(
                modifier = Modifier
                    .fillMaxWidth()
                    // .navigationBarsWithImePadding() //TODO: 뭔차이?
                    .imePadding() // TODO: appcompanist 의 imePadding과 foundation의 것은 무엇이 다른가?
                    .background(backgroundColor),
                selectedIndex = subGoals[selectedIndex].colorIndex,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                onChooseColor = { colorIndex ->
                    if (subGoals[selectedIndex].colorIndex == colorIndex) return@ColorChooser
                    subGoals[selectedIndex] = subGoals[selectedIndex].copy(colorIndex = colorIndex)
                }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSubGaolScreen() {
    val subGoals: SnapshotStateList<SubGoal> = remember {
        val subGoals = IntRange(0, 3)
            .map { SubGoal(title = "", colorIndex = it) }
            .toTypedArray()
        mutableStateListOf(*subGoals)
    }
    HagoMandalTheme {
        SubGaolScreen(userName = "신승민", mainGoal = "주식부자가 된 나.", subGoals = subGoals)
    }
}
