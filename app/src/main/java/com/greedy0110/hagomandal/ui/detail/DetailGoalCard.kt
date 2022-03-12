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
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.GoalTextField
import com.greedy0110.hagomandal.ui.cardColorBrushes
import com.greedy0110.hagomandal.ui.theme.t20
import com.greedy0110.hagomandal.ui.theme.t24
import com.greedy0110.hagomandal.util.coloredShadow

@Composable
fun DetailGoalCard(
    modifier: Modifier = Modifier,
    brushColorIndex: Int,
    expanded: Boolean = false,
    title: String = "",
    onEditSubGoalClick: () -> Unit = {},
    onCardClick: () -> Unit = {},
    details: List<String> = emptyList(),
    onDetailFixed: (Int, String) -> Unit = { _, _ -> },
    isLastCard: Boolean = false,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {},
) {
    val titleGap = animateDpAsState(targetValue = if (expanded) 15.dp else 7.dp)
    val dotGap = animateDpAsState(targetValue = if (expanded) 20.dp else 4.dp)
    val bottomPadding = animateDpAsState(targetValue = if (expanded) 30.dp else 13.dp)

    val cardColorBrush = cardColorBrushes[brushColorIndex]

    Column(
        modifier = modifier
            .coloredShadow(
                color = Color.Black,
                alpha = 0.2f,
                borderRadius = 16.dp,
                shadowRadius = 20.dp,
                offsetY = (-16).dp,
                offsetX = 2.dp
            )
            .background(cardColorBrush, RoundedCornerShape(16.dp))
            .clickable { onCardClick() }
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

        val count = details.size
        // val focusManager = LocalFocusManager.current
        repeat(count) { index ->
            val isDoneable = index == details.lastIndex && isLastCard

            GoalTextField(
                value = details[index],
                onValueChange = { newValue -> onDetailFixed(index, newValue) },
                prefix = { Text(text = "• ", style = t20) },
                textStyle = t20,
                keyboardOptions = KeyboardOptions(imeAction = if (isDoneable) ImeAction.Done else ImeAction.Next),
                keyboardActions = KeyboardActions(
                    onNext = {
                        // 마지막 이면, 다음 카드로 넘어가야함.
                        if (index == details.lastIndex) onNext()
                        //TODO: 다음 detail TextField로 포커스가 넘어가야함.
                        // 근데... 현재focus가 어디있는지 확신할 수가 없네.
                        else {
                            // focusManager.moveFocus(FocusDirection.Down)
                        }
                    },
                    onDone = { onDone() },
                ),
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
            title = "문제 해결 능력 기르기"
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
