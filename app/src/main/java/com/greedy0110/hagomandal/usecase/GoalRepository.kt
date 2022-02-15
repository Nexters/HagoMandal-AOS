package com.greedy0110.hagomandal.usecase

import javax.inject.Singleton

enum class GoalColor {
    RED, ORAGE, YELLOW, LIGHT_GREEN, GREEN, BLUE, NAVY, VIOLET, PURPLE, PINK
}

enum class SubGoalLocation {
    TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT
}

@Singleton
class GoalRepository {
    fun setGoalDuration(days: Int){
        // TODO("Not Implemented")
    }

    fun setMainGoal(){
        // TODO("Not Implemented")
    }

    fun setSubGoal(subGoalLocation: SubGoalLocation, goal: String){
        // TODO("Not Implemented")
    }

    fun setSubGoalColor(subGoalLocation: SubGoalLocation, goalColor: GoalColor) {
        // TODO("Not Implemented")
    }

    fun setDetailGoal(index: Int, goal: String) {
        // TODO("Not Implemented")
    }
}

