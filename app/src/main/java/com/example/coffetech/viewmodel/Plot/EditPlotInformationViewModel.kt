        package com.example.coffetech.viewmodel.Plot


        import androidx.lifecycle.ViewModel
        import kotlinx.coroutines.flow.MutableStateFlow
        import kotlinx.coroutines.flow.StateFlow
        import kotlinx.coroutines.flow.asStateFlow

        class EditPlotInformationViewModel: ViewModel() {

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
        }