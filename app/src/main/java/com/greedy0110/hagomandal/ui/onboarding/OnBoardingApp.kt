package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

@Composable
fun OnBoardingApp() {
    HagoMandalTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                systemUiController.setSystemBarsColor(
                    Color.Transparent,
                    darkIcons = darkIcons,
                    isNavigationBarContrastEnforced = false,
                )
            }

            val scaffoldState = rememberScaffoldState()

            Scaffold(scaffoldState = scaffoldState) {
                OnBoardingNavGraph()
            }
        }
    }
}
