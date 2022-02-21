package com.greedy0110.hagomandal.data.remote

import com.squareup.moshi.Json

data class GoalDto(
    @Json(name = "level")
    val goalCategory: Int,
    val position: Int,
    val title: String,
    val contents: String? = null
)
