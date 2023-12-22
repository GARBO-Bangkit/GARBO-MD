package com.garbo.garboapplication.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.garbo.garboapplication.Result
import com.garbo.garboapplication.data.response.FileUploadResponse
import com.garbo.garboapplication.data.retrofit.ApiService
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

class UploadRepository private constructor(
    private val apiService: ApiService
) {

    fun uploadImage(token: String, imageFile: File): LiveData<Result<FileUploadResponse>> =
        liveData {
            emit(Result.Loading)
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "image",
                imageFile.name,
                requestImageFile
            )
            try {
                val response =
                    apiService.uploadImage("Bearer $token", multipartBody)
                if (response.error != null) {
                    emit(Result.Error(response.error))
                } else {
                    emit(Result.Success(response))
                }
            } catch (e: Exception) {
                emit(Result.Error(e.message.toString()))
            }
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