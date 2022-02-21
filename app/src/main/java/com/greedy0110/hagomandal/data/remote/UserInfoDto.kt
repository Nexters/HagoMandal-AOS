package com.greedy0110.hagomandal.data.remote

import com.squareup.moshi.Json

data class UserInfoDto(
    @Json(name = "category_code")
    val categoryCode: String,
    val value: InfoValueDto
)
