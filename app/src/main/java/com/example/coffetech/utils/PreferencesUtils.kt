package com.example.coffetech.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val sharedPref: SharedPreferences =
        context.getSharedPreferences("myAppPreferences", Context.MODE_PRIVATE)

    // Función para guardar el token, nombre y correo
    fun saveSessionData(token: String, name: String, email: String) {
        with(sharedPref.edit()) {
            putString("session_token", token)
            putString("user_name", name)
            putString("user_email", email)
            apply()
        }
    }

    // Función para verificar si el usuario está logueado
    fun isLoggedIn(): Boolean {
        return sharedPref.getString("session_token", null) != null
    }


    // Función para obtener el token de sesión
    fun getSessionToken(): String? {
        return sharedPref.getString("session_token", null)
    }

    // Función para obtener el nombre de usuario
    fun getUserName(): String {
        return sharedPref.getString("user_name", "Usuario") ?: "Usuario"
    }

    // Función para obtener el correo de usuario
    fun getUserEmail(): String {
        return sharedPref.getString("user_email", "") ?: ""
    }

    // Función para eliminar los datos de sesión (cerrar sesión)
    fun clearSession() {
        with(sharedPref.edit()) {
            remove("session_token")
            remove("user_name")
            remove("user_email")
            apply()
        }
    }
}
