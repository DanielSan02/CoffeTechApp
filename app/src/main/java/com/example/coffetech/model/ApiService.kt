package com.example.coffetech.model

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

// Data classes for API requests and responses

/**
 * Data class representing the registration request payload.
 *
 * @property name The user's name.
 * @property email The user's email.
 * @property password The user's password.
 * @property passwordConfirmation Confirmation of the user's password.
 */
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String,
    val passwordConfirmation: String
)

/**
 * Data class representing the registration response from the server.
 *
 * @property status The status of the registration request ("success" or "error").
 * @property message The message associated with the registration response.
 * @property data Optional additional data.
 */
data class RegisterResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

/**
 * Data class representing the login request payload.
 *
 * @property email The user's email.
 * @property password The user's password.
 * @property fcm_token The user's token cloud message firebase
 */
data class LoginRequest(
    val email: String,
    val password: String,
    val fcm_token: String? = null
)


/**
 * Data class representing the login response from the server.
 *
 * @property status The status of the login request.
 * @property message The message associated with the login response.
 * @property data Token data if login is successful.
 */
data class LoginResponse(
    val status: String,
    val message: String,
    val data: TokenData? = null
)

/**
 * Data class representing the token and user data.
 *
 * @property session_token The session token for the authenticated user.
 * @property name The name of the authenticated user.
 */
data class TokenData(
    val session_token: String,
    val name: String
)

/**
 * Data class representing the verification request payload.
 *
 * @property token The token for verifying the user's account.
 */
data class VerifyRequest(
    val token: String
)

/**
 * Data class representing the verification response from the server.
 *
 * @property status The status of the verification request.
 * @property message The message associated with the verification response.
 * @property data Optional additional data.
 */
data class VerifyResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

/**
 * Data class representing the forgot password request payload.
 *
 * @property email The user's email for password recovery.
 */
data class ForgotPasswordRequest(
    val email: String
)

/**
 * Data class representing the forgot password response from the server.
 *
 * @property status The status of the forgot password request.
 * @property message The message associated with the forgot password response.
 * @property data Optional additional data.
 */
data class ForgotPasswordResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

/**
 * Data class representing the reset password request payload.
 *
 * @property token The token for resetting the user's password.
 * @property new_password The new password for the user.
 * @property confirm_password Confirmation of the new password.
 */
data class ResetPasswordRequest(
    val token: String,
    val new_password: String,
    val confirm_password: String
)

/**
 * Data class representing the reset password response from the server.
 *
 * @property status The status of the reset password request.
 * @property message The message associated with the reset password response.
 * @property data Optional additional data.
 */
data class ResetPasswordResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

/**
 * Data class representing the logout request payload.
 *
 * @property session_token The session token to be invalidated.
 */
data class LogoutRequest(
    val session_token: String
)

/**
 * Data class representing the logout response from the server.
 *
 * @property status The status of the logout request.
 * @property message The message associated with the logout response.
 * @property data Optional additional data.
 */
data class LogoutResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

/**
 * Data class representing the update profile request payload.
 *
 * @property new_name The new name for the user's profile.
 */
data class UpdateProfileRequest(
    val new_name: String
)

/**
 * Data class representing the update profile response from the server.
 *
 * @property status The status of the profile update request.
 * @property message The message associated with the profile update response.
 */
data class UpdateProfileResponse(
    val status: String,
    val message: String
)

/**
 * Data class representing the change password request payload.
 *
 * @property current_password The current password of the user.
 * @property new_password The new password for the user.
 */
data class ChangePasswordRequest(
    val current_password: String,
    val new_password: String
)

/**
 * Data class representing the change password response from the server.
 *
 * @property status The status of the change password request.
 * @property message The message associated with the change password response.
 */
data class ChangePasswordResponse(
    val status: String,
    val message: String
)

/**
 * Generic response type for API responses that can handle different data types.
 *
 * @param T The type of data expected in the response.
 * @property status The status of the API response.
 * @property message The message associated with the API response.
 * @property data The data returned by the API, if applicable.
 */
