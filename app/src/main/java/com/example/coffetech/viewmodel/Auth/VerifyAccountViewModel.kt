// VerifyAccountViewModel.kt

package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.model.VerifyRequest
import com.example.coffetech.model.VerifyResponse
import com.example.coffetech.utils.SharedPreferencesHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VerifyAccountViewModel : ViewModel() {

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

    fun verifyUser(navController: NavController, context: Context) {
        if (token.value.isBlank()) {
            errorMessage.value = "El token es obligatorio"
            return
        }

        val verifyRequest = VerifyRequest(token = token.value)
        isLoading.value = true // Indicar que estamos en proceso de carga

        RetrofitInstance.api.verifyUser(verifyRequest).enqueue(object : Callback<VerifyResponse> {
            override fun onResponse(call: Call<VerifyResponse>, response: Response<VerifyResponse>) {
                isLoading.value = false

                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()

                            navController.navigate(Routes.LoginView) {
                                popUpTo(Routes.VerifyAccountView) { inclusive = true }
                            }


                        } else {
                            errorMessage.value = it.message ?: "Error desconocido del servidor"
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        val errorJson = JSONObject(it)
                        val errorMessage = errorJson.getString("message")
                        Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                    } ?: run {
                        errorMessage.value = "Error desconocido al registrar usuario"
                        Toast.makeText(context, errorMessage.value, Toast.LENGTH_LONG).show()
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
