// FinanceReportView.kt
package com.example.coffetech.view

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.TopBarWithBackArrow
import com.example.coffetech.model.FinancialReportData
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.components.MPBarChartComparison
import com.example.coffetech.view.components.MPPieChart
import com.example.coffetech.view.components.PdfFloatingActionButton
import com.example.coffetech.view.components.generatePdf
import com.example.coffetech.viewmodel.reports.FinanceReportViewModel
import com.example.coffetech.viewmodel.reports.LoteRecommendation

@Composable
fun FinanceReportView(
    modifier: Modifier = Modifier,
    navController: NavController,
    plotIds: List<Int>,
    startDate: String,
    endDate: String,
    viewModel: FinanceReportViewModel = viewModel()
) {
    val context = LocalContext.current

    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val reportData by viewModel.reportData.collectAsState()

    // Generar las recomendaciones después de cargar los datos
    val recomendaciones = remember(reportData) {
        reportData?.let { viewModel.generarRecomendaciones() } ?: emptyList()
    }

    LaunchedEffect(Unit) {
        viewModel.getFinancialReport(
            context = context,
            plotIds = plotIds,
            fechaInicio = startDate,
            fechaFin = endDate
        )
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
    ) {
        TopBarWithBackArrow(
            onBackClick = { navController.popBackStack() },
            title = "Reporte financiero"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            } else if (errorMessage != null) {
                Text(
                    text = errorMessage ?: "",
                    color = Color.Red,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            } else if (reportData != null) {
                // Pasar las recomendaciones a ReportContent
                ReportContent(reportData = reportData!!, recomendaciones = recomendaciones)
            } else {
                Text(
                    text = "No se pudo generar el reporte.",
                    color = Color.Gray,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center),
                    textAlign = TextAlign.Center
                )
            }
            PdfFloatingActionButton(
                onButtonClick = {
                    if (reportData != null && recomendaciones.isNotEmpty()) {
                        generatePdf(context, reportData!!, recomendaciones)
                    } else {
                        Toast.makeText(context, "No hay datos para generar el PDF", Toast.LENGTH_SHORT).show()
                    }
                }
            )
        }
    }
}


@Composable
fun ReportContent(reportData: FinancialReportData, recomendaciones: List<LoteRecommendation>) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título del reporte
        Text(
            text = "Reporte Financiero de la Finca: ${reportData.finca_nombre}",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF49602D),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Periodo
        Text(
            text = "Periodo: ${reportData.periodo}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lotes incluidos
        Text(
            text = "Lotes Incluidos: ${reportData.lotes_incluidos.joinToString(", ")}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Introducción
        Text(
            text = "Introducción:",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Text(
            text = "Este es el reporte financiero de la finca: ${reportData.finca_nombre} que incluye los lotes: ${reportData.lotes_incluidos.joinToString(", ")}, en el periodo ${reportData.periodo}. A continuación, se presenta un análisis Fde los ingresos y gastos por lote y para la finca en su conjunto.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 1. Comparación de Ingresos y Gastos por Lote
        Text(
            text = "1. Comparación de Ingresos y Gastos por Lote",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Gráfico de Barras usando MPAndroidChart
        MPBarChartComparison(
            plotFinancials = reportData.plot_financials,
            modifier = Modifier
                .fillMaxWidth()
                .height(300.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 2. Distribución de Categorías de Ingresos y Gastos por Lote
        Text(
            text = "2. Distribución de Categorías de Ingresos y Gastos por Lote",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        reportData.plot_financials.forEach { plotFinancial ->
            Text(
                text = "Lote: ${plotFinancial.plot_name}",
                style = MaterialTheme.typography.titleSmall,
                color = Color(0xFF3F3D3D),
                modifier = Modifier.fillMaxWidth()
            )

            // Ingresos por Categoría
            MPPieChart(
                title = "Ingresos por Categoría",
                categories = plotFinancial.ingresos_por_categoria,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Gastos por Categoría
            MPPieChart(
                title = "Gastos por Categoría",
                categories = plotFinancial.gastos_por_categoria,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))
        }

        // 3. Resumen Financiero de la Finca XYZ
        Text(
            text = "3. Resumen Financiero de la Finca: ${reportData.finca_nombre}",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar los totales
        Text(
            text = "Total Ingresos: \$${reportData.farm_summary.total_ingresos}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Total Gastos: \$${reportData.farm_summary.total_gastos}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Balance Financiero: \$${reportData.farm_summary.balance_financiero}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Distribución de Ingresos y Gastos de la Finca
        Text(
            text = "Distribución de Ingresos y Gastos de la Finca",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Distribución de Ingresos de la Finca
        MPPieChart(
            title = "Distribución de Ingresos de la Finca",
            categories = reportData.farm_summary.ingresos_por_categoria,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Distribución de Gastos de la Finca
        MPPieChart(
            title = "Distribución de Gastos de la Finca",
            categories = reportData.farm_summary.gastos_por_categoria,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        // 4. Análisis y Recomendaciones
        Text(
            text = "4. Análisis y Recomendaciones",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Mostrar las recomendaciones
        recomendaciones.forEach { recomendacion ->
            Text(
                text = "Lote: ${recomendacion.loteNombre} - ${recomendacion.rendimiento}",
                style = MaterialTheme.typography.bodyMedium,
                color = Color(0xFF3F3D3D),
                modifier = Modifier.fillMaxWidth()
            )
            recomendacion.recomendaciones.forEach { texto ->
                Text(
                    text = "- $texto",
                    style = MaterialTheme.typography.bodySmall,
                    color = Color(0xFF3F3D3D),
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
        }

        // Aquí puedes agregar los textos de análisis y recomendaciones

        // 5. Conclusiones
        Text(
            text = "5. Conclusiones",
            style = MaterialTheme.typography.titleMedium,
            color = Color(0xFF49602D),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Texto de conclusiones
        Text(
            text = "Este reporte financiero proporciona una visión de la situación económica de la finca ${reportData.finca_nombre} y sus lotes seleccionados en el periodo ${reportData.periodo}. Con base en los análisis realizados, se recomienda seguir las acciones propuestas para mejorar el rendimiento financiero y asegurar la sostenibilidad y crecimiento de la finca.",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
    }
}


@Preview(showBackground = true)
@Composable
fun FinanceReportPreview() {
    val navController = NavController(LocalContext.current)
    CoffeTechTheme {
        FinanceReportView(
            navController = navController,
            plotIds = listOf(1, 2, 3),
            startDate = "2024-01-01",
            endDate = "2024-03-31"
        )
    }
}
