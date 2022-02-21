package com.greedy0110.hagomandal.data.remote

import com.squareup.moshi.Json

data class GoalIdDto(
    @Json(name = "node_id")
    val id: String,
    val message: String?
)
