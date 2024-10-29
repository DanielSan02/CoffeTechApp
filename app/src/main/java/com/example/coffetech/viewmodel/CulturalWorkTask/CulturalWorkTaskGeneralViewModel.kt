package com.example.coffetech.viewmodel.CulturalWorkTask

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffetech.model.CulturalWorkTask
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch



class CulturalWorkTaskGeneralViewModel(
initialTasks: List<CulturalWorkTask> = emptyList() // Tareas iniciales opcionales
) : ViewModel() {

    // Listas de opciones para cada dropdown
    private val _estadoOptions = MutableStateFlow<List<String>>(listOf("Por hacer", "Terminado"))
    val estadoOptions: StateFlow<List<String>> = _estadoOptions.asStateFlow()

    private val _assignedToOptions = MutableStateFlow<List<String>>(listOf("A mi", "A otros colaboradores"))
    val assignedToOptions: StateFlow<List<String>> = _assignedToOptions.asStateFlow()

    private val _dateOrderOptions = MutableStateFlow<List<String>>(listOf("Recientes primero", "Antiguos primero"))
    val dateOrderOptions: StateFlow<List<String>> = _dateOrderOptions.asStateFlow()

    private val _tasks = MutableStateFlow<List<CulturalWorkTask>>(initialTasks)
    val tasks: StateFlow<List<CulturalWorkTask>> = _tasks.asStateFlow()

    private val _stateFilter = MutableStateFlow<String?>(null)
    val stateFilter: StateFlow<String?> = _stateFilter.asStateFlow()

    private val _assignedToFilter = MutableStateFlow<String?>(null)
    val assignedToFilter: StateFlow<String?> = _assignedToFilter.asStateFlow()

    private val _dateOrder = MutableStateFlow<Boolean>(true) // true: Recientes primero, false: Antiguos primero
    val dateOrder: StateFlow<Boolean> = _dateOrder.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    // Estado de búsqueda utilizando TextFieldValue
    private val _searchQuery = MutableStateFlow(TextFieldValue(""))
    val searchQuery: StateFlow<TextFieldValue> = _searchQuery.asStateFlow()

    // Función para actualizar la consulta de búsqueda
    fun onSearchQueryChanged(query: TextFieldValue) {
        _searchQuery.value = query
    }

    // Estados de expansión para cada dropdown
    private val _isEstadoDropdownExpanded = MutableStateFlow(false)
    val isEstadoDropdownExpanded: StateFlow<Boolean> = _isEstadoDropdownExpanded.asStateFlow()

    fun setEstadoDropdownExpanded(isExpanded: Boolean) {
        _isEstadoDropdownExpanded.value = isExpanded
    }

    private val _isAssignedDropdownExpanded = MutableStateFlow(false)
    val isAssignedDropdownExpanded: StateFlow<Boolean> = _isAssignedDropdownExpanded.asStateFlow()

    fun setAssignedDropdownExpanded(isExpanded: Boolean) {
        _isAssignedDropdownExpanded.value = isExpanded
    }

    // Función para cargar tareas
    fun loadTasks(nameFarm: String, plotName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                val loadedTasks = listOf(
                    CulturalWorkTask(
                        cultural_work_task_id = 1,
                        cultural_works_name = "Recolección de Café",
                        collaborator_name = "naty rmu",
                        owner_name = "Natalia Rodríguez Mu",
                        status = "Por hacer",
                        task_date = "2024-10-28"
                    ),
                    CulturalWorkTask(
                        cultural_work_task_id = 2,
                        cultural_works_name = "Poda de Árboles",
                        collaborator_name = "otros colaboradores",
                        owner_name = "María García",
                        status = "Terminado",
                        task_date = "2024-10-27"
                    )
                )
                _tasks.value = loadedTasks
            } catch (e: Exception) {
                _errorMessage.value = "Error cargando las tareas culturales."
            } finally {
                _isLoading.value = false
            }
        }
    }


    val filteredTasks: StateFlow<List<CulturalWorkTask>> = combine(
        _tasks,
        _stateFilter,
        _assignedToFilter,
        _dateOrder,
        _searchQuery // Incluye la consulta de búsqueda
    ) { tasks, state, assignedTo, isRecentFirst, query ->
        var filtered = tasks

        // Filtrar por estado
        state?.let { stateValue ->
            filtered = filtered.filter { task ->
                task.status == stateValue
            }
        }

        // Filtrar por asignación
        assignedTo?.let { assignedValue ->
            val assignedToNormalized = when (assignedValue) {
                "A mi" -> "me"
                "A otros colaboradores" -> "otros colaboradores"
                else -> assignedValue
            }
            filtered = filtered.filter { task ->
                task.collaborator_name == assignedToNormalized
            }
        }

        // Filtrar por nombre de tarea
        if (query.text.isNotBlank()) {
            filtered = filtered.filter { task ->
                task.cultural_works_name.contains(query.text, ignoreCase = true)
            }
        }

        // Ordenar por fecha
        if (isRecentFirst) {
            filtered.sortedByDescending { it.task_date }
        } else {
            filtered.sortedBy { it.task_date }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}

