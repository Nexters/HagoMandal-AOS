package com.greedy0110.hagomandal.data.remote

data class GoalDto(
    val level: Int,
    val position: Int,
    val title: String,
    val contents: String? = null
)
