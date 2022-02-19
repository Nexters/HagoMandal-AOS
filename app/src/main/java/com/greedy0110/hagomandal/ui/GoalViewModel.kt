package com.greedy0110.hagomandal.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedy0110.hagomandal.usecase.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// TODO: goal 관련 데이터 통합 관리하자. 지금 화면마다 관리하니까 주고 받고 난리도 아님...
@HiltViewModel
class GoalViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    val userName: StateFlow<String> = flow {
        emit(userRepository.getUserName())
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), "")

    private val _mainGoal: MutableStateFlow<String> = MutableStateFlow("")
    val mainGoal: StateFlow<String> = _mainGoal

    private val _subGoals: MutableStateFlow<List<SubGoal>> =
        MutableStateFlow(IntRange(0, 3).map { SubGoal(title = "", colorIndex = it) })
    val subGoals: StateFlow<List<SubGoal>> = _subGoals

    private val _detailGoals: MutableStateFlow<List<List<String>>> = MutableStateFlow(
        listOf(
            listOf("", "", "", ""),
            listOf("", "", "", ""),
            listOf("", "", "", ""),
            listOf("", "", "", ""),
        )
    )

    val detailGoal: StateFlow<List<DetailGoal>> = combine(
        _subGoals,
        _detailGoals
    ) { subs, details ->
        require(subs.size == details.size)
        subs.zip(details) { sub, detail ->
            DetailGoal(
                title = sub.title,
                details = detail,
                colorIndex = sub.colorIndex
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())

    fun setMainGoal(raw: String) {
        _mainGoal.tryEmit(raw)
    }

    fun setSubGoal(index: Int, raw: String) {
        _subGoals.update {
            it.toMutableList().apply { set(index, it[index].copy(title = raw)) }
        }
    }

    fun setSubGoal(index: Int, colorIndex: Int) {
        _subGoals.update {
            it.toMutableList().apply { set(index, it[index].copy(colorIndex = colorIndex)) }
        }
    }

    fun setDetailGoal(subGoalIndex: Int, index: Int, raw: String) {
        _detailGoals.update { details ->
            val originTargetDetailList = details[subGoalIndex]
            val setTargetDetailList = originTargetDetailList.toMutableList().apply { set(index, raw) }
            details.toMutableList().apply { set(subGoalIndex, setTargetDetailList) }
        }
    }
}

data class DetailGoal(
    val title: String = "",
    val details: List<String> = listOf("", "", "", ""),
    val colorIndex: Int = 0,
)

data class SubGoal(
    val title: String = "",
    val colorIndex: Int = 0,
)
