package com.greedy0110.hagomandal.ui.onboarding

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
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
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.debounce
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

        userName
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

            val goToGuide = onBoardingViewModel.needToShowGuide.collectAsState()
            val startDestination = when (goToGuide.value) {
                null -> ""
                true -> OnBoardingDestinations.INTRO
                else -> OnBoardingDestinations.GOAL
            }
            if (startDestination.isNotBlank()) {
                OnBoardingNavGraph(startDestination = startDestination)
            }
        }
    }
}
