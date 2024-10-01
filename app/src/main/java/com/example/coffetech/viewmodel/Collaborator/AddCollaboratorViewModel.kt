package com.example.coffetech.viewmodel.Collaborator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.navigation.NavController

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import com.example.coffetech.model.CreateFarmRequest
import com.example.coffetech.model.CreateFarmResponse
import com.example.coffetech.model.CreateInvitationRequest
import com.example.coffetech.model.CreateInvitationResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AddCollaboratorViewModel : ViewModel() {

    // Estados para los datos
    private val _collaboratorEmail = MutableStateFlow("")
    val collaboratorEmail: StateFlow<String> = _collaboratorEmail.asStateFlow()

    // Estado del menú de dropdown
    private val _isDropdownExpanded = mutableStateOf(false)
    val isDropdownExpanded = _isDropdownExpanded


    private val _collaboratorRole = MutableStateFlow<List<String>>(emptyList())
    val collaboratorRole: StateFlow<List<String>> = _collaboratorRole.asStateFlow()

    private val _selectedRole = MutableStateFlow("Seleccione un rol")
    val selectedRole: StateFlow<String> = _selectedRole.asStateFlow()

    var errorMessage = MutableStateFlow("")
        private set
    var isLoading = MutableStateFlow(false)
        private set

    fun onCollaboratorEmailChange(newName: String) {
        _collaboratorEmail.value = newName
    }

    fun onCollaboratorRoleChange(newRole: String) {
        _selectedRole.value = newRole
    }

    fun loadRolesFromSharedPreferences(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val roles = sharedPreferencesHelper.getRoles()?.map { it.name } ?: emptyList()
        _collaboratorRole.value = roles
    }




    fun validateInputs(): Boolean {
        if (_collaboratorEmail.value.isBlank()) {
            errorMessage.value = "El correo del colaborador no puede estar vacío."
            return false
        }

        // Valida que sea un formato de correo válido (opcional)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(_collaboratorEmail.value).matches()) {
            errorMessage.value = "El correo del colaborador no es válido."
            return false
        }

        // Validación del rol seleccionado
        if (_selectedRole.value == "Seleccione un rol") {
            errorMessage.value = "Debe seleccionar un rol válido."
            return false
        }

        errorMessage.value = ""  // Limpiar el mensaje de error si no hay problemas
        return true
    }

    fun onCreate(navController: NavController, context: Context, farmId: Int) {
        if (!validateInputs()) {
            return
        }

        errorMessage.value = ""
        isLoading.value = true

        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken() ?: run {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            isLoading.value = false
            return
        }

        // Crear el objeto de solicitud
        val createInvitationRequest = CreateInvitationRequest(
            email = _collaboratorEmail.value,
            suggested_role = _selectedRole.value,
            farm_id = farmId
        )

        // Realizar la solicitud al servidor
        RetrofitInstance.api.createInvitation(sessionToken, createInvitationRequest).enqueue(object :
            Callback<CreateInvitationResponse> {
            override fun onResponse(call: Call<CreateInvitationResponse>, response: Response<CreateInvitationResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, "Invitación creada exitosamente", Toast.LENGTH_LONG).show()
                            navController.popBackStack() // Regresar a la pantalla anterior
                        } else {
                            errorMessage.value = it.message
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
                                "Error desconocido al crear la invitación."
                            }
                            this@AddCollaboratorViewModel.errorMessage.value = errorMessage
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        } catch (e: Exception) {
                            this@AddCollaboratorViewModel.errorMessage.value = "Error al procesar la respuesta del servidor."
                            Toast.makeText(context, "Error al procesar la respuesta del servidor.", Toast.LENGTH_LONG).show()
                        }
                    } ?: run {
                        this@AddCollaboratorViewModel.errorMessage.value = "Respuesta vacía del servidor."
                        Toast.makeText(context, "Respuesta vacía del servidor.", Toast.LENGTH_LONG).show()
                    }
                }
            }

            override fun onFailure(call: Call<CreateInvitationResponse>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = "Error de conexión: ${t.message}"
                Toast.makeText(context, "Error de conexión: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }




}