data class ApiResponse<T>(
    val status: String,
    val message: String,
    val data: T? = null
)

/**
 * Data class representing the create farm request payload.
 *
 * @property name The name of the farm.
 * @property area The area of the farm.
 * @property unitMeasure The unit of measurement for the farm's area.
 */
data class CreateFarmRequest(
    val name: String,
    val area: Double,
    val unitMeasure: String
)

/**
 * Data class representing the create farm response from the server.
 *
 * @property status The status of the create farm request.
 * @property message The message associated with the create farm response.
 * @property data Optional additional data.
 */
data class CreateFarmResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

/**
 * Data class representing the list farms response from the server.
 *
 * @property status The status of the list farms request.
 * @property message The message associated with the list farms response.
 * @property data The list of farms returned by the server.
 */
data class ListFarmResponse(
    val status: String,
    val message: String,
    val data: FarmDataResponse
)

/**
 * Data class representing the farm data in the list farms response.
 *
 * @property farms The list of farms returned by the server.
 */
data class FarmDataResponse(
    val farms: List<FarmResponse>
)

/**
 * Data class representing an individual farm's details.
 *
 * @property farm_id The ID of the farm.
 * @property name The name of the farm.
 * @property area The area of the farm.
 * @property unit_of_measure The unit of measurement for the farm's area.
 * @property status The status of the farm.
 * @property role The role associated with the user for this farm.
 */
data class FarmResponse(
    val farm_id: Int,
    val name: String,
    val area: Double,
    val unit_of_measure: String,
    val status: String,
    val role: String
)

data class GetFarmResponse(
    val status: String,
    val message: String,
    val data: FarmDataWrapper
)

data class FarmDataWrapper(
    val farm: FarmResponse
)


data class UpdateFarmRequest(
    val farm_id: Int,
    val name: String,
    val area: Double,
    val unitMeasure: String
)


data class UpdateFarmResponse(
    val status: String,
    val message: String,
    val data: FarmResponse
)

data class CreateInvitationRequest(
    val email: String,
    val suggested_role: String,
    val farm_id: Int
)

data class CreateInvitationResponse(
    val status: String,
    val message: String
)

data class Notification(
    val message: String,
    val date: String,
    val notification_type: String,
    val farm_id: Int,
    val reminder_time: String?,
    val notifications_id: Int,
    val user_id: Int,
    val invitation_id: Int,
    val notification_type_id: Int?,
    val status: String
)

data class NotificationResponse(
    val status: String,
    val message: String,
    val data: Any
)

/**
 * Data class representing the list collaborators response from the server.
 *
 * @property status The status of the list collaborators request.
 * @property message The message associated with the list collaborators response.
 * @property data The list of collaborators returned by the server.
 */
data class ListCollaboratorResponse(
    val status: String,
    val message: String,
    val data: List<CollaboratorResponse>
)

/**
 * Data class representing an individual farm's details.
 *
 * @property farm_id The ID of the farm.
 * @property name The name of the collaborator.
 * @property email The email of the collaborator.
 * @property role The role associated with the collaborator.
 */

data class CollaboratorResponse(
    val user_id: Int,
    val name: String,
    val email: String,
    val role: String
)

// Data class for editing collaborator request
data class EditCollaboratorRequest(
    val collaborator_user_id: Int,
    val new_role: String
)

// Data class for editing collaborator response
data class EditCollaboratorResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

data class CoffeeVariety(
    val coffee_variety_id: Int,
    val name: String
)

// Definir la estructura de Permiso
data class Permission(
    val permission_id: Int,
    val name: String,
    val description: String
)

// Actualización de Role para incluir la lista de permisos
data class Role(
    val role_id: Int,
    val name: String,
    val permissions: List<Permission> // Lista de permisos asociados al rol
)


data class UnitMeasure(
    val unit_of_measure_id: Int,
    val name: String,
    val abbreviation: String,
    val unit_of_measure_type: UnitMeasureType
)

