package com.greedy0110.hagomandal.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Purple200,
    primaryVariant = Purple700,
    secondary = Teal200
)

private val LightColorPalette = lightColors(
    primary = Purple500,
    primaryVariant = Purple700,
    secondary = Teal200

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Immutable
data class HagoColors(
    val background: Color = Color(0xff141414),
    val onBackground: Color = Color.White,
    val primary: Color = Color(0xff00c25e),
    val onPrimary: Color = Color.White,
    val secondary: Color = Color(0xff333333),
    val onSecondary: Color = Color.White,
    val surface: Color = Color.White,
    val onSurface: Color = Color(0xff202632),
)

val LocalHagoColors = staticCompositionLocalOf {
    HagoColors()
}

@Composable
fun HagoMandalTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    CompositionLocalProvider(LocalHagoColors provides HagoColors()) {
        MaterialTheme(
            colors = colors,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object HagoMandalTheme {
    val colors: HagoColors
        @Composable
        get() = LocalHagoColors.current
}