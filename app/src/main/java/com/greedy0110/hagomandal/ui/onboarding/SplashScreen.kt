package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun SplashScreen() {
    Box(
        modifier = Modifier.fillMaxSize().background(HagoMandalTheme.colors.background),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.size(228.dp, 300.dp),
            painter = painterResource(id = R.drawable.splash),
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun PreviewSplashScreen() {
    HagoMandalTheme {
        SplashScreen()
    }
}
