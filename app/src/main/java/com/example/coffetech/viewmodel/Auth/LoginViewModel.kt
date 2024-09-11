package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.LoginRequest
import com.example.coffetech.model.LoginResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel() : ViewModel(), Parcelable {

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")
        private set

    constructor(parcel: Parcel) : this()

    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {}

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LoginViewModel> {
        override fun createFromParcel(parcel: Parcel): LoginViewModel {
            return LoginViewModel(parcel)
        }

        override fun newArray(size: Int): Array<LoginViewModel?> {
            return arrayOfNulls(size)
        }
    }

    fun loginUser(navController: NavController, context: Context) {
        // Validar que los campos no estén vacíos
        if (email.value.isBlank() || password.value.isBlank()) {
            errorMessage.value = "El correo y la contraseña son obligatorios"
            Log.e("LoginViewModel", "Los campos de email o contraseña están vacíos") // Log de error
            return
        }

        // Validar el formato del correo electrónico
        val isValidEmail = validateEmail(email.value)
        if (!isValidEmail) {
            errorMessage.value = "Correo electrónico no válido"
            Log.e("LoginViewModel", "El formato del correo electrónico es inválido") // Log de error
            return
        }

        // Si todas las validaciones pasan, limpiar el mensaje de error y proceder con la solicitud
        errorMessage.value = ""

        val loginRequest = LoginRequest(email = email.value, password = password.value)

        Log.d("LoginViewModel", "Iniciando solicitud de inicio de sesión con email: ${email.value}")

        // Realizar la solicitud de inicio de sesión al servidor
        RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                Log.d("LoginViewModel", "Respuesta del servidor recibida")
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        Log.d("LoginViewModel", "Estado de la respuesta: ${it.status}")
                        if (it.status == "success") {
                            // Obtener el token de la respuesta
                            val token = it.data?.session_token
                            val name = it.data?.name ?: "Usuario"
                            val email = email.value

                            token?.let {
                                // Guardar el token, nombre y correo en SharedPreferences
                                val sharedPreferencesHelper = SharedPreferencesHelper(context)
                                sharedPreferencesHelper.saveSessionData(token, name, email)

                                Log.d("LoginViewModel", "Datos guardados correctamente: token=$token, name=$name")

                                // Notificar inicio de sesión exitoso
                                Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                                navController.navigate(Routes.StartView) {
                                    popUpTo(Routes.LoginView) { inclusive = true } // Elimina LoginView de la pila de navegación
                                }

                            } ?: run {
                                Log.e("LoginViewModel", "El token no fue recibido en la respuesta")
                                Toast.makeText(context, "No se recibió el token de sesión", Toast.LENGTH_LONG).show()
                            }
                        } else {
                            Log.e("LoginViewModel", "Inicio de sesión fallido: ${it.message}")
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    errorBody?.let {
                        Log.e("LoginViewModel", "Error del servidor: $it")
                        try {
                            val errorJson = JSONObject(it)
                            val errorMessage = if (errorJson.has("message")) {
                                errorJson.getString("message")
                            } else {
                                "Error desconocido al iniciar sesión"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Log.e("LoginViewModel", "Error al procesar la respuesta del servidor: ${e.message}")
                            Toast.makeText(context, "Error al procesar la respuesta del servidor", Toast.LENGTH_LONG).show()
                        }
                    } ?: run {
                        Log.e("LoginViewModel", "Respuesta vacía del servidor")
                        val unknownErrorMessage = "Error desconocido al iniciar sesión"
                        Toast.makeText(context, unknownErrorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("LoginViewModel", "Fallo en la conexión: ${t.message}")
                val failureMessage = "Fallo en la conexión: ${t.message}"
                Toast.makeText(context, failureMessage, Toast.LENGTH_LONG).show()
            }
        })
    }


    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
