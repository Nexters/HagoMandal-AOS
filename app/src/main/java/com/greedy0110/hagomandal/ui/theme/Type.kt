package com.greedy0110.hagomandal.ui.theme

import androidx.compose.material.Typography
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

// Set of Material typography styles to start with
val Typography = Typography(
    body1 = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp
    )
    /* Other default text styles to override
    button = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.W500,
        fontSize = 14.sp
    ),
    caption = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp
    )
    */
)

// TODO: Noto Sans KR 적용하기
val defaultFontFamily: FontFamily = FontFamily.SansSerif
val t12 = TextStyle(
    fontWeight = FontWeight.W400,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 12.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

val t10 = TextStyle(
    fontWeight = FontWeight.W500,
    color = Color.White,
    fontSize = 10.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

val t16 = TextStyle(
    fontWeight = FontWeight.W500,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 16.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

val t18 = TextStyle(
    fontWeight = FontWeight.W500,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 18.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

val t14 = TextStyle(
    fontWeight = FontWeight.W400,
    color = Color.White,
    fontSize = 14.sp,
    fontFamily = defaultFontFamily,
    letterSpacing = (-0.3).sp
)

val t20 = TextStyle(
    fontWeight = FontWeight.W700,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 20.sp,
    fontFamily = defaultFontFamily,
    lineHeight = 24.sp,
    letterSpacing = (-0.3).sp,
    textAlign = TextAlign.Center
)

val t24 = TextStyle(
    fontWeight = FontWeight.W700,
    color = Color.White,
    fontStyle = FontStyle.Normal,
    fontSize = 24.sp,
    fontFamily = defaultFontFamily,
    lineHeight = 28.sp,
    letterSpacing = (-0.3).sp,
    textAlign = TextAlign.Center
)
