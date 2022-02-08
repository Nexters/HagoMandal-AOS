package com.greedy0110.hagomandal.util

import androidx.compose.ui.Modifier

fun Modifier.`if`(
    condition: Boolean,
    then: Modifier.() -> Modifier
): Modifier =
    if (condition) {
        then()
    } else {
        this
    }
