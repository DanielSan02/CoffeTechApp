package com.example.coffetech.viewmodel.CulturalWorkTask

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class EditCulturalWorkViewModel: ViewModel() {

    private val _flowering_date = MutableStateFlow("")
    val flowering_date: StateFlow<String> = _flowering_date.asStateFlow()

    private val _typeCulturalWork = MutableStateFlow<List<String>>(emptyList())
    val typeCulturalWork: StateFlow<List<String>> = _typeCulturalWork.asStateFlow()

    var isLoading = MutableStateFlow(false)
        private set

    var isFormSubmitted = MutableStateFlow(false)
        private set

}