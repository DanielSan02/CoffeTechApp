package com.example.coffetech.viewmodel.flowering

import androidx.lifecycle.ViewModel
import com.example.coffetech.model.Flowering
import com.example.coffetech.model.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainFloweringViewModel: ViewModel() {


    private val _plotName = MutableStateFlow("")
    val plotName: StateFlow<String> = _plotName.asStateFlow()

    private val _flowering_date = MutableStateFlow("")
    val flowering_date: StateFlow<String> = _flowering_date.asStateFlow()

    // Lista completa de tareas
    private val _tasks = MutableStateFlow<List<Task>>(emptyList())
    val tasks: StateFlow<List<Task>> = _tasks.asStateFlow()

    private val _start_date = MutableStateFlow("")
    val start_date: StateFlow<String> = _start_date.asStateFlow()

    private val _end_date = MutableStateFlow("")
    val end_date: StateFlow<String> = _end_date.asStateFlow()


    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()
}