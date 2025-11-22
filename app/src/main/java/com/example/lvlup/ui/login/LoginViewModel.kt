package com.example.lvlup.ui.login

import android.content.Context
import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.lvlup.data.UserEntity
import com.example.lvlup.repository.UserRepository
import kotlinx.coroutines.launch

class LoginViewModel(private val repo: UserRepository, private val context: Context): ViewModel() {
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var loginSuccess by mutableStateOf(false)
    var loginError by mutableStateOf<String?>(null)
    var usuarioActivo by mutableStateOf<UserEntity?>(null)

    //para la persistencia, mas el context de arriba
    private val prefs = context.getSharedPreferences("sesion", Context.MODE_PRIVATE)

    //para iniciaizar la sesion
    fun cargarSesion() {
        val savedId = prefs.getInt("id", 0)
        val savedNombre = prefs.getString("nombre", null)
        val savedEmail = prefs.getString("email", null)
        val savedPassword = prefs.getString("password", null)

        if (savedNombre != null && savedEmail != null && savedPassword != null) {
            usuarioActivo = UserEntity(
                id = savedId,
                nombre = savedNombre,
                email = savedEmail,
                password = savedPassword
            )
            loginSuccess = true
        }
    }

    fun login(onResult: (UserEntity?) -> Unit = {}) = viewModelScope.launch {
        val user = repo.login(email, password)
        loginSuccess = user != null
        loginError = if (user == null) "Credenciales inválidas" else null
        usuarioActivo = user

        //Guardar sesion si usuario es valido
        if (user != null) {
            prefs.edit().putString("user_id", user.id.toString()).apply()
            prefs.edit().putString("nombre", user.nombre).apply()
            prefs.edit().putString("user_email", user.email).apply()
            prefs.edit().putString("password", user.password).apply()
        }
        onResult(user)
    }

    fun registerNewUser(username: String, email: String, password: String, onResult: (UserEntity?) -> Unit = {}) {
        viewModelScope.launch {
            val user = UserEntity(nombre = username, email = email, password = password)
            repo.register(user)
            val usuarioRegistrado = repo.login(email, password)
            usuarioActivo = usuarioRegistrado
            onResult(usuarioRegistrado)
        }
    }

    fun guardarSesion(context: Context, usuarioId: Int) {
        val prefs = context.getSharedPreferences("lvlup_prefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("usuario_id", usuarioId).apply()
    }

    fun logout() {
        prefs.edit().clear().apply()
        usuarioActivo = null
        loginSuccess = false
        loginError = null
        email = ""
        password = ""
    }
}
