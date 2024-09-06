// NewPasswordViewModel.kt

package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.model.ResetPasswordRequest
import com.example.coffetech.model.ResetPasswordResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewPasswordViewModel : ViewModel() {

    var password = mutableStateOf("")
        private set

    var confirmPassword = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")
        private set

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
        clearErrorMessage()
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
        clearErrorMessage()
    }

    private fun clearErrorMessage() {
        errorMessage.value = ""
    }

    fun resetPassword(navController: NavController, context: Context, token: String) {
        Log.d("NewPasswordViewModel", "Token recibido en resetPassword: $token")


        if (password.value.isBlank() || confirmPassword.value.isBlank()) {
            errorMessage.value = "Ambos campos son obligatorios"
            return
        }
        val (isValidPassword, passwordMessage) = validatePassword(password.value, confirmPassword.value)
        if (!isValidPassword) {
            errorMessage.value = passwordMessage
        } else {
            errorMessage.value = ""

        // Asegúrate de que estás creando el objeto ResetPasswordRequest correctamente
        val resetPasswordRequest = ResetPasswordRequest(
            token = token,
            new_password = password.value,
            confirm_password = confirmPassword.value // Pasa el passwordConfirmation aquí
        )

        RetrofitInstance.api.resetPassword(resetPasswordRequest).enqueue(object : Callback<ResetPasswordResponse> {
            override fun onResponse(call: Call<ResetPasswordResponse>, response: Response<ResetPasswordResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, "Contraseña restablecida exitosamente", Toast.LENGTH_SHORT).show()
                            navController.navigate(Routes.LoginView)
                        } else {
                            errorMessage.value = it.message ?: "Error desconocido del servidor"
                        }
                    }
                } else {
                    errorMessage.value = "Error desconocido al restablecer la contraseña"
                }
            }

            override fun onFailure(call: Call<ResetPasswordResponse>, t: Throwable) {
                errorMessage.value = "Fallo en la conexión: ${t.message}"
                Toast.makeText(context, errorMessage.value, Toast.LENGTH_LONG).show()
            }
        })
    }}
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
    private fun isPasswordValid(password: String): Boolean {
        val passwordRegex = Regex("^(?=.*[A-Z])(?=.*[0-9])(?=.*[@#\$%^&+=!]).{8,}$")
        return passwordRegex.matches(password)
    }
}
