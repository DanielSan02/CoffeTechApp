package com.example.coffetech.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
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
    val data: Any? = null // Datos adicionales (opcional)
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
    val name: String
)

// Data class para la solicitud de verificación
data class VerifyRequest(
    val token: String,
)

data class VerifyResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

data class ForgotPasswordRequest(
    val email: String
)

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

data class UpdateProfileResponse(
    val status: String,
    val message: String
)

data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)

data class ChangePasswordResponse(
    val status: String,
    val message: String
)

// Tipo genérico para ApiResponse, para manejar diferentes tipos de respuesta
data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
)
data class CreateFarmRequest(
    val name: String,
    val area: Double,
    val unitMeasure: String
)

data class CreateFarmResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)


data class ListFarmResponse(
    val status: String,
    val message: String,
    val data: FarmDataResponse
)

data class FarmDataResponse(
    val farms: List<FarmResponse>
)

data class FarmResponse(
    val farm_id: Int,
    val name: String,
    val area: Double,
    val unit_of_measure: String,
    val status: String,
    val role: String
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

    @POST("/auth/verify-token")
    fun confirmForgotPassword(@Body request: VerifyRequest): Call<VerifyResponse>

    @POST("/auth/reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ResetPasswordResponse>

    @POST("/auth/logout")
    fun logoutUser(@Body request: LogoutRequest): Call<LogoutResponse>

    @POST("/auth/update-profile")
    fun updateProfile(
        @Body request: UpdateProfileRequest,
        @Query("session_token") sessionToken: String
    ): Call<UpdateProfileResponse>

    @PUT("/auth/change-password")
    fun changePassword(
        @Body request: ChangePasswordRequest,
        @Query("session_token") sessionToken: String
    ): Call<ChangePasswordResponse>

    // Cambié los métodos a GET para obtener datos
    @GET("/utils/list-roles")
    fun getRoles(): Call<ApiResponse<List<Role>>>

    @GET("/utils/unit-measure")
    fun getUnitMeasures(): Call<ApiResponse<List<UnitMeasure>>>

    @POST("/farm/create-farm")
    fun createFarm(
        @Query("session_token") sessionToken: String, // El token se pasa como query parameter
        @Body request: CreateFarmRequest // El cuerpo de la petición se pasa aquí
    ): Call<CreateFarmResponse>


    @POST("/farm/list-farm")
    fun listFarms(
        @Query("session_token") sessionToken: String
    ): Call<ListFarmResponse>


}
