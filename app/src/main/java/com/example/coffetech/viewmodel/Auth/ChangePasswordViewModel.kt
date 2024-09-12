package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.model.ChangePasswordRequest
import com.example.coffetech.model.ChangePasswordResponse
import com.example.coffetech.model.ResetPasswordRequest
import com.example.coffetech.model.ResetPasswordResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ChangePasswordViewModel : ViewModel() {
    var currentPassword = mutableStateOf("")
        private set

    var newPassword = mutableStateOf("")
        private set

    var confirmPassword = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")

    var isLoading = mutableStateOf(false)

    fun onCurrentPasswordChange(newValue: String) {
        currentPassword.value = newValue
    }

    fun onNewPasswordChange(newValue: String) {
        newPassword.value = newValue
    }

    fun onConfirmPasswordChange(newValue: String) {
        confirmPassword.value = newValue
    }

    // Función para validar que la contraseña cumple con los requisitos de seguridad


    fun validatePasswordRequirements(): Boolean {
        val (isValid, message) = validatePassword(newPassword.value, confirmPassword.value)

        return if (isValid) {
            errorMessage.value = ""
            true
        } else {
            errorMessage.value = message
            false
        }
    }

    var isPasswordChanged = mutableStateOf(false) // Agregar esta línea

    private fun validatePassword(password: String, confirmPassword: String): Pair<Boolean, String> {
        val specialCharacterPattern = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")
        val uppercasePattern = Regex(".*[A-Z].*")

        return when {
            password != confirmPassword -> Pair(false, "Las contraseñas no coinciden")
            password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres")
            !specialCharacterPattern.containsMatchIn(password) -> Pair(false, "La contraseña debe contener al menos un carácter especial")
            !uppercasePattern.containsMatchIn(password) -> Pair(false, "La contraseña debe contener al menos una letra mayúscula")
            else -> Pair(true, "Contraseña válida")
        }
    }





    fun changePassword(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            return
        }

        // Crea la solicitud con la contraseña actual y la nueva contraseña
        val changePasswordRequest = ChangePasswordRequest(
            current_password = currentPassword.value,
            new_password = newPassword.value
        )
        isLoading.value = true
        // Realiza la llamada al backend usando el método PUT
        RetrofitInstance.api.changePassword(changePasswordRequest, sessionToken)
            .enqueue(object : Callback<ChangePasswordResponse> {
                override fun onResponse(
                    call: Call<ChangePasswordResponse>,
                    response: Response<ChangePasswordResponse>
                ) {
                    isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        if (responseBody?.status == "success") {
                            // Si la contraseña se cambió con éxito
                            isPasswordChanged.value = true
                            Toast.makeText(context, "Contraseña cambiada exitosamente.", Toast.LENGTH_LONG).show()

                        } else if (responseBody?.status == "error") {
                            // Si hubo un error en el cambio de contraseña (credenciales incorrectas, etc.)
                            val errorMsg = responseBody.message ?: "Error desconocido."
                            errorMessage.value = errorMsg
                            Toast.makeText(context, errorMsg, Toast.LENGTH_LONG).show()
                        } else {
                            // Manejar cualquier otra respuesta inesperada
                            errorMessage.value = "Respuesta inesperada del servidor."
                            Toast.makeText(context, "Respuesta inesperada del servidor.", Toast.LENGTH_LONG).show()
                        }
                    } else {
                        // Manejar el caso en el que la respuesta no fue exitosa (error del servidor)
                        errorMessage.value = "Error al cambiar la contraseña."
                        Toast.makeText(context, "Error al cambiar la contraseña.", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                    // Manejar errores de conexión o fallos de la solicitud
                    val connectionErrorMsg = "Error de conexión: ${t.message}"
                    errorMessage.value = connectionErrorMsg
                    Toast.makeText(context, connectionErrorMsg, Toast.LENGTH_LONG).show()
                }
            })
    }

}