data class UnitMeasureType(
    val unit_of_measure_type_id: Int,
    val name: String
)

data class CreatePlotRequest(
    val name: String,
    val coffee_variety_name: String,
    val latitude: String,
    val longitude: String,
    val altitude: String,
    val farm_id: Int
)


data class Plot(
    val plot_id: Int,
    val name: String,
    val coffee_variety_name: String,
    val latitude: String,
    val longitude: String,
    val altitude: String
)

data class ListPlotsResponse(
    val status: String,
    val message: String,
    val data: PlotsData
)

data class PlotsData(
    val plots: List<Plot>
)

// En com.example.coffetech.model

data class GetPlotResponse(
    val status: String,
    val message: String,
    val data: PlotDataWrapper
)

data class PlotDataWrapper(
    val plot: Plot
)


// En com.example.coffetech.model

/**
 * Solicitud para actualizar la información general de un lote.
 *
 * @property plot_id El ID del lote a actualizar.
 * @property name El nuevo nombre del lote.
 * @property coffee_variety_name La nueva variedad de café del lote.
 */
data class UpdatePlotGeneralInfoRequest(
    val plot_id: Int,
    val name: String,
    val coffee_variety_name: String
)

/**
 * Respuesta de la API para la actualización de la información general de un lote.
 *
 * @property status El estado de la operación ("success" o "error").
 * @property message El mensaje asociado a la respuesta.
 * @property data Los datos actualizados del lote.
 */
data class UpdatePlotGeneralInfoResponse(
    val status: String,
    val message: String,
    val data: Plot? = null
)
// UpdatePlotLocationRequest.kt

data class UpdatePlotLocationRequest(
    val plot_id: Int,
    val latitude: String,
    val longitude: String,
    val altitude: String
)


// UpdatePlotLocationResponse.kt
data class UpdatePlotLocationResponse(
    val status: String,
    val message: String,
    val data: PlotLocationData
)

data class PlotLocationData(
    val plot_id: Int,
    val latitude: String,
    val longitude: String,
    val altitude: String
)

//MODULE FLOWERINGS
data class Flowering(
    val flowering_id: Int,
    val plot_id: Int,
    val flowering_date: String,
    val harvest_date: String? = null,
    val status: String,
    val flowering_type_name: String,
)

data class ListFloweringsResponse(
    val status: String,
    val message: String,
    val data: FloweringsData
)

data class GetFloweringHistoryResponse(
    val status: String,
    val message: String,
    val data: FloweringHistoryData
)

data class FloweringHistoryData(
    val flowerings: List<Flowering>
)


data class FloweringDataWrapper(
    val flowering: Flowering
)


data class GetActiveFloweringsResponse(
    val status: String,
    val message: String,
    val data: FloweringsData
)

data class FloweringsData(
    val flowerings: List<Flowering>
)

data class CreateFloweringRequest(
    val plot_id: Int,
    val flowering_type_name: String,
    val flowering_date: String, // Formato "YYYY-MM-DD"
    val harvest_date: String? = null // Opcional
)


data class CreateFloweringResponse(
    val status: String,
    val message: String,
    val data: FloweringDataWrapper? = null
)

data class UpdateFloweringRequest(
    val flowering_id: Int,
    val harvest_date: String
)

data class DeleteFloweringResponse(
    val status: String,
    val message: String,
    val data: Any? = null
)

data class UpdateFloweringResponse(
    val status: String,
    val message: String,
    val data: FloweringDataWrapper? = null
)

data class Task(
    val task: String,
    val start_date: String,
    val end_date: String,
    val programar: String // Agregado el campo 'programar'
)

data class RecommendationsData(
    val recommendations: Recommendation
)

data class Recommendation(
    val flowering_id: Int,
    val flowering_type_name: String,
    val flowering_date: String,
    val current_date: String,
    val tasks: List<Task>
)

data class GetRecommendationsResponse(
    val status: String,
    val message: String,
    val data: RecommendationsData
)

