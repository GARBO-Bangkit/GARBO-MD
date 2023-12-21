package com.garbo.garboapplication.data.response

import com.google.gson.annotations.SerializedName

data class LoginResponse (
    @field:SerializedName("access_token")
    val accessToken: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)