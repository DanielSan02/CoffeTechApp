package com.example.coffetech.viewmodel.CulturalWorkTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.example.coffetech.model.CreateCulturalWorkTaskRequest
import com.example.coffetech.model.RetrofitInstance
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class ReminderViewModel : ViewModel() {
    private val _isReminderForUser = MutableStateFlow(false)
    val isReminderForUser: StateFlow<Boolean> = _isReminderForUser.asStateFlow()

    private val _isReminderForCollaborator = MutableStateFlow(false)
    val isReminderForCollaborator: StateFlow<Boolean> = _isReminderForCollaborator.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    // Token de sesión (debería obtenerse de manera segura, por ejemplo, desde un repositorio)
    private val sessionToken = "OGBTS1Adblk5Mjc3J3qHfbcwYMdSvdRq" // Reemplazar con la obtención real

    fun setReminderForUser(value: Boolean) {
        _isReminderForUser.value = value
    }

    fun setReminderForCollaborator(value: Boolean) {
        _isReminderForCollaborator.value = value
    }

    fun saveReminders(
        plotId: Int,
        culturalWorkType: String,
        date: String,
        collaboratorUserId: Int,
        navController: NavController
    ) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Asumiendo que la API para guardar recordatorios es similar a la de crear tareas culturales
                val request = CreateCulturalWorkTaskRequest(
                    cultural_works_name = culturalWorkType,
                    plot_id = plotId,
                    reminder_owner = _isReminderForUser.value,
                    reminder_collaborator = _isReminderForCollaborator.value,
                    collaborator_user_id = collaboratorUserId,
                    task_date = date
                )

                val response = RetrofitInstance.api.createCulturalWorkTask(sessionToken, request)
                if (response.status == "success") {
                    // Navegar dos pantallas atrás
                    navController.popBackStack() // Volver una pantalla
                    navController.popBackStack() // Volver otra pantalla
                    navController.popBackStack() // Volver una pantalla

                } else {
                    _errorMessage.value = response.message
                }
            } catch (e: Exception) {
                _errorMessage.value = e.localizedMessage
            } finally {
                _isLoading.value = false
            }
        }
    }
}
