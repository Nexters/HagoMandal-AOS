package com.greedy0110.hagomandal.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.greedy0110.hagomandal.usecase.Job

@Entity
data class UserEntity (
    @PrimaryKey val id: String,
    val name: String,
    val job: Job?,
    val interest: String?
)


