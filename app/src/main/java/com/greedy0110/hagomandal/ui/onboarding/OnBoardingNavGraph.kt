package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greedy0110.hagomandal.R

object OnBoardingDestinations {
    const val INTRO = "intro"
    const val DISPLAY = "display"
    const val NICKNAME = "nickname"
    const val DEADLINE = "deadline"
    const val GOAL_TYPE = "goal_type"
    const val MAIN_GOAL = "main_goal"
}

const val DISPLAY_RES_ID = "res_id"

@Composable
fun OnBoardingNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = OnBoardingDestinations.INTRO
) {

    // TODO: 테스트용
    val startDestination = OnBoardingDestinations.NICKNAME

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(
            route = "${OnBoardingDestinations.DISPLAY}/{$DISPLAY_RES_ID}",
            arguments = listOf(navArgument(DISPLAY_RES_ID) { type = NavType.IntType })
        ) { backStackEntry ->
            backStackEntry.arguments
                ?.getInt(DISPLAY_RES_ID, -1)
                .takeIf { it != -1 }
                .also { resId -> DisplayScreen(resId ?: R.drawable.ic_launcher_foreground) }
        }
        composable(OnBoardingDestinations.NICKNAME) {
            NicknameScreen()
        }
        composable(OnBoardingDestinations.DEADLINE) {
            NicknameScreen()
        }
    }
}
