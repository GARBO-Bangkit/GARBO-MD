package com.garbo.garboapplication.view.register

import androidx.lifecycle.ViewModel
import com.garbo.garboapplication.data.repository.UserRepository

class RegisterViewModel(private val repository: UserRepository) : ViewModel() {
    fun register(name: String, email: String, pass: String) = repository.register(name, email, pass)
}