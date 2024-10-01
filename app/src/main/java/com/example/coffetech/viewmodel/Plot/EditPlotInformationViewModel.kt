        package com.example.coffetech.viewmodel.Plot


        import androidx.lifecycle.ViewModel
        import androidx.lifecycle.viewModelScope
        import kotlinx.coroutines.flow.MutableStateFlow
        import kotlinx.coroutines.flow.StateFlow
        import kotlinx.coroutines.flow.asStateFlow
        import kotlinx.coroutines.launch

        class EditPlotInformationViewModel : ViewModel() {

            // Estados de nombre, área, y otros datos de la finca
            private val _plotName = MutableStateFlow("")
            val plotName: StateFlow<String> = _plotName.asStateFlow()

            private val _plotCoffeeVariety = MutableStateFlow<List<String>>(emptyList())
            val plotCoffeeVariety: StateFlow<List<String>> = _plotCoffeeVariety.asStateFlow()

            private val _selectedVariety = MutableStateFlow("")
            val selectedVariety: StateFlow<String> = _selectedVariety.asStateFlow()

            var errorMessage = MutableStateFlow("")
                private set
            var isLoading = MutableStateFlow(false)
                private set

            // Estado para rastrear si hay cambios pendientes
            private val _hasChanges = MutableStateFlow(false)
            val hasChanges: StateFlow<Boolean> = _hasChanges.asStateFlow()

            // Función para manejar la acción del botón "Floraciones"
            fun onFloracionesClick(plotId: Int, onSuccess: () -> Unit, onError: (String) -> Unit) {
                viewModelScope.launch {
                    isLoading.value = true
                    errorMessage.value = "" // Limpia cualquier mensaje de error previo

                    try {
                        // Realiza alguna lógica, como guardar o actualizar información relacionada con "Floraciones"
                        // Aquí podrías llamar a un repositorio para ejecutar una operación

                        // Supón que hay una función en un repositorio llamada `updateFloraciones`
                        // repository.updateFloraciones(plotId)

                        // Si la operación es exitosa, se puede llamar a `onSuccess`
                        onSuccess()
                    } catch (e: Exception) {
                        // Manejar el error y actualizar el mensaje de error
                        errorMessage.value = "Error al procesar Floraciones: ${e.message}"
                        onError(errorMessage.value)
                    } finally {
                        isLoading.value = false
                    }
                }
            }

            // Método opcional para actualizar el estado si se detectan cambios
            fun setHasChanges(hasChanges: Boolean) {
                _hasChanges.value = hasChanges
            }
        }

