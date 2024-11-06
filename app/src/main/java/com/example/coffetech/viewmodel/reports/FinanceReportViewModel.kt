// FinanceReportViewModel.kt
package com.example.coffetech.viewmodel.reports

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.model.FinancialReportData
import com.example.coffetech.model.FinancialReportRequest
import com.example.coffetech.model.FinancialReportResponse

import com.example.coffetech.utils.SharedPreferencesHelper
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FinanceReportViewModel : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _reportData = MutableStateFlow<FinancialReportData?>(null)
    val reportData: StateFlow<FinancialReportData?> = _reportData.asStateFlow()


    fun getFinancialReport(
        context: Context,
        plotIds: List<Int>,
        fechaInicio: String,
        fechaFin: String
    ) {
        _isLoading.value = true
        _errorMessage.value = null

        val sharedPreferencesHelper = SharedPreferencesHelper(context)
        val sessionToken = sharedPreferencesHelper.getSessionToken() ?: run {
            _errorMessage.value = "No se encontró el token de sesión."
            Toast.makeText(
                context,
                "Error: No se encontró el token de sesión. Por favor, inicia sesión nuevamente.",
                Toast.LENGTH_LONG
            ).show()
            _isLoading.value = false
            return
        }

        val request = FinancialReportRequest(
            plot_ids = plotIds,
            fechaInicio = fechaInicio,
            fechaFin = fechaFin
        )

        RetrofitInstance.api.getFinancialReport(sessionToken, request)
            .enqueue(object : Callback<FinancialReportResponse> {
                override fun onResponse(
                    call: Call<FinancialReportResponse>,
                    response: Response<FinancialReportResponse>
                ) {
                    _isLoading.value = false
                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            if (it.status == "success") {
                                _reportData.value = it.data
                            } else {
                                _errorMessage.value = it.message ?: "Error desconocido al generar el reporte."
                            }
                        } ?: run {
                            _errorMessage.value = "No se pudo generar el reporte."
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        errorBody?.let {
                            try {
                                val errorJson = JSONObject(it)
                                _errorMessage.value = errorJson.optString(
                                    "message",
                                    "Error desconocido al generar el reporte."
                                )
                            } catch (e: Exception) {
                                _errorMessage.value = "Error al procesar la respuesta del servidor."
                            }
                        } ?: run {
                            _errorMessage.value = "Error al generar el reporte: respuesta vacía del servidor."
                        }
                    }
                }

                override fun onFailure(call: Call<FinancialReportResponse>, t: Throwable) {
                    _isLoading.value = false
                    _errorMessage.value = "Error de conexión"
                }
            })
    }

    fun generarRecomendaciones(): List<LoteRecommendation> {
        val recomendaciones = mutableListOf<LoteRecommendation>()

        reportData.value?.plot_financials?.forEach { plot ->
            val ingresos = plot.ingresos
            val gastos = plot.gastos
            val balance = plot.balance

            val rendimiento = when {
                balance > 0.2 * ingresos -> "Excelente rendimiento"
                balance > 0.1 * ingresos -> "Buen rendimiento"
                else -> "Rendimiento bajo"
            }

            val recomendacionesLote = mutableListOf<String>()

            when (rendimiento) {
                "Excelente rendimiento" -> {
                    recomendacionesLote.add("Recomendable mantener o incrementar la inversión.")
                }
                "Buen rendimiento" -> {
                    recomendacionesLote.add("Oportunidades para optimizar gastos en categorías específicas.")
                }
                "Rendimiento bajo" -> {
                    recomendacionesLote.add("Se recomienda revisar gastos y explorar estrategias para aumentar ingresos.")
                }
            }

            // Añadir recomendaciones específicas para optimizar gastos
            if (rendimiento != "Excelente rendimiento") {
                plot.gastos_por_categoria.forEach { categoria ->
                    when (categoria.category_name) {
                        "Pagos a colaboradores" -> {
                            recomendacionesLote.add("Revisar y optimizar los pagos a colaboradores.")
                        }
                        "Fertilizantes" -> {
                            recomendacionesLote.add("Reducir el uso de fertilizantes o buscar alternativas más económicas.")
                        }
                        "Plaguicidas/herbicidas" -> {
                            recomendacionesLote.add("Evaluar el uso de plaguicidas/herbicidas y buscar alternativas sostenibles.")
                        }
                        "Otros" -> {
                            recomendacionesLote.add("Analizar los gastos en 'Otros' y buscar posibles optimizaciones.")
                        }
                    }
                }
            }

            // Recomendaciones para incrementar ingresos
            if (rendimiento == "Rendimiento bajo") {
                plot.ingresos_por_categoria.forEach { categoria ->
                    when (categoria.category_name) {
                        "Venta de café" -> {
                            recomendacionesLote.add("Explorar nuevos mercados para la venta de café o mejorar la calidad del producto.")
                        }
                        "Otros" -> {
                            recomendacionesLote.add("Diversificar las fuentes de ingresos, como añadir otros productos agrícolas.")
                        }
                    }
                }
            }

            recomendaciones.add(
                LoteRecommendation(
                    loteNombre = plot.plot_name,
                    rendimiento = rendimiento,
                    recomendaciones = recomendacionesLote
                )
            )
        }

        return recomendaciones
    }



}
data class LoteRecommendation(
    val loteNombre: String,
    val rendimiento: String,
    val recomendaciones: List<String>
)
