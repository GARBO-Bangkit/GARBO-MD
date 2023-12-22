package com.garbo.garboapplication.data.response

import com.google.gson.annotations.SerializedName

data class PointResponse(
    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("point")
    val points: String? = null,

    @field:SerializedName("error")
    val error: String? = null
)