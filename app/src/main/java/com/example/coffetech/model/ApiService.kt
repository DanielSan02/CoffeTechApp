package com.example.coffetech.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Query


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
    val data: TokenData? = null
)

data class TokenData(
    val session_token: String,
    val name: String // Añadir el nombre del usuario que devuelve el backend
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

data class LogoutRequest(
    val session_token: String
)

data class LogoutResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

data class UpdateProfileRequest(
    val new_name: String
)

// Data class para la respuesta de actualización de perfil
data class UpdateProfileResponse(
    val status: String,
    val message: String
)

// Data class para la solicitud de cambio de contraseña
data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)

// Data class para la respuesta del cambio de contraseña
data class ChangePasswordResponse(
    val status: String,
    val message: String
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

    @POST("/auth/logout")
    fun logoutUser(@Body request: LogoutRequest): Call<LogoutResponse>

    @POST("/auth/update-profile")
    fun updateProfile(@Body request: UpdateProfileRequest, @retrofit2.http.Query("session_token") sessionToken: String): Call<UpdateProfileResponse>

    @PUT("/auth/change-password")
        fun changePassword(@Body request: ChangePasswordRequest,@Query("session_token") sessionToken: String): Call<ChangePasswordResponse>



}
