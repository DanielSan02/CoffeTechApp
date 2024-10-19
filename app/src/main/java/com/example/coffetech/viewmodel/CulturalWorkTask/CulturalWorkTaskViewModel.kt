package com.example.coffetech.viewmodel.cultural

import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

data class CulturalWorkTask(
    val id: Int,
    val name: String,
    val assignedTo: String, // "me" o "otros colaboradores"
    val assignedToName: String, // Nombre del colaborador asignado
    val state: String,
    val date: Long // Timestamp en milisegundos
)

class CulturalWorkTaskViewModel(
    initialTasks: List<CulturalWorkTask> = emptyList() // Tareas iniciales opcionales
) : ViewModel() {

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

    private val _isDateOrderDropdownExpanded = MutableStateFlow(false)
    val isDateOrderDropdownExpanded: StateFlow<Boolean> = _isDateOrderDropdownExpanded.asStateFlow()

    fun setDateOrderDropdownExpanded(isExpanded: Boolean) {
        _isDateOrderDropdownExpanded.value = isExpanded
    }

    // Listas de opciones para cada dropdown
    private val _estadoOptions = MutableStateFlow<List<String>>(listOf("Por hacer", "Terminado"))
    val estadoOptions: StateFlow<List<String>> = _estadoOptions.asStateFlow()

    private val _assignedToOptions = MutableStateFlow<List<String>>(listOf("A mi", "A otros colaboradores"))
    val assignedToOptions: StateFlow<List<String>> = _assignedToOptions.asStateFlow()

    private val _dateOrderOptions = MutableStateFlow<List<String>>(listOf("Recientes primero", "Antiguos primero"))
    val dateOrderOptions: StateFlow<List<String>> = _dateOrderOptions.asStateFlow()

    // Función para cargar tareas
    fun loadTasks(nameFarm: String, plotName: String) {
        viewModelScope.launch {
            _isLoading.value = true
            try {
                // Simulación de carga de tareas
                val loadedTasks = listOf(
                    CulturalWorkTask(
                        id = 1,
                        name = "Recolección de Café",
                        assignedTo = "me",
                        assignedToName = "Juan Pérez",
                        state = "Por hacer",
                        date = 1672531199000L
                    ),
                    CulturalWorkTask(
                        id = 2,
                        name = "Poda de Árboles",
                        assignedTo = "otros colaboradores",
                        assignedToName = "María García",
                        state = "Terminado",
                        date = 1672617599000L
                    ),
                    CulturalWorkTask(
                        id = 3,
                        name = "Aplicación de Fertilizantes",
                        assignedTo = "me",
                        assignedToName = "Juan Pérez",
                        state = "Por hacer",
                        date = 1672703999000L
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

    fun setStateFilter(state: String?) {
        _stateFilter.value = state
    }

    fun setAssignedToFilter(assignedTo: String?) {
        _assignedToFilter.value = assignedTo
    }

    fun setDateOrder(isRecentFirst: Boolean) {
        _dateOrder.value = isRecentFirst
    }

    // Función para obtener tareas filtradas y ordenadas
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
                task.state == stateValue
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
                task.assignedTo == assignedToNormalized
            }
        }

        // Filtrar por nombre de tarea
        if (query.text.isNotBlank()) {
            filtered = filtered.filter { task ->
                task.name.contains(query.text, ignoreCase = true)
            }
        }

        // Ordenar por fecha
        if (isRecentFirst) {
            filtered.sortedByDescending { it.date }
        } else {
            filtered.sortedBy { it.date }
        }
    }.stateIn(viewModelScope, SharingStarted.Lazily, emptyList())
}
