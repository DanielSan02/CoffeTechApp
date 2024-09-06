// RegisterViewModel.kt (ViewModel)

package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.RegisterRequest
import com.example.coffetech.model.RegisterResponse
import com.example.coffetech.model.RetrofitInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegisterViewModel : ViewModel() {

    var name = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var confirmPassword = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")
        private set

    fun onNameChange(newName: String) {
        name.value = newName
    }

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    fun registerUser(navController: NavController, context: Context) {
        if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank()) {
            errorMessage.value = "Todos los campos son obligatorios"
            return
        }

        val (isValidPassword, passwordMessage) = validatePassword(password.value, confirmPassword.value)
        val isValidEmail = validateEmail(email.value)

        if (!isValidEmail) {
            errorMessage.value = "Correo electrónico no válido"
        } else if (!isValidPassword) {
            errorMessage.value = passwordMessage
        } else {
            errorMessage.value = "" // Limpiar el mensaje de error

            val registerRequest = RegisterRequest(name.value, email.value, password.value, confirmPassword.value)

            RetrofitInstance.api.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            if (it.status == "success") {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                                navController.navigate(Routes.VerifyAccountView)
                            } else {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        errorBody?.let {
                            val errorJson = JSONObject(it)
                            val errorMessage = errorJson.getString("message")
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        } ?: run {
                            val unknownErrorMessage = "Error desconocido al registrar usuario"
                            Toast.makeText(context, unknownErrorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    val failureMessage = "Fallo en la conexión: ${t.message}"
                    Toast.makeText(context, failureMessage, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

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

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
