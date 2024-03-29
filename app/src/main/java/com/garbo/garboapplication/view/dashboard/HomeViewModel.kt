package com.garbo.garboapplication.view.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.garbo.garboapplication.data.pref.UserModel
import com.garbo.garboapplication.data.repository.HistoryRepository
import com.garbo.garboapplication.data.repository.UserRepository
import kotlinx.coroutines.launch

class HomeViewModel(private val userRepository: UserRepository, private val historyRepository: HistoryRepository): ViewModel() {
    fun getSession(): LiveData<UserModel> {
        return userRepository.getSession().asLiveData()
    }

    fun getPoints(token: String) = userRepository.getPoints(token)

    fun getLatestHistory(token: String) = historyRepository.getLatestHistories(token)

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }
}