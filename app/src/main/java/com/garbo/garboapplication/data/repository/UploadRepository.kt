package com.garbo.garboapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.garbo.garboapplication.data.retrofit.ApiService
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.data.response.FileUploadResponse

class UploadRepository private constructor(
    private val apiService: ApiService
) {

    fun uploadImage(token: String, imageFile: File): LiveData<Result<FileUploadResponse>> = liveData {
//        emit(Result.Loading)
//        val requestBody = description.toRequestBody("text/plain".toMediaType())
//        val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
//        val multipartBody = MultipartBody.Part.createFormData(
//            "photo",
//            imageFile.name,
//            requestImageFile
//        )
//        try {
//            val successResponse =
//                apiService.uploadImage("Bearer $token", multipartBody, requestBody)
//            emit(Result.Success(successResponse))
//        } catch (e: HttpException) {
//            val errorBody = e.response()?.errorBody()?.string()
//            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
//            emit(Result.Error(errorResponse.message))
//        }
    }

    companion object {
        @Volatile
        private var instance: UploadRepository? = null
        fun getInstance(apiService: ApiService) =
            instance ?: synchronized(this) {
                instance ?: UploadRepository(apiService)
            }.also { instance = it }
    }
}