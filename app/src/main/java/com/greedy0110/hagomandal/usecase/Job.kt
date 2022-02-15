package com.greedy0110.hagomandal.usecase

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Job(
    val 대분류: String,
    val 소분류: String
) : Parcelable
