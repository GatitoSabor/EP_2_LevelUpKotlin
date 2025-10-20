package com.example.lvlup.ui.login

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvlup.data.UserEntity
import com.example.lvlup.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository): ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)
    var loginError by mutableStateOf<String?>(null)

    // Usuario actualmente logueado (incluido nombre/email/pass)
    var usuarioActivo by mutableStateOf<UserEntity?>(null)

    fun login() = viewModelScope.launch {
        val user = repo.login(email, password)
        loginSuccess = user != null
        loginError = if (user == null) "Credenciales inválidas" else null
        usuarioActivo = user               // <-- Guarda el usuario activo al loguear!
    }

    fun registerNewUser(username: String, email: String, password: String, onResult: (Boolean) -> Unit) {
        viewModelScope.launch {
            val user = UserEntity(nombre = username, email = email, password = password)
            repo.register(user)
            usuarioActivo = user           // <-- Guarda el usuario creado como activo!
            onResult(true)
        }
    }

    fun logout() {
        usuarioActivo = null
    }
}