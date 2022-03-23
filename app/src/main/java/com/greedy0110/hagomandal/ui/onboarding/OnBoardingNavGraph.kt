package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.greedy0110.hagomandal.ui.GoalScreen
import com.greedy0110.hagomandal.ui.GoalViewModel
import com.greedy0110.hagomandal.ui.share.ShareScreen

object OnBoardingDestinations {
    const val INTRO = "intro"
    const val MANDALART_GUIDE = "mandarlart_guide"
    const val NAME = "name"
    const val TYPE = "type"
    const val CAREER = "career"
    const val OTHER_JOB = "other_job"
    const val GET_STARTED = "get_started"
    const val GOAL = "goal"

    // TODO: on boarding 에 국한된 내용이 아니라... 앱 전체의 네비게이션이 되어버림.
    const val SHARE = "share"
    const val MODIFY_NAME = "modify_name"
}

@Composable
fun OnBoardingNavGraph(
    navController: NavHostController = rememberNavController(),
    startDestination: String = OnBoardingDestinations.INTRO,
    onBoardingViewModel: OnBoardingViewModel = viewModel()
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
                },
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(OnBoardingDestinations.TYPE) {
            TypeScreen(
                onClickCareer = {
                    navController.navigate(OnBoardingDestinations.CAREER)
                },
                onClickFree = {
                    navController.navigate(OnBoardingDestinations.GET_STARTED)
                },
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(OnBoardingDestinations.CAREER) {
            CareerScreen(
                onCareerSelected = {
                    navController.navigate(OnBoardingDestinations.GET_STARTED)
                },
                onClickOtherCareer = {
                    navController.navigate(OnBoardingDestinations.OTHER_JOB)
                },
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(OnBoardingDestinations.OTHER_JOB) {
            OtherJobScreen(
                onNext = {
                    navController.navigate(OnBoardingDestinations.GET_STARTED)
                },
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(OnBoardingDestinations.GET_STARTED) {
            GetStartedScreen(
                onNext = {
                    navController.navigate(OnBoardingDestinations.GOAL) {
                        popUpTo(OnBoardingDestinations.INTRO) {
                            inclusive = true
                        }
                    }
                },
                onBoardingViewModel = onBoardingViewModel
            )
        }
        composable(OnBoardingDestinations.GOAL) {
            val goalViewModel: GoalViewModel = hiltViewModel()
            GoalScreen(
                onSubmit = {
                    // TODO: 강조 on 상태로 켜야한다.
                    navController.navigate(OnBoardingDestinations.SHARE)
                },
                goalViewModel = goalViewModel
            )
        }
        composable(OnBoardingDestinations.SHARE) {
            val goalViewModel: GoalViewModel = hiltViewModel()
            ShareScreen(
                goalViewModel = goalViewModel,
                emphasize = true,
                onModifyNameClick = {
                    navController.navigate(OnBoardingDestinations.MODIFY_NAME)
                },
                onDeleteGoalsClick = {
                    // TODO: 목표 제거하고 골 화면으로 다시 이동
                    navController.navigate(OnBoardingDestinations.GOAL)
                }
            )
        }
        composable(OnBoardingDestinations.MODIFY_NAME) {
            NameScreen(
                onBoardingViewModel = onBoardingViewModel,
                onNext = { navController.popBackStack() },
                isUpdating = true,
            )
        }
    }
}
