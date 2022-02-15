package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.usecase.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository
): ViewModel() {

    suspend fun needToShowGuide() : Boolean {
        return !userRepository.isShownOnBoarding()
    }
}

@Composable
fun OnBoardingApp(
    onBoardingViewModel: OnBoardingViewModel = viewModel()
) {
    HagoMandalTheme {
        ProvideWindowInsets {
            val systemUiController = rememberSystemUiController()
            val darkIcons = MaterialTheme.colors.isLight
            SideEffect {
                // TODO: 왜 투명 안되냐.
                systemUiController.setSystemBarsColor(
                    Color.Transparent,
                    darkIcons = darkIcons,
                    isNavigationBarContrastEnforced = false,
                )
            }

            var startDestination by remember { mutableStateOf("") }
            LaunchedEffect(key1 = onBoardingViewModel) {
                if (onBoardingViewModel.needToShowGuide()) {
                    startDestination = OnBoardingDestinations.INTRO
                } else {
                    startDestination = OnBoardingDestinations.GOALf
                }
            }

            if (startDestination.isNotBlank()) {
                OnBoardingNavGraph(startDestination = startDestination)
            }
        }
    }
}
