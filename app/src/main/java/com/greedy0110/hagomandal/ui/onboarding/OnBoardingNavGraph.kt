package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.greedy0110.hagomandal.ui.GoalScreen

object OnBoardingDestinations {
    const val INTRO = "intro"
    const val MANDALART_GUIDE = "mandarlart_guide"
    const val NAME = "name"
    const val TYPE = "type"
    const val CAREER = "career"
    const val OTHER_JOB = "other_job"
    const val GET_STARTED = "get_started"
    const val GOAL = "goal"
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
                    navController.navigate(OnBoardingDestinations.MANDALART_GUIDE)
                },
                onNext = {
                    navController.navigate(OnBoardingDestinations.NAME)
                }
            )
        }
        composable(OnBoardingDestinations.MANDALART_GUIDE) {
            GuideScreen(
                onNext = {
                    navController.navigate(OnBoardingDestinations.NAME)
                }
            )
        }
        composable(OnBoardingDestinations.NAME) {
            NameScreen(
                onNext = {
                    navController.navigate(OnBoardingDestinations.TYPE)
                }
            )
        }
        composable(OnBoardingDestinations.TYPE) {
            TypeScreen(
                onClickCareer = {
                    navController.navigate(OnBoardingDestinations.CAREER)
                },
                onClickFree = {
                    navController.navigate(OnBoardingDestinations.GET_STARTED)
                }
            )
        }
        composable(OnBoardingDestinations.CAREER) {
            CareerScreen(
                onCareerSelected = {
                    navController.navigate(OnBoardingDestinations.GET_STARTED)
                },
                onClickOtherCareer = {
                    navController.navigate(OnBoardingDestinations.OTHER_JOB)
                }
            )
        }
        composable(OnBoardingDestinations.OTHER_JOB) {
            OtherJobScreen(
                onNext = {
                    navController.navigate(OnBoardingDestinations.GET_STARTED)
                }
            )
        }
        composable(OnBoardingDestinations.GET_STARTED) {
            val getStartedViewModel: GetStartedViewModel = hiltViewModel()
            GetStartedScreen(
                onNext = {
                    navController.navigate(OnBoardingDestinations.GOAL)
                },
                getStartedViewModel
            )
        }
        composable(OnBoardingDestinations.GOAL) {
            GoalScreen()
        }
    }
}