//CulturalWorksTask

data class CulturalWorkTask(
    val cultural_work_task_id: Int,
    val cultural_works_name: String,
    val owner_name: String,
    val collaborator_name: String,
    val status: String,
    val task_date: String // Formato "yyyy-MM-dd"
)

data class CulturalWorkTasksData(
    val tasks: List<CulturalWorkTask>
)

data class ListCulturalWorkTasksResponse(
    val status: String,
    val message: String,
    val data: CulturalWorkTasksData
)

// Data class para un colaborador
data class Collaborator(
    val user_id: Int,
    val name: String
)

// Data class para la respuesta de colaboradores
data class CollaboratorsData(
    val collaborators: List<Collaborator>
)

data class CollaboratorsResponse(
    val status: String,
    val message: String,
    val data: CollaboratorsData
)

// Data class para la solicitud de creación de tarea cultural
data class CreateCulturalWorkTaskRequest(
    val cultural_works_name: String,
    val plot_id: Int,
    val reminder_owner: Boolean,
    val reminder_collaborator: Boolean,
    val collaborator_user_id: Int,
    val task_date: String // Formato "yyyy-MM-dd"
)

// Data class para la respuesta de creación de tarea cultural
data class CreateCulturalWorkTaskResponseData(
    val cultural_work_tasks_id: Int,
    val cultural_works_id: Int,
    val plot_id: Int,
    val status: String,
    val reminder_owner: Boolean,
    val reminder_collaborator: Boolean,
    val collaborator_user_id: Int,
    val owner_user_id: Int,
    val created_at: String,
    val task_date: String
)

data class CreateCulturalWorkTaskResponse(
    val status: String,
    val message: String,
    val data: CreateCulturalWorkTaskResponseData
)


// API service interface for interacting with backend services

/**
 * Retrofit API service interface for interacting with backend services.
 */
interface ApiService {
    /**
     * Registers a new user.
     *
     * @param request The request payload containing user registration data.
     * @return A [Call] object for the registration response.
     */
    @POST("/auth/register")
    fun registerUser(@Body request: RegisterRequest): Call<RegisterResponse>

    /**
     * Logs in a user.
     *
     * @param request The request payload containing user login data.
     * @return A [Call] object for the login response.
     */
    @POST("/auth/login")
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    /**
     * Verifies a user's account using a token.
     *
     * @param request The request payload containing the verification token.
     * @return A [Call] object for the verification response.
     */
    @POST("/auth/verify")
    fun verifyUser(@Body request: VerifyRequest): Call<VerifyResponse>

    /**
     * Initiates the forgot password process for a user.
     *
     * @param request The request payload containing the user's email.
     * @return A [Call] object for the forgot password response.
     */
    @POST("/auth/forgot-password")
    fun forgotPassword(@Body request: ForgotPasswordRequest): Call<ForgotPasswordResponse>

    /**
     * Verifies the forgot password token.
     *
     * @param request The request payload containing the verification token.
     * @return A [Call] object for the verification response.
     */
    @POST("/auth/verify-token")
    fun confirmForgotPassword(@Body request: VerifyRequest): Call<VerifyResponse>

    /**
     * Resets the user's password.
     *
     * @param request The request payload containing the token and new password.
     * @return A [Call] object for the reset password response.
     */
    @POST("/auth/reset-password")
    fun resetPassword(@Body request: ResetPasswordRequest): Call<ResetPasswordResponse>

    /**
     * Logs out a user.
     *
     * @param request The request payload containing the session token.
     * @return A [Call] object for the logout response.
     */
    @POST("/auth/logout")
    fun logoutUser(@Body request: LogoutRequest): Call<LogoutResponse>

    /**
     * Updates a user's profile.
     *
     * @param request The request payload containing updated profile information.
     * @param sessionToken The session token of the user making the request.
     * @return A [Call] object for the profile update response.
     */
    @POST("/auth/update-profile")
    fun updateProfile(
        @Body request: UpdateProfileRequest,
        @Query("session_token") sessionToken: String
    ): Call<UpdateProfileResponse>

