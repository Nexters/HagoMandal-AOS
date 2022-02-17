package com.greedy0110.hagomandal.ui.detail

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.GoalTextField
import com.greedy0110.hagomandal.ui.cardColorBrushes
import com.greedy0110.hagomandal.ui.theme.t20
import com.greedy0110.hagomandal.ui.theme.t24
import com.greedy0110.hagomandal.util.`if`

@Composable
fun DetailGoalCard(
    modifier: Modifier = Modifier,
    brushColorIndex: Int,
    expanded: Boolean = false,
    title: String = "",
    onEditSubGoalClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
    details: SnapshotStateList<String> = mutableStateListOf(),
    details2: List<String> = emptyList(),
    setDetails2: (List<String>) -> Unit = {}
) {
    val titleGap = animateDpAsState(targetValue = if (expanded) 15.dp else 7.dp)
    val dotGap = animateDpAsState(targetValue = if (expanded) 15.dp else 4.dp)
    val bottomPadding = animateDpAsState(targetValue = if (expanded) 30.dp else 13.dp)

    val cardColorBrush = cardColorBrushes[brushColorIndex]

    Column(
        modifier = modifier
            .background(cardColorBrush, RoundedCornerShape(16.dp))
            .`if`(expanded.not()) {
                clickable { onCardClick() }
            }
            .padding(start = 24.dp, end = 24.dp, top = 20.dp, bottom = bottomPadding.value),
        horizontalAlignment = Alignment.Start
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = title, style = t24)
            Spacer(modifier = Modifier.width(4.dp))
            Icon(
                modifier = Modifier
                    .size(24.dp)
                    .clickable { onEditSubGoalClick() },
                painter = painterResource(id = R.drawable.ic_edit_1),
                tint = Color.White,
                contentDescription = null
            )
        }
        Spacer(modifier = Modifier.height(titleGap.value))

        val count = details2.size
        repeat(count) { index ->
            GoalTextField(
                value = details2[index],
                onValueChange = { newValue ->
                    details2
                        .toMutableList()
                        .also { it[index] = newValue }
                        .also { setDetails2(it.toList()) }
                },
                prefix = { Text(text = "• ", style = t20) },
                textStyle = t20,
            )
            if (index != count - 1) Spacer(modifier = Modifier.height(dotGap.value))
        }
    }
}

@Preview
@Composable
fun PreviewDetailCard() {
    Column(modifier = Modifier.padding(20.dp)) {
        DetailGoalCard(
            modifier = Modifier.fillMaxWidth(),
            brushColorIndex = 0,
            expanded = true,
            title = "문제 해결 능력 기르기",
            details = remember { mutableStateListOf("아아아", "이이이", "호호호") }
        )
        DetailGoalCard(modifier = Modifier.fillMaxWidth(), brushColorIndex = 1)
        DetailGoalCard(
            modifier = Modifier.fillMaxWidth(),
            brushColorIndex = 2,
            expanded = true
        )
        DetailGoalCard(modifier = Modifier.fillMaxWidth(), brushColorIndex = 4)
    }
}
