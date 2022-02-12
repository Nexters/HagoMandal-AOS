package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController

object OnBoardingDestinations {
    const val INTRO = "intro"
    const val MANDALART_GUIDE = "mandarlart_guide"
    const val NAME = "name"
    const val TYPE = "type"
    const val CAREER = "career"
    const val OTHER_JOB = "other_job"
    const val GET_STARTED = "get_started"
}

@Composable
fun OnBoardingNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = OnBoardingDestinations.INTRO
) {
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(OnBoardingDestinations.INTRO) {
            IntroScreen(
                onWhatIsMandalartClick = {
                    Unit
                },
                onNext = {
                    Unit
                }
            )
        }
    }
}
