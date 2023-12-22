package com.garbo.garboapplication.data.response

import com.google.gson.annotations.SerializedName

data class HistoryResponse(
	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("result_list")
	val resultList: List<ResultListItem>? = null
)

data class ResultListItem(

	@field:SerializedName("foto")
	val photo_url: String? = null,

	@field:SerializedName("jenis_sampah")
	val classification: String? = null,

	@field:SerializedName("timestamp")
	val timestamp: String? = null,

	@field:SerializedName("username")
	val username: String? = null,

	@field:SerializedName("error")
	val error: String? = null,
)
