package com.example.coffetech.viewmodel.healthcheck

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ResultHealthCheckViewModel: ViewModel() {

    var errorMessage = MutableStateFlow("")
        private set
    var isLoading = MutableStateFlow(false)
        private set
}