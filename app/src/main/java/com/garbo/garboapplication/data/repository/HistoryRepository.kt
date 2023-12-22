package com.garbo.garboapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.data.response.HistoryResponse
import com.garbo.garboapplication.data.retrofit.ApiService

class HistoryRepository private constructor(
    private val apiService: ApiService
) {
    private val _historyResponse = MutableLiveData<Result<HistoryResponse>>()
    fun getHistories(token: String): LiveData<Result<HistoryResponse>> = liveData {
        emit(Result.Loading)
        try {
            val reqToken = "Bearer $token"
            val response = apiService.getHistory(reqToken)
            if (response.error != null) {
                emit(Result.Error(response.error))
            } else {
                _historyResponse.value = Result.Success(response)
                emitSource(_historyResponse)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var instance: HistoryRepository? = null
        fun getInstance(
            apiService: ApiService
        ): HistoryRepository = instance ?: synchronized(this) {
            instance ?: HistoryRepository(apiService)
        }.also { instance = it }
    }
}