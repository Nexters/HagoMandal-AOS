package com.greedy0110.hagomandal.ui.subgoal

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.greedy0110.hagomandal.ui.GoalTextField
import com.greedy0110.hagomandal.ui.cardColorBrushes
import com.greedy0110.hagomandal.ui.theme.defaultFontFamily
import com.greedy0110.hagomandal.util.coloredShadow

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SubGoalCard(
    modifier: Modifier = Modifier,
    brushColorIndex: Int = 0,
    selected: Boolean = false,
    title: String = "",
    setTitle: (String) -> Unit = {},
    isDoneable: Boolean = false,
    onNext: () -> Unit = {},
    onDone: () -> Unit = {},
    focusRequester: FocusRequester = remember { FocusRequester() }
) {
    val maxTitleLength = 12

    val fontSizeSp by animateIntAsState(
        targetValue = if (selected) 20 else 15,
    )
    val textAlpha by animateFloatAsState(
        targetValue = if (selected) 1f else 0.3f,
    )

    val textStyle = TextStyle(
        fontWeight = FontWeight.W500,
        color = Color.White.copy(textAlpha),
        fontStyle = FontStyle.Normal,
        fontSize = fontSizeSp.sp,
        fontFamily = defaultFontFamily
    )

    Column(
        // TODO: shadow 설정은 어떻게?
        modifier = modifier
            .coloredShadow(
                color = Color.Black,
                alpha = 0.2f,
                borderRadius = 16.dp,
                shadowRadius = 20.dp,
                offsetY = (-16).dp,
                offsetX = 2.dp
            )
            .background(cardColorBrushes[brushColorIndex], shape = RoundedCornerShape(16.dp))
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        GoalTextField(
            modifier = Modifier.focusRequester(focusRequester),
            value = title,
            onValueChange = { if (it.length <= maxTitleLength) setTitle(it) },
            keyboardOptions = KeyboardOptions(imeAction = if (isDoneable) ImeAction.Done else ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { onNext() },
                onDone = { onDone() },
            ),
            maxLength = 12,
            textStyle = textStyle,
            hint = "세부목표",
            enabled = selected,
        )
        Spacer(modifier = Modifier.size(66.dp))
        Text(
            text = "${title.length}/$maxTitleLength",
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Preview(widthDp = 320, heightDp = 640, showBackground = true)
@Composable
fun PreviewSubGoalCard() {
    Column(modifier = Modifier.padding(20.dp)) {
        SubGoalCard(
            modifier = Modifier.fillMaxWidth(),
            brushColorIndex = 0,
            selected = true
        )
        SubGoalCard(modifier = Modifier.fillMaxWidth(), brushColorIndex = 1)
        SubGoalCard(
            modifier = Modifier.fillMaxWidth(),
            brushColorIndex = 2,
            selected = true
        )
        SubGoalCard(modifier = Modifier.fillMaxWidth(), brushColorIndex = 4)
        // SubGoalCard(
        //     modifier = Modifier.fillMaxWidth(),
        //     backgroundColor = Color(0xff5533c0),
        //     selected = true
        // )
        // SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff35ace4))
        // SubGoalCard(
        //     modifier = Modifier.fillMaxWidth(),
        //     backgroundColor = Color(0xff09c6a6),
        //     selected = true
        // )
        // SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xfff3c403))
    }
}
