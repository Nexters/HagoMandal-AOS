package com.greedy0110.hagomandal.usecase

data class Mandalart(
    val mainGoal: String,
    val subGoal: List<SubGoal>
) {
    init {
        if (subGoal.count() != SUB_GOAL_COUNT) {
            throw IllegalArgumentException("subGoal은 $SUB_GOAL_COUNT 개만 가능합니다.")
        }
    }

    companion object {
        const val SUB_GOAL_COUNT = 4
    }
}
