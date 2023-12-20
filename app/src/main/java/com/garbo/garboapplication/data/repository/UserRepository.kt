package com.garbo.garboapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _registerResponse = MutableLiveData<Result<RegisterResponse>>()
    private val _loginResponse = MutableLiveData<Result<LoginResponse>>()

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun register(name: String, email: String, pass: String): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val response = apiService.register(name, email, pass)
                if (response.error == true) {
                    emit(Result.Error(response.message ?: ""))
                } else {
                    _registerResponse.value = Result.Success(response)
                    emitSource(_registerResponse)
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
        }

    fun login(email: String, pass: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.login(email, pass)
            if (response.error == true) {
                emit(Result.Error(response.message ?: ""))
            } else {
                _loginResponse.value = Result.Success(response)
                emitSource(_loginResponse)
            }
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    suspend fun logout() {
        userPreference.logout()
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            userPreference: UserPreference,
            apiService: ApiService
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(userPreference, apiService)
            }.also { instance = it }
    }
}