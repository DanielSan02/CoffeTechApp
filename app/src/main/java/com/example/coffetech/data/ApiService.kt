package com.example.coffetech.data

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
)
data class RegisterResponse(
    val message: String // Ajusta esto de acuerdo a la respuesta que tu servidor devuelve
)

data class LoginRequest(
    val email: String,
    val password: String,
)

data class LoginResponse(
    val message: String // Ajusta esto de acuerdo a la respuesta que tu servidor devuelve
)

data class VerifyRequest(
    val token: String,
)

data class VerifyResponse(
    val message: String // Ajusta esto de acuerdo a la respuesta que tu servidor devuelve
)

data class ForgotPasswordRequest(
    val email: String
)

data class ForgotPasswordResponse(
    val success: Boolean,
    val message: String
)


interface ApiService {
    @POST("/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    @POST("/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("/verify")
    fun verifyUser(@Body request: VerifyRequest): Call<VerifyResponse>

    @POST("/forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

}
