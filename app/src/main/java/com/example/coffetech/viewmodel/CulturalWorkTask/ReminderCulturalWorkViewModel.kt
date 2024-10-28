package com.example.coffetech.viewmodel.CulturalWorkTask

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ReminderViewModel : ViewModel() {
    private val _isReminderForUser = MutableStateFlow(false)
    val isReminderForUser: StateFlow<Boolean> = _isReminderForUser

    private val _isReminderForCollaborator = MutableStateFlow(false)
    val isReminderForCollaborator: StateFlow<Boolean> = _isReminderForCollaborator

    fun setReminderForUser(value: Boolean) {
        _isReminderForUser.value = value
    }

    fun setReminderForCollaborator(value: Boolean) {
        _isReminderForCollaborator.value = value
    }

    fun saveReminders() {
        viewModelScope.launch {
        }
    }
}