    /**
     * Changes the user's password.
     *
     * @param request The request payload containing the current and new passwords.
     * @param sessionToken The session token of the user making the request.
     * @return A [Call] object for the change password response.
     */
    @PUT("/auth/change-password")
    fun changePassword(
        @Body request: ChangePasswordRequest,
        @Query("session_token") sessionToken: String
    ): Call<ChangePasswordResponse>

    /**
     * Retrieves a list of roles available to users.
     *
     * @return A [Call] object for the roles response.
     */
    @GET("/utils/list-roles")
    fun getRoles(): Call<ApiResponse<List<Role>>>

    /**
     * Retrieves a list of unit measures.
     *
     * @return A [Call] object for the unit measures response.
     */
    @GET("/utils/unit-measure")
    fun getUnitMeasures(): Call<ApiResponse<List<UnitMeasure>>>

    /**
     * Creates a new farm.
     *
     * @param sessionToken The session token of the user making the request.
     * @param request The request payload containing farm details.
     * @return A [Call] object for the create farm response.
     */

    @GET("/utils/list-coffee-varieties")
    fun getCoffeeVarieties(): Call<ApiResponse<List<CoffeeVariety>>>

    @POST("/farm/create-farm")
    fun createFarm(
        @Query("session_token") sessionToken: String,
        @Body request: CreateFarmRequest
    ): Call<CreateFarmResponse>

    /**
     * Lists all farms associated with the user's account.
     *
     * @param sessionToken The session token of the user making the request.
     * @return A [Call] object for the list farm response.
     */
    @POST("/farm/list-farm")
    fun listFarms(
        @Query("session_token") sessionToken: String
    ): Call<ListFarmResponse>


    // Método corregido para obtener los detalles de la finca
    @GET("/farm/get-farm/{farm_id}")
    fun getFarm(
        @Path("farm_id") farmId: Int,
        @Query("session_token") sessionToken: String
    ): Call<GetFarmResponse>


    @POST("/farm/update-farm")
    fun updateFarm(
        @Query("session_token") sessionToken: String,
        @Body request: UpdateFarmRequest
    ): Call<UpdateFarmResponse>

    @POST("/invitation/create-invitation")
    fun createInvitation(
        @Query("session_token") sessionToken: String,
        @Body request: CreateInvitationRequest
    ): Call<CreateInvitationResponse>

    @GET("/notification/get-notification")
    fun getNotifications(
        @Query("session_token") sessionToken: String
    ): Call<NotificationResponse>

    @POST("/invitation/respond-invitation/{invitation_id}")
    fun respondInvitation(
        @Path("invitation_id") invitationId: Int,
        @Query("action") action: String,
        @Query("session_token") sessionToken: String
    ): Call<ApiResponse<Any>>

    /**
     * Lists all collaborators associated with the user's account.
     *
     * @param sessionToken The session token of the user making the request.
     * @return A [Call] object for the list farm response.
     */
    @GET("/collaborators/list-collaborators")
    fun listCollaborators(
        @Query("farm_id") farmId: Int,
        @Query("session_token") sessionToken: String
    ): Call<ListCollaboratorResponse>

    @POST("/collaborators/edit-collaborator-role")
    fun editCollaboratorRole(
        @Query("farm_id") farmId: Int,
        @Query("session_token") sessionToken: String,
        @Body request: EditCollaboratorRequest
    ): Call<EditCollaboratorResponse>

    @POST("/collaborators/delete-collaborator")
    fun deleteCollaborator(
        @Query("farm_id") farmId: Int,
        @Query("session_token") sessionToken: String,
        @Body requestBody: Map<String, Int>
    ): Call<Void>

    @POST("/plots/create-plot")
    fun createPlot(
        @Query("session_token") sessionToken: String,
        @Body request: CreatePlotRequest
    ): Call<CreateFarmResponse>

