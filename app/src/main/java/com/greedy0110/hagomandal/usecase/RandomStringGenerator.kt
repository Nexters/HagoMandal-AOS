package com.greedy0110.hagomandal.usecase

object RandomStringGenerator {
    private val charSet = ('a'..'z') + ('A'..'Z') + ('0'..'9')

    fun generate(length: Int): String {
        return List(length) { charSet.random() }
            .joinToString("")
    }
}
