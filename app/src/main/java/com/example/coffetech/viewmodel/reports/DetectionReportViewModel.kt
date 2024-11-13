// DetectionReportViewModel.kt
package com.example.coffetech.viewmodel.reports

import android.content.ContentValues.TAG
import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffetech.model.DetectionHistory
import com.example.coffetech.model.DetectionHistoryRequest
import com.example.coffetech.model.DetectionHistoryResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetectionReportViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private val _detectionData = MutableStateFlow<List<DetectionHistory>?>(null)
    val detectionData: StateFlow<List<DetectionHistory>?> = _detectionData

    fun getDetectionHistory(
        context: Context,
        plotIds: List<Int>,
        fechaInicio: String,
        fechaFin: String,
    ) {

        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken() ?: run {
            Log.e(TAG, "No se encontró el token de sesión.")
            _errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(
                context,
                "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.",
                Toast.LENGTH_LONG
            ).show()
            _isLoading.value = false
            return
        }

        _isLoading.value = true
        _errorMessage.value = null

        val request = DetectionHistoryRequest(
            plotIds = plotIds,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin
        )

        RetrofitInstance.api.getDetectionHistory(sessionToken, request)
            .enqueue(object : Callback<DetectionHistoryResponse> {
                override fun onResponse(
                    call: Call<DetectionHistoryResponse>,
                    response: Response<DetectionHistoryResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.status == "success") {
                            _detectionData.value = body.data.detections
                        } else {
                            _errorMessage.value = body?.message ?: "Error desconocido."
                        }
                    } else {
                        _errorMessage.value = "Error de servidor: ${response.code()}"
                    }
                }

                override fun onFailure(call: Call<DetectionHistoryResponse>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = t.message ?: "Fallo al conectar con el servidor."
                    Log.e("DetectionReportViewModel", "Error: ${t.message}")
                }
            })
    }
}
