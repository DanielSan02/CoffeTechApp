// ForgotPasswordViewModel.kt (ViewModel)

package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.ForgotPasswordRequest
import com.example.coffetech.model.ForgotPasswordResponse
import com.example.coffetech.model.RetrofitInstance
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ForgotPasswordViewModel : ViewModel() {

    var email = mutableStateOf("")
        private set

    var isEmailValid = mutableStateOf(true)
        private set

    var errorMessage = mutableStateOf("")
        private set

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
        isEmailValid.value = isValidEmail(newEmail)
        if (isEmailValid.value) {
            errorMessage.value = ""
        } else {
            errorMessage.value = "Correo electrónico no válido"
        }
    }

    fun sendForgotPasswordRequest(navController: NavController, context: Context) {
        if (!isEmailValid.value) {
            errorMessage.value = "Correo electrónico no válido"
            return
        }

        val forgotPasswordRequest = ForgotPasswordRequest(email.value)

        RetrofitInstance.api.forgotPassword(forgotPasswordRequest).enqueue(object : Callback<ForgotPasswordResponse> {
            override fun onResponse(call: Call<ForgotPasswordResponse>, response: Response<ForgotPasswordResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            navController.navigate(Routes.ConfirmTokenForgotPasswordView)
                        } else {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        try {
                            val errorJson = JSONObject(it)
                            val errorMessage = if (errorJson.has("message")) {
                                errorJson.getString("message")
                            } else {
                                "Error desconocido al enviar email"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error al procesar la respuesta del servidor", Toast.LENGTH_LONG).show()
                        }
                    } ?: run {
                        val unknownErrorMessage = "Error desconocido al registrar usuario"
                        Toast.makeText(context, unknownErrorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<ForgotPasswordResponse>, t: Throwable) {
                errorMessage.value = "Error de red: ${t.localizedMessage}"
                Toast.makeText(context, errorMessage.value, Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$".toRegex()
        return emailRegex.matches(email)
    }
}
