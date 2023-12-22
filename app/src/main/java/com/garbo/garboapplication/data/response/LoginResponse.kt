package com.garbo.garboapplication.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)