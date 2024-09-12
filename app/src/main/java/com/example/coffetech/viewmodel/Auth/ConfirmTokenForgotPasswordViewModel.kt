// ConfirmTokenForgotPasswordViewModel.kt (ViewModel)

package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.model.VerifyRequest
import com.example.coffetech.model.VerifyResponse
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// ConfirmTokenForgotPasswordViewModel.kt (ViewModel)

class ConfirmTokenForgotPasswordViewModel : ViewModel() {

    var token = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    fun onTokenChange(newToken: String) {
        token.value = newToken
        if (newToken.isNotBlank()) {
            errorMessage.value = ""
        }
    }

    fun confirmToken(navController: NavController, context: Context) {
        if (token.value.isBlank()) {
            errorMessage.value = "El token es obligatorio"
            return
        }

        val verifyRequest = VerifyRequest(token = token.value)

        isLoading.value = true // Indicar que estamos en proceso de carga

        RetrofitInstance.api.confirmForgotPassword(verifyRequest).enqueue(object : Callback<VerifyResponse> {
            override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {
                isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()

                            Log.d("ConfirmTokenViewModel", "Navegando a NewPasswordView con token: ${token.value}")

                            navController.navigate("${Routes.NewPasswordView}/${token.value}")
                        } else {
                            errorMessage.value = it.message ?: "Error desconocido del servidor"
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
                                "Error desconocido al confirmar el token"
                            }
                            this@ConfirmTokenForgotPasswordViewModel.errorMessage.value = errorMessage
                        } catch (e: Exception) {
                            errorMessage.value = "Error desconocido al confirmar el token"
                        }
                    } ?: run {
                        errorMessage.value = "Error desconocido al confirmar el token"
                    }
                }
            }

            override fun onFailure(call: Call<VerifyResponse>, t: Throwable) {
                errorMessage.value = "Fallo en la conexión: ${t.message}"
                Toast.makeText(context, errorMessage.value, Toast.LENGTH_LONG).show()
            }
        })
    }
}
