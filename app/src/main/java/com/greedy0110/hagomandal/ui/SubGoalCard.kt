package com.greedy0110.hagomandal.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SubGoalCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xfff3c403)
) {
    // TODO: text size가 선택적으로 변경되어야한다.
    val (title, setTitle) = rememberSaveable { mutableStateOf("") }
    val maxTitleLength = 12

    Column(
        // TODO: shadow 설정은 어떻게?
        modifier = modifier
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // TODO: hint 설정은 어떻게?
        BasicTextField(
            value = title,
            onValueChange = setTitle,
            // TODO: 마지막 요소는 imeAction이 다를 것.
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { },
                onDone = { },
            ),
            // TODO: textStyle 지정은 전체적으로 고려하기
            textStyle = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.W500,
                fontSize = TextUnit(20f, TextUnitType.Sp)
            ),
            cursorBrush = SolidColor(Color.White),

        )
        Spacer(modifier = Modifier.size(66.dp))
        Text(
            text = "${title.length}/$maxTitleLength",
//            fontSize = 12.dp,
            color = Color.White.copy(alpha = 0.8f)
        )
    }
}

@Preview(widthDp = 320, heightDp = 640)
@Composable
fun PreviewSubGoalCard() {
    Column(modifier = Modifier.padding(20.dp)) {
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff5533c0))
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff35ace4))
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff09c6a6))
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xfff3c403))
    }
}
