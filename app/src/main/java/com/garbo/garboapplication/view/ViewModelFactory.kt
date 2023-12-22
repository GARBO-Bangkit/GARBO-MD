package com.garbo.garboapplication.view

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.garbo.garboapplication.data.repository.HistoryRepository
import com.garbo.garboapplication.data.repository.UploadRepository
import com.garbo.garboapplication.data.repository.UserRepository
import com.garbo.garboapplication.di.Injection
import com.garbo.garboapplication.view.dashboard.HomeViewModel
import com.garbo.garboapplication.view.history.HistoryViewModel
import com.garbo.garboapplication.view.login.LoginViewModel
import com.garbo.garboapplication.view.register.RegisterViewModel
import com.garbo.garboapplication.view.upload.UploadViewModel

class UserViewModelFactory(private val repository: UserRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(repository) as T
            }

            modelClass.isAssignableFrom(RegisterViewModel::class.java) -> {
                RegisterViewModel(repository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UserViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): UserViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UserViewModelFactory::class.java) {
                    INSTANCE = UserViewModelFactory(Injection.provideUserRepository(context))
                }
            }
            return INSTANCE as UserViewModelFactory
        }
    }
}

class HomeViewModelFactory(
    private val userRepository: UserRepository,
    private val historyRepository: HistoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(userRepository, historyRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HomeViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): HomeViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HomeViewModelFactory::class.java) {
                    INSTANCE = HomeViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.provideHistoryRepository(context)
                    )
                }
            }
            return INSTANCE as HomeViewModelFactory
        }
    }
}

class HistoryViewModelFactory(
    private val userRepository: UserRepository,
    private val historyRepository: HistoryRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HistoryViewModel::class.java) -> {
                HistoryViewModel(userRepository, historyRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: HistoryViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): HistoryViewModelFactory {
            if (INSTANCE == null) {
                synchronized(HistoryViewModelFactory::class.java) {
                    INSTANCE = HistoryViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.provideHistoryRepository(context)
                    )
                }
            }
            return INSTANCE as HistoryViewModelFactory
        }
    }
}

class UploadViewModelFactory(
    private val userRepository: UserRepository,
    private val uploadRepository: UploadRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(UploadViewModel::class.java) -> {
                UploadViewModel(userRepository, uploadRepository) as T
            }

            else -> throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: UploadViewModelFactory? = null

        @JvmStatic
        fun getInstance(context: Context): UploadViewModelFactory {
            if (INSTANCE == null) {
                synchronized(UploadViewModelFactory::class.java) {
                    INSTANCE = UploadViewModelFactory(
                        Injection.provideUserRepository(context),
                        Injection.provideUploadRepository(context)
                    )
                }
            }
            return INSTANCE as UploadViewModelFactory
        }
    }
}