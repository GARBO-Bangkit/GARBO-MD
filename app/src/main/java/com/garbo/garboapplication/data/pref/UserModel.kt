package com.garbo.garboapplication.data.pref

data class UserModel(
    val email: String,
    val name: String,
    val username: String,
    val token: String,
    val isLogin: Boolean = false
)