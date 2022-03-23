package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.insets.ProvideWindowInsets
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.greedy0110.hagomandal.ui.theme.HagoMandalTheme
import com.greedy0110.hagomandal.usecase.Job
import com.greedy0110.hagomandal.usecase.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(FlowPreview::class)
@HiltViewModel
class OnBoardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _userName: MutableStateFlow<String> = MutableStateFlow("")
    val userName: StateFlow<String> = _userName

    val needToShowGuide = flow<Boolean?> {
        emit(!userRepository.isShownOnBoarding())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)

    init {

        // stateFlow 라서 최초 값이 있는데 이 최초값을 무시하자.
        // 안그러면, 항상 empty string이 세팅된다.
        userName
            .drop(1)
            .debounce(1000L)
            .onEach { userRepository.setName(it) }
            .launchIn(viewModelScope)
    }

    fun shownGuide() {
        viewModelScope.launch {
            userRepository.shownOnBoarding()
        }
    }

    fun setUserName(userName: String) {
        _userName.tryEmit(userName)
    }

    fun setJob(sector: String, subSector: String) {
        viewModelScope.launch {
            userRepository.setJob(Job(sector, subSector))
        }
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

            var showSplash by remember { mutableStateOf(true) }
            val goToGuide = onBoardingViewModel.needToShowGuide.collectAsState()
            val startDestination = when (goToGuide.value) {
                null -> ""
                true -> OnBoardingDestinations.INTRO
                else -> OnBoardingDestinations.GOAL
            }

            when {
                showSplash -> SplashScreen()
                startDestination.isNotBlank() -> OnBoardingNavGraph(startDestination = startDestination)
            }

            LaunchedEffect(key1 = Unit) {
                delay(1000)
                showSplash = false
            }
        }
    }
}
