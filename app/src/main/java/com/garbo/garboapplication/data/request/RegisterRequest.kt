package com.garbo.garboapplication.data.request

data class RegisterRequest(
    val username: String,
    val password: String,
    val name: String,
    val email: String
)