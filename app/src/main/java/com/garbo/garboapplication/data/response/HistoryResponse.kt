package com.garbo.garboapplication.data.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
    @field:SerializedName("error")
    val error: String? = null,

    @field:SerializedName("HistoryResponse")
    val historyResponse: List<HistoryResponseItem>? = null
)

data class HistoryResponseItem(

    @field:SerializedName("foto")
    val photo_url: String? = null,

    @field:SerializedName("jenis_sampah")
    val classification: String? = null,

    @field:SerializedName("timestamp")
    val timestamp: String? = null,

    @field:SerializedName("username")
    val username: String? = null
)
