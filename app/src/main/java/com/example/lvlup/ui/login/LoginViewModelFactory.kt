package com.example.lvlup.ui.login

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.lvlup.repository.UserRepository

class LoginViewModelFactory(private val repo: UserRepository, private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
            return LoginViewModel(repo, context) as T
            // Pasa repo y context al constructor de tu ViewModel
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}