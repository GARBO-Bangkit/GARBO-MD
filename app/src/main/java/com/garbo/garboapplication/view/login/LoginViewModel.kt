package com.garbo.garboapplication.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.garbo.garboapplication.data.pref.UserModel
import com.garbo.garboapplication.data.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repository: UserRepository) : ViewModel() {
    fun login(email: String, pass: String) = repository.login(email, pass)

    fun saveSession(user: UserModel) {
        viewModelScope.launch {
            repository.saveSession(user)
        }
    }
}