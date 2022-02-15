package com.greedy0110.hagomandal.usecase

data class SubGoal(
    val title: String,
    val detailGoals: List<DetailGoal>
) {
    init {
        if (detailGoals.count() != DETAIL_GOAL_COUNT) {
            throw IllegalArgumentException("dteailGoal은 $DETAIL_GOAL_COUNT 개만 가능합니다.")
        }
    }

    companion object {
        const val DETAIL_GOAL_COUNT = 4
    }
}
