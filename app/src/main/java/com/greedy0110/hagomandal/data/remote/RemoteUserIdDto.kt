package com.greedy0110.hagomandal.data.remote

import com.squareup.moshi.Json

data class RemoteUserIdDto(
    @Json(name = "user_uuid")
    val remoteUserId: String,
    val message: String?
)
