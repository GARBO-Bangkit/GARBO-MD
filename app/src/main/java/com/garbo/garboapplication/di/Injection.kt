package com.garbo.garboapplication.di

import android.content.Context
import com.garbo.garboapplication.data.pref.UserPreference
import com.garbo.garboapplication.data.pref.dataStore
import com.garbo.garboapplication.data.repository.UserRepository
import com.garbo.garboapplication.data.retrofit.ApiConfig

object Injection {
    fun provideUserRepository(context: Context): UserRepository {
        val pref = UserPreference.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(pref, apiService)
    }
}