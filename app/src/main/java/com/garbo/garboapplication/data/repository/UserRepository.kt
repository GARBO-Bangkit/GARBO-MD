package com.garbo.garboapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.data.pref.UserModel
import com.garbo.garboapplication.data.pref.UserPreference
import com.garbo.garboapplication.data.request.LoginRequest
import com.garbo.garboapplication.data.request.RegisterRequest
import com.garbo.garboapplication.data.response.LoginResponse
import com.garbo.garboapplication.data.response.RegisterResponse
import com.garbo.garboapplication.data.retrofit.ApiService
import kotlinx.coroutines.flow.Flow

class UserRepository private constructor(
    private val userPreference: UserPreference,
    private val apiService: ApiService
) {
    private val _loginResponse = MutableLiveData<Result<LoginResponse>>()
    private val _registerResponse = MutableLiveData<Result<RegisterResponse>>()

    suspend fun saveSession(user: UserModel) {
        userPreference.saveSession(user)
    }

    fun getSession(): Flow<UserModel> {
        return userPreference.getSession()
    }

    fun login(username: String, pass: String): LiveData<Result<LoginResponse>> = liveData {
        emit(Result.Loading)
        try {
            val request = LoginRequest(username, pass)
            val response = apiService.login(request)
            if (response.message != null) {
                emit(Result.Error(response.message))
            } else {
                _loginResponse.value = Result.Success(response)
                emitSource(_loginResponse)
            }
        } catch (e: Exception) {
            val statusCode = e.message?.let { message ->
                Regex("HTTP (\\d+)").find(message)?.groups?.get(1)?.value
            }

            val friendlyMessage = when (statusCode) {
                "400" -> "Missing data"
                "401" -> "Invalid username or password"
                else -> e.message.toString()
            }
            emit(Result.Error(friendlyMessage))
        }
    }

    fun register(
        username: String,
        pass: String,
        name: String,
        email: String
    ): LiveData<Result<RegisterResponse>> =
        liveData {
            emit(Result.Loading)
            try {
                val request = RegisterRequest(username, pass, name, email)
                val response = apiService.register(request)
                if (response.message == "User registered successfully") {
                    _registerResponse.value = Result.Success(response)
                    emitSource(_registerResponse)
                } else {
                    emit(Result.Error(response.message ?: ""))
                }
            } catch (e: Exception) {
                val statusCode = e.message?.let { message ->
                    Regex("HTTP (\\d+)").find(message)?.groups?.get(1)?.value
                }

                val friendlyMessage = when (statusCode) {
                    "400" -> "Missing data"
                    "409" -> "Username or email already exist"
                    else -> e.message.toString()
                }
                emit(Result.Error(friendlyMessage))
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