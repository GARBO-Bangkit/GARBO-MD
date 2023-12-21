package com.garbo.garboapplication.view.upload

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.garbo.garboapplication.data.pref.UserModel
import com.garbo.garboapplication.data.repository.UploadRepository
import com.garbo.garboapplication.data.repository.UserRepository
import java.io.File

class UploadViewModel(
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository
) : ViewModel() {
    fun uploadImage(token: String, file: File) =
        uploadRepository.uploadImage(token, file)

    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }
}