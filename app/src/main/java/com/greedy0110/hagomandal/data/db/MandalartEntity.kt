package com.greedy0110.hagomandal.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.greedy0110.hagomandal.usecase.DetailGoal
import com.greedy0110.hagomandal.usecase.GoalColor
import com.greedy0110.hagomandal.usecase.SubGoal

@Entity(tableName = "mandalart_entity")
data class MandalartEntity (
    @PrimaryKey val id: String,
    val period: Int,
    val mainGoal: String,
    val subGoals: List<SubGoal>,
    val detailGoal: List<DetailGoal>
)