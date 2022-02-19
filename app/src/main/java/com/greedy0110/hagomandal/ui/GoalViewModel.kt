package com.greedy0110.hagomandal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedy0110.hagomandal.usecase.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class GoalViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userName: StateFlow<String> = flow {
        emit(userRepository.getUserName())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")
}
