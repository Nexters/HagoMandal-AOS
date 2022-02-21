package com.greedy0110.hagomandal.usecase

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
enum class GoalColor : Parcelable {
    RED, ORAGE, YELLOW, LIGHT_GREEN, GREEN, BLUE, NAVY, VIOLET, PURPLE, PINK
}

@Entity
data class SubGoal(
    val color: GoalColor,
    val content: String
)
