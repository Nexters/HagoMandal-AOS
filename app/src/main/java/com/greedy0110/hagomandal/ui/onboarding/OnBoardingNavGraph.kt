package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.greedy0110.hagomandal.R
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme

object OnBoardingDestinations {
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
    startDestination: String = "${OnBoardingDestinations.DISPLAY}/{$DISPLAY_RES_ID}"
) {

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
    }
}

@Composable
fun NicknameScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Red)
    )
}

@Preview(showBackground = true, widthDp = 150, heightDp = 150)
@Composable
fun PreviewNicknameScreen() {
    HagoMandalTheme {
        NicknameScreen()
    }
}

@Composable
fun DisplayScreen(resId: Int) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .wrapContentSize(Alignment.Center)
    ) {
        Image(
            modifier = Modifier.size(100.dp),
            painter = painterResource(id = resId),
            contentDescription = null,
            colorFilter = ColorFilter.tint(Color.Red),
        )
    }
}

@Preview(showBackground = true, widthDp = 150, heightDp = 150)
@Composable
fun PreviewDisplayScreen() {
    HagoMandalTheme {
        DisplayScreen(resId = R.drawable.ic_launcher_foreground)
    }
}
