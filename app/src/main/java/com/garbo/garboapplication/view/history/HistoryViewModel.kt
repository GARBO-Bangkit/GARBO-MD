package com.garbo.garboapplication.view.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.garbo.garboapplication.data.pref.UserModel
import com.garbo.garboapplication.data.repository.HistoryRepository
import com.garbo.garboapplication.data.repository.UserRepository

class HistoryViewModel(
    private val userRepository: UserRepository,
    private val historyRepository: HistoryRepository
) : ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getHistories(token: String) = historyRepository.getHistories(token)
}