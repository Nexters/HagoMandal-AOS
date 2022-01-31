package com.greedy0110.hagomandal.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class GoalEntity(
    @PrimaryKey val id: Int
)
