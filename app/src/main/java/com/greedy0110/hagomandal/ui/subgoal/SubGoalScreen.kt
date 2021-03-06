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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.insets.imePadding
import com.greedy0110.hagomandal.ui.ColorChooser
import com.greedy0110.hagomandal.ui.SubGoal
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.ui.theme.t14
import com.greedy0110.hagomandal.ui.theme.t24

@Composable
fun SubGaolScreen(
    modifier: Modifier = Modifier,
    subGoals: List<SubGoal> = emptyList(),
    setSubGoal: (Int, String) -> Unit = { _, _ -> },
    setSubGoalColor: (Int, Int) -> Unit = { _, _ -> },
    userName: String,
    mainGoal: String,
    onDone: () -> Unit = {},
    isEndScroll: Boolean = true,
) {
    val (selectedIndex, setSelectedIndex) = remember { mutableStateOf(subGoals.lastIndex) }

    Scaffold(
        modifier = modifier,
        backgroundColor = HagoMandalTheme.colors.background
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
                    setSubGoal = { index, raw -> setSubGoal(index, raw) },
                    selectedIndex = selectedIndex,
                    setSelectedIndex = setSelectedIndex,
                    onNext = { index -> setSelectedIndex(index + 1) },
                    onDone = { onDone() },
                    isEndScroll = isEndScroll
                )
            }
            ColorChooser(
                modifier = Modifier
                    .fillMaxWidth()
                    // .navigationBarsWithImePadding() //TODO: 뭔차이?
                    .imePadding() // TODO: appcompanist 의 imePadding과 foundation의 것은 무엇이 다른가?
                    .background(HagoMandalTheme.colors.background),
                selectedIndex = subGoals[selectedIndex].colorIndex,
                contentPadding = PaddingValues(horizontal = 8.dp, vertical = 6.dp),
                onChooseColor = { colorIndex -> setSubGoalColor(selectedIndex, colorIndex) }
            )
        }
    }
}

@Preview
@Composable
fun PreviewSubGaolScreen() {
    val subGoals = IntRange(0, 3)
        .map { SubGoal(title = "", colorIndex = it) }
    HagoMandalTheme {
        SubGaolScreen(userName = "신승민", mainGoal = "주식부자가 된 나.", subGoals = subGoals)
    }
}
