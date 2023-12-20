package com.garbo.garboapplication.view

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.garbo.garboapplication.view.login.data.LoginDataSource
import com.garbo.garboapplication.view.login.data.LoginRepository
import com.garbo.garboapplication.view.login.ui.login.LoginViewModel

/**
 * ViewModel provider factory to instantiate LoginViewModel.
 * Required given LoginViewModel has a non-empty constructor
 */
class ViewModelFactory : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(
                loginRepository = LoginRepository(
                    dataSource = LoginDataSource()
                )
            ) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}