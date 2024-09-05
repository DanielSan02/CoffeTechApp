package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.os.Parcel
import android.os.Parcelable
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.LoginRequest
import com.example.coffetech.model.LoginResponse
import com.example.coffetech.model.RetrofitInstance
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
            return
        }

        // Validar el formato del correo electrónico
        val isValidEmail = validateEmail(email.value)
        if (!isValidEmail) {
            errorMessage.value = "Correo electrónico no válido"
            return
        }

        // Si todas las validaciones pasan, limpiar el mensaje de error y proceder con la solicitud
        errorMessage.value = ""

        val loginRequest = LoginRequest(email = email.value, password = password.value)

        // Realizar la solicitud de inicio de sesión al servidor
        RetrofitInstance.api.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, "Inicio de sesión exitoso", Toast.LENGTH_LONG).show()
                            navController.navigate(Routes.FarmView) // Navegar a la pantalla de inicio
                        } else {
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show() // Mostrar mensaje de error del servidor
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
                                "Error desconocido al iniciar sesión"
                            }
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            Toast.makeText(context, "Error al procesar la respuesta del servidor", Toast.LENGTH_LONG).show()
                        }
                    } ?: run {
                        val unknownErrorMessage = "Error desconocido al iniciar sesión"
                        Toast.makeText(context, unknownErrorMessage, Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                val failureMessage = "Fallo en la conexión: ${t.message}"
                Toast.makeText(context, failureMessage, Toast.LENGTH_LONG).show() // Mostrar mensaje de error de conexión
            }
        })
    }

    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