    @GET("/plots/list-plots/{farm_id}")
    fun listPlots(
        @Path("farm_id") farmId: Int,
        @Query("session_token") sessionToken: String
    ): Call<ListPlotsResponse>


    @GET("/plots/get-plot/{plot_id}")
    fun getPlot(
        @Path("plot_id") plotId: Int,
        @Query("session_token") sessionToken: String
    ): Call<GetPlotResponse>


    /**
     * Actualiza la información general de un lote.
     *
     * @param sessionToken El token de sesión del usuario.
     * @param request El cuerpo de la solicitud con los datos a actualizar.
     * @return Un [Call] con la respuesta de la actualización.
     */
    @POST("/plots/update-plot-general-info")
    fun updatePlotGeneralInfo(
        @Query("session_token") sessionToken: String,
        @Body request: UpdatePlotGeneralInfoRequest
    ): Call<UpdatePlotGeneralInfoResponse>

    @POST("/plots/update-plot-location")
    fun updatePlotLocation(
        @Query("session_token") sessionToken: String,
        @Body request: UpdatePlotLocationRequest
    ): Call<UpdatePlotLocationResponse>

    @GET("/flowering/get-active-flowerings/{plot_id}")
    fun getActiveFlowerings(
        @Path("plot_id") plotId: Int,
        @Query("session_token") sessionToken: String
    ): Call<GetActiveFloweringsResponse>

    @GET("/flowering/get-flowering-history/{plot_id}")
    fun getFloweringHistory(
        @Path("plot_id") plotId: Int,
        @Query("session_token") sessionToken: String
    ): Call<GetFloweringHistoryResponse>

    /**
     * Crea una nueva floración.
     *
     * @param sessionToken El token de sesión del usuario.
     * @param request La solicitud para crear la floración.
     * @return Un [Call] con la respuesta de la creación de la floración.
     */
    @POST("/flowering/create-flowering")
    fun createFlowering(
        @Query("session_token") sessionToken: String,
        @Body request: CreateFloweringRequest
    ): Call<CreateFloweringResponse>

    @POST("flowering/update-flowering")
    fun updateFlowering(
        @Query("session_token") sessionToken: String,
        @Body request: UpdateFloweringRequest
    ): Call<UpdateFloweringResponse>

    @POST("flowering/delete-flowering/{flowering_id}")
    fun deleteFlowering(
        @Path("flowering_id") floweringId: Int,
        @Query("session_token") sessionToken: String
    ): Call<DeleteFloweringResponse>

    @GET("/flowering/get-recommendations/{flowering_id}")
    fun getRecommendations(
        @Path("flowering_id") floweringId: Int,
        @Query("session_token") sessionToken: String
    ): Call<GetRecommendationsResponse>

    // CulturalWorkTask

    @GET("/culturalWorkTask/list-cultural-work-tasks/{plot_id}")
    fun listCulturalWorkTasks(
        @Path("plot_id") plotId: Int,
        @Query("session_token") sessionToken: String
    ): Call<ListCulturalWorkTasksResponse>

    @GET("/culturalWorkTask/collaborators-with-complete-permission")
    suspend fun getCollaboratorsWithCompletePermission(
        @Query("plot_id") plotId: Int,
        @Query("session_token") sessionToken: String
    ): CollaboratorsResponse

    @POST("/culturalWorkTask/create-cultural-work-task")
    suspend fun createCulturalWorkTask(
        @Query("session_token") sessionToken: String,
        @Body request: CreateCulturalWorkTaskRequest
    ): CreateCulturalWorkTaskResponse


}

// OpenElevationResponse.kt

data class OpenElevationResponse(
    val results: List<ElevationResult>
)

data class ElevationResult(
    val elevation: Double,
    val location: Location
)

data class Location(
    val latitude: Double,
    val longitude: Double
)


// OpenElevationService.kt

interface OpenElevationService {
    @GET("/api/v1/lookup")
    suspend fun getElevation(
        @Query("locations") locations: String
    ): OpenElevationResponse
}

