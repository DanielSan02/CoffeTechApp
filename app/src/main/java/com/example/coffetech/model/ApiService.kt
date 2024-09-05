package com.example.coffetech.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST


// Data class para la solicitud de registro
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
)

// Data class para la respuesta del registro
data class RegisterResponse(
    val status: String,  // "success" o "error"
    val message: String, // Mensaje asociado con la respuesta
    val data: Any? = null // Datos adicionales (opcional), puede ser null o de cualquier tipo
)

// Data class para la solicitud de inicio de sesión
data class LoginRequest(
    val email: String,
    val password: String,
)

// Data class para la respuesta del inicio de sesión
data class LoginResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

// Data class para la solicitud de verificación
data class VerifyRequest(
    val token: String,
)

// Data class para la respuesta de verificación
data class VerifyResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)


// Data class para la solicitud de "olvidó su contraseña"
data class ForgotPasswordRequest(
    val email: String
)

// Data class para la respuesta de "olvidó su contraseña"
data class ForgotPasswordResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

data class ResetPasswordRequest(
    val token: String,
    val new_password: String,
    val confirm_password: String

)
data class ResetPasswordResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

// Interfaz del servicio API
interface ApiService {
    @POST("/auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/auth/verify")
    fun verifyUser(@Body request: VerifyRequest): Call<VerifyResponse>

    @POST("/auth/forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

    @POST("/auth/verify-token") // Cambia este endpoint al correcto para verificar el token
    fun confirmForgotPassword(@Body request: VerifyRequest): Call<VerifyResponse>

    @POST("/auth/reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest) : Call <ResetPasswordResponse>
}
