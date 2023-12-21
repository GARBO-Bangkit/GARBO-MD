package com.garbo.garboapplication.view.register

import androidx.lifecycle.ViewModel
import com.garbo.garboapplication.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(username: String, pass: String, name: String, email: String) = repository.register(username, pass, name, email)
}