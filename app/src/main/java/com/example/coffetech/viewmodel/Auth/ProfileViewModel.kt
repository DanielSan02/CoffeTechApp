package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.coffetech.model.UpdateProfileRequest
import com.example.coffetech.model.UpdateProfileResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProfileViewModel : ViewModel() {
    var name = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")
        private set

    var isProfileUpdated = mutableStateOf(false)
        private set

    // Cargar los datos del usuario desde SharedPreferences
    fun loadUserData(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        name.value = sharedPreferencesHelper.getUserName() // Cargar el nombre
        email.value = sharedPreferencesHelper.getUserEmail() // Cargar el correo
    }

    // Método para manejar los cambios en el nombre
    fun onNameChange(newName: String) {
        name.value = newName
        isProfileUpdated.value = true // Habilita el botón Guardar cuando el nombre cambie
    }

    // Función para actualizar el perfil en el backend
    fun saveProfile(context: Context, onSuccess: () -> Unit) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            return
        }

        val updateRequest = UpdateProfileRequest(new_name = name.value)

        RetrofitInstance.api.updateProfile(updateRequest, sessionToken).enqueue(object : Callback<UpdateProfileResponse> {
            override fun onResponse(call: Call<UpdateProfileResponse>, response: Response<UpdateProfileResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody?.status == "success") {
                        // Guardar el nuevo nombre en SharedPreferences
                        sharedPreferencesHelper.saveSessionData(sessionToken, name.value, email.value)

                        // Mostrar un Toast de éxito
                        Toast.makeText(context, "Perfil actualizado exitosamente.", Toast.LENGTH_LONG).show()
                        isProfileUpdated.value = false
                        // Indicar éxito
                        onSuccess()
                    } else {
                        val errorMsg = responseBody?.message ?: "Error desconocido al actualizar el perfil."
                        errorMessage.value = errorMsg
                        Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                    }
                } else {
                    val serverErrorMsg = "Error al actualizar el perfil."
                    errorMessage.value = serverErrorMsg
                    Toast.makeText(context, serverErrorMsg, Toast.LENGTH_LONG).show()
                    Log.e("ProfileViewModel", "Error en la respuesta del servidor: ${response.errorBody()?.string()}")
                }
            }

            override fun onFailure(call: Call<UpdateProfileResponse>, t: Throwable) {
                val connectionErrorMsg = "Error de conexión: ${t.message}"
                errorMessage.value = connectionErrorMsg
                Toast.makeText(context, connectionErrorMsg, Toast.LENGTH_LONG).show()
                Log.e("ProfileViewModel", "Error de conexión: ${t.message}")
            }
        })
    }
}
