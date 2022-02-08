package com.greedy0110.hagomandal.ui

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateIntAsState
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.ExperimentalUnitApi
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val defaultFontFamily: FontFamily = FontFamily.SansSerif
private val t20 = TextStyle(
    fontWeight = FontWeight.W700,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 20.sp,
    fontFamily = defaultFontFamily
)

@OptIn(ExperimentalUnitApi::class)
@Composable
fun SubGoalCard(
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color(0xfff3c403),
    selected: Boolean = false,
) {
    // TODO: text size가 선택적으로 변경되어야한다.
    val (title, setTitle) = rememberSaveable { mutableStateOf("") }
    val maxTitleLength = 12

    // TODO: 애니메이션 처리 고도화.
    val animationDuration = 1000
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
            .background(backgroundColor, shape = RoundedCornerShape(16.dp))
            .padding(top = 16.dp, start = 20.dp, end = 20.dp, bottom = 20.dp),
        horizontalAlignment = Alignment.Start
    ) {
        // TODO: hint 설정은 어떻게?
        // TODO: 기본 TextField 설정이 이렇게 어려울 수 가 있나?...
        BasicTextField(
            value = title,
            onValueChange = { if (it.length <= maxTitleLength) setTitle(it) },
            // // TODO: 마지막 요소는 imeAction이 다를 것.
            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
            keyboardActions = KeyboardActions(
                onNext = { },
                onDone = { },
            ),
            // // TODO: textStyle 지정은 전체적으로 고려하기
            textStyle = textStyle,
            cursorBrush = SolidColor(Color.White),
            decorationBox = {
                if (title.isEmpty()) Text("세부목표", style = textStyle)
                else Text(title, style = textStyle)
            }
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
        SubGoalCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xff5533c0),
            selected = true
        )
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xff35ace4))
        SubGoalCard(
            modifier = Modifier.fillMaxWidth(),
            backgroundColor = Color(0xff09c6a6),
            selected = true
        )
        SubGoalCard(modifier = Modifier.fillMaxWidth(), backgroundColor = Color(0xfff3c403))
    }
}
