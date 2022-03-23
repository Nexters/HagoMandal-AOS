package com.greedy0110.hagomandal.ui

import android.os.Parcelable
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.greedy0110.hagomandal.repository.ISimpleGoalRepository
import com.greedy0110.hagomandal.usecase.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.drop
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.sample
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import timber.log.Timber
import javax.inject.Inject

// TODO: goal 관련 데이터 통합 관리하자. 지금 화면마다 관리하니까 주고 받고 난리도 아님...
@HiltViewModel
class GoalViewModel @Inject constructor(
    private val userRepository: UserRepository,
    private val goalRepository: ISimpleGoalRepository,
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
            val setTargetDetailList =
                originTargetDetailList.toMutableList().apply { set(index, raw) }
            details.toMutableList().apply { set(subGoalIndex, setTargetDetailList) }
        }
    }

    private val goalSavingTrigger = MutableSharedFlow<Unit>(replay = 1)

    init {

        // 최초 저장된 값을 불러온다.
        viewModelScope.launch {
            runCatching {
                val (main, details) = goalRepository.getGoals()
                _mainGoal.tryEmit(main)
                _subGoals.tryEmit(details.map { SubGoal(title = it.title, colorIndex = it.colorIndex) })
                _detailGoals.tryEmit(details.map { it.details })
            }
                .onFailure {
                    it.printStackTrace()
                    Timber.d("기존에 저장된 목표가 없음.")
                }
            goalSavingTrigger.tryEmit(Unit)
        }
    }

    init {

        combine(
            goalSavingTrigger,
            _mainGoal,
            detailGoal,
        ) { _, main, details -> Pair(main, details) }
            .onEach { Timber.d("저장을 위해서 ${it.first} ${it.second}") }
            .drop(1)
            .sample(1000L)
            .distinctUntilChanged()
            .onEach { (main, details) ->
                Timber.d("목표 저장 흐름 탐 $main $details")
                goalRepository.saveGoals(main, details)
            }
            .launchIn(viewModelScope)
    }

    private fun saveGoal() {
    }
}

@Parcelize
data class DetailGoal(
    val title: String = "",
    val details: List<String> = listOf("", "", "", ""),
    val colorIndex: Int = 0,
) : Parcelable

data class SubGoal(
    val title: String = "",
    val colorIndex: Int = 0,
)
