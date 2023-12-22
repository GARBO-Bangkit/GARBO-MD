package com.garbo.garboapplication.data.retrofit

import com.garbo.garboapplication.data.request.LoginRequest
import com.garbo.garboapplication.data.request.RegisterRequest
import com.garbo.garboapplication.data.response.FileUploadResponse
import com.garbo.garboapplication.data.response.HistoryResponse
import com.garbo.garboapplication.data.response.HistoryResponseItem
import com.garbo.garboapplication.data.response.LoginResponse
import com.garbo.garboapplication.data.response.PointResponse
import com.garbo.garboapplication.data.response.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @POST("register")
    suspend fun register(
        @Body request: RegisterRequest
    ): RegisterResponse

    @POST("login")
    suspend fun login(
        @Body request: LoginRequest
    ): LoginResponse

    @GET("point")
    suspend fun getPoint(
        @Header("Authorization") token: String
    ): PointResponse

    @GET("result")
    suspend fun getHistory(
        @Header("Authorization") token: String
    ): HistoryResponse

    @GET("lasthistory")
    suspend fun getLatestHistory(
        @Header("Authorization") token: String
    ): HistoryResponseItem

    @Multipart
    @POST("sendpicture")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
    ): FileUploadResponse
}