package com.example.lvlup.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.map

val Context.dataStore by preferencesDataStore(name = "prefs")

class PreferencesManager(private val context: Context) {
    companion object {
        val KEY_USER_EMAIL = stringPreferencesKey("user_email")
    }

    // Guardar email del usuario logueado
    suspend fun saveUserEmail(email: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_USER_EMAIL] = email
        }
    }

    // Leer email del usuario logueado
    val userEmailFlow = context.dataStore.data
        .map { prefs -> prefs[KEY_USER_EMAIL] ?: "" }

    // Para borrar
    suspend fun clearUserEmail() {
        context.dataStore.edit { prefs ->
            prefs.remove(KEY_USER_EMAIL)
        }
    }
}