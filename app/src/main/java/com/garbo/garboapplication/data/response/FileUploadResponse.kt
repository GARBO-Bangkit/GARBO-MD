package com.garbo.garboapplication.data.response

import com.google.gson.annotations.SerializedName

data class FileUploadResponse (
    @field:SerializedName("accuracy")
    val accuracy: String? = null,

    @field:SerializedName("image_url")
    val imageUrl: String? = null,

    @field:SerializedName("prediction")
    val prediction: String? = null,

    @field:SerializedName("error")
    val error: String? = null
)