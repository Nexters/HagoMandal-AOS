package com.greedy0110.hagomandal.usecase

import androidx.room.Entity

enum class GoalColor {
    RED, ORAGE, YELLOW, LIGHT_GREEN, GREEN, BLUE, NAVY, VIOLET, PURPLE, PINK
}

@Entity
data class SubGoal(
    val color: GoalColor,
    val content: String
)
