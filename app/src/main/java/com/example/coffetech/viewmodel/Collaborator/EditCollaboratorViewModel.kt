package com.example.coffetech.viewmodel.Collaborator

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import androidx.navigation.NavController
import android.content.Context
import android.widget.Toast
import com.example.coffetech.model.EditCollaboratorRequest
import com.example.coffetech.model.EditCollaboratorResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditCollaboratorViewModel : ViewModel() {

    // Estado del rol seleccionado
    private val _collaboratorRole = MutableStateFlow<List<String>>(emptyList())
    val collaboratorRole: StateFlow<List<String>> = _collaboratorRole.asStateFlow()

    private val _selectedRole = MutableStateFlow("Seleccione un rol")
    val selectedRole: StateFlow<String> = _selectedRole.asStateFlow()

    var errorMessage = MutableStateFlow("")
        private set
    var isLoading = MutableStateFlow(false)
        private set


    // Estado para rastrear si hay cambios pendientes
    private val _hasChanges = MutableStateFlow(false)
    val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

    // Guardar valores iniciales para comparación
    private var initialSelectedRole = ""

    // Inicializar los valores iniciales

    fun initializeValues(selectedRole: String) {
        initialSelectedRole = selectedRole
        _selectedRole.value = selectedRole
    }

    // Validar la entrada para asegurarse de que el rol sea válido
    private fun validateInputs(): Boolean {
        if (_selectedRole.value == "Seleccione un rol") {
            errorMessage.value = "Debe seleccionar una opción válida para el rol."
            return false
        }

        return true
    }

    // Cargar roles desde SharedPreferences
    fun loadRolesFromSharedPreferences(context: Context) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val roles = sharedPreferencesHelper.getRoles()?.map { it.name } ?: emptyList()
        _collaboratorRole.value = roles
    }

    // Función para editar el colaborador
    fun editCollaborator(context: Context, farmId: Int, collaboratorId: Int, navController: NavController) {
        if (!validateInputs()) return

        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            return
        }

        isLoading.value = true

        // Crear objeto de solicitud para editar el colaborador
        val request = EditCollaboratorRequest(
            collaborator_user_id = collaboratorId,
            new_role = selectedRole.value
        )

        // Llamar a la API para editar el colaborador
        RetrofitInstance.api.editCollaboratorRole(farmId, sessionToken, request).enqueue(object : Callback<EditCollaboratorResponse> {
            override fun onResponse(call: Call<EditCollaboratorResponse>, response: Response<EditCollaboratorResponse>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    responseBody?.let {
                        if (it.status == "success") {
                            Toast.makeText(context, "Colaborador actualizado correctamente.", Toast.LENGTH_LONG).show()
                            navController.popBackStack() // Regresar a la pantalla anterior
                        } else {
                            errorMessage.value = it.message
                            Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                        }
                    }
                } else {
                    errorMessage.value = "Error al actualizar el colaborador."
                    Toast.makeText(context, "Error al actualizar el colaborador.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<EditCollaboratorResponse>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = "Error de conexión"
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })
    }

    // Función para manejar el cambio de rol
    fun onCollaboratorRoleChange(newRole: String) {
        _selectedRole.value = newRole
        checkForChanges()
    }

    // Verificar si hay cambios para habilitar/deshabilitar el botón de guardar
    private fun checkForChanges() {
        _hasChanges.value = _selectedRole.value != initialSelectedRole
    }

    fun deleteCollaborator(context: Context, farmId: Int, collaboratorId: Int, navController: NavController) {
        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken()

        if (sessionToken == null) {
            errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(context, "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.", Toast.LENGTH_LONG).show()
            return
        }

        isLoading.value = true

        // Crear objeto de solicitud para eliminar el colaborador
        val requestBody = mapOf("collaborator_user_id" to collaboratorId)

        // Llamar a la API para eliminar el colaborador
        RetrofitInstance.api.deleteCollaborator(farmId, sessionToken, requestBody).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                isLoading.value = false
                if (response.isSuccessful) {
                    Toast.makeText(context, "Colaborador eliminado correctamente.", Toast.LENGTH_LONG).show()
                    navController.popBackStack() // Regresar a la pantalla anterior
                } else {
                    errorMessage.value = "Error al eliminar el colaborador."
                    Toast.makeText(context, "Error al eliminar el colaborador.", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                isLoading.value = false
                errorMessage.value = "Error de conexión"
                Toast.makeText(context, "Error de conexión", Toast.LENGTH_LONG).show()
            }
        })
    }


}
