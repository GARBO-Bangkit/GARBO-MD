package com.garbo.garboapplication.data.retrofit

import com.garbo.garboapplication.data.request.LoginRequest
import com.garbo.garboapplication.data.request.RegisterRequest
import com.garbo.garboapplication.data.response.LoginResponse
import com.garbo.garboapplication.data.response.RegisterResponse
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse
}