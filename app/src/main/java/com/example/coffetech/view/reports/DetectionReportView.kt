// DetectionReportView.kt
package com.example.coffetech.view

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.TopBarWithBackArrow
import com.example.coffetech.model.Detection
import com.example.coffetech.model.DetectionHistory
import com.example.coffetech.view.components.CsvFloatingActionButton
import com.example.coffetech.view.components.PdfFloatingActionButton
import com.example.coffetech.viewmodel.reports.DetectionReportViewModel
import com.example.coffetech.view.components.generateCsvDetection
import com.example.coffetech.view.components.generatePdfDetection


@Composable
fun DetectionReportView(
    modifier: Modifier = Modifier,
    navController: NavController,
    plotIds: List<Int>,
    startDate: String,
    endDate: String,
    viewModel: DetectionReportViewModel = viewModel()
) {
    val TAG = "DetectionReportView"

    Log.d(TAG, "DetectionReportView Composable iniciado")

    val context = LocalContext.current
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val detectionData by viewModel.detectionData.collectAsState()

    // Estados para manejar permisos
    var showPdfPermissionRationale by remember { mutableStateOf(false) }
    var showCsvPermissionRationale by remember { mutableStateOf(false) }

    // Launchers para solicitar permisos
    val pdfPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Permiso de almacenamiento concedido para PDF")
            if (detectionData != null) {
                generatePdfDetection(context, detectionData!!, "Historial_Detecciones.pdf")
            } else {
                Toast.makeText(context, "No hay datos para generar el PDF", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e(TAG, "Permiso de almacenamiento denegado para PDF")
            showPdfPermissionRationale = true
        }
    }

    val csvPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted ->
        if (isGranted) {
            Log.d(TAG, "Permiso de almacenamiento concedido para CSV")
            if (detectionData != null) {
                generateCsvDetection(context, detectionData!!, "Historial_Detecciones.csv")
            } else {
                Toast.makeText(context, "No hay datos para generar el CSV", Toast.LENGTH_SHORT).show()
            }
        } else {
            Log.e(TAG, "Permiso de almacenamiento denegado para CSV")
            showCsvPermissionRationale = true
        }
    }

    // Función para iniciar la generación del PDF
    fun initiatePdfGeneration() {
        Log.d(TAG, "Iniciando generación de PDF")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // Verificar si el permiso ya está concedido
            val hasPermission = context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED

            Log.d(TAG, "Permiso WRITE_EXTERNAL_STORAGE concedido: $hasPermission")

            if (hasPermission) {
                if (detectionData != null) {
                    generatePdfDetection(context, detectionData!!, "Historial_Detecciones.pdf")
                } else {
                    Toast.makeText(context, "No hay datos para generar el PDF", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Solicitar el permiso
                Log.d(TAG, "Solicitando permiso WRITE_EXTERNAL_STORAGE para PDF")
                pdfPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else {
            // Para Android 10 y superiores, no se necesita permiso para usar MediaStore
            Log.d(TAG, "Android versión >= Q, no se requiere permiso WRITE_EXTERNAL_STORAGE para PDF")
            if (detectionData != null) {
                generatePdfDetection(context, detectionData!!, "Historial_Detecciones.pdf")
            } else {
                Toast.makeText(context, "No hay datos para generar el PDF", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Función para iniciar la generación del CSV
    fun initiateCsvGeneration() {
        Log.d(TAG, "Iniciando generación de CSV")
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            // Verificar si el permiso ya está concedido
            val hasPermission = context.checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == android.content.pm.PackageManager.PERMISSION_GRANTED

            Log.d(TAG, "Permiso WRITE_EXTERNAL_STORAGE concedido: $hasPermission")

            if (hasPermission) {
                if (detectionData != null) {
                    generateCsvDetection(context, detectionData!!, "Historial_Detecciones.csv")
                } else {
                    Toast.makeText(context, "No hay datos para generar el CSV", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Solicitar el permiso
                Log.d(TAG, "Solicitando permiso WRITE_EXTERNAL_STORAGE para CSV")
                csvPermissionLauncher.launch(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        } else {
            // Para Android 10 y superiores, no se necesita permiso para usar MediaStore
            Log.d(TAG, "Android versión >= Q, no se requiere permiso WRITE_EXTERNAL_STORAGE para CSV")
            if (detectionData != null) {
                generateCsvDetection(context, detectionData!!, "Historial_Detecciones.csv")
            } else {
                Toast.makeText(context, "No hay datos para generar el CSV", Toast.LENGTH_SHORT).show()
            }
        }
    }

    LaunchedEffect(Unit) {
        Log.d(TAG, "Llamando a getDetectionHistory")
        viewModel.getDetectionHistory(
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
            onBackClick = {
                Log.d(TAG, "Botón de retroceso clickeado")
                navController.popBackStack()
                navController.popBackStack()

            },
            title = "Historial de Detecciones"
        )

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
        ) {
            when {
                isLoading -> {
                    Log.d(TAG, "Mostrando CircularProgressIndicator")
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }
                errorMessage != null -> {
                    Log.e(TAG, "Mostrando mensaje de error: $errorMessage")
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }

                detectionData != null -> {
                    Log.d(TAG, "Mostrando ReportContent")
                    ReportContent(
                        detections = detectionData!!,
                        onChartsCaptured = {}
                    )
                }
                else -> {
                    Log.e(TAG, "No se pudo generar el reporte.")
                    Text(
                        text = "No se pudo generar el reporte.",
                        color = Color.Gray,
                        style = MaterialTheme.typography.bodyMedium,
                        modifier = Modifier.align(Alignment.Center),
                        textAlign = TextAlign.Center
                    )
                }
            }

            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp),
                horizontalAlignment = Alignment.End
            ) {
                CsvFloatingActionButton(
                    onButtonClick = {
                        initiateCsvGeneration()
                    }
                )

                PdfFloatingActionButton(
                    onButtonClick = {
                        initiatePdfGeneration()
                    }
                )
            }
        }
    }

    // Mostrar diálogos de razón para los permisos
    if (showPdfPermissionRationale) {
        Log.d(TAG, "Mostrando diálogo de razón para permiso de almacenamiento para PDF")
        AlertDialog(
            onDismissRequest = { showPdfPermissionRationale = false },
            title = { Text(text = "Permiso de almacenamiento") },
            text = { Text("Esta aplicación necesita acceso al almacenamiento para guardar el PDF del historial de detecciones.") },
            confirmButton = {
                TextButton(onClick = {
                    Log.d(TAG, "Usuario decidió abrir configuración para habilitar permisos para PDF")
                    showPdfPermissionRationale = false
                    // Abrir la configuración de la aplicación para que el usuario pueda habilitar el permiso manualmente
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Abrir Configuración")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    Log.d(TAG, "Usuario canceló el diálogo de permiso para PDF")
                    showPdfPermissionRationale = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }

    if (showCsvPermissionRationale) {
        Log.d(TAG, "Mostrando diálogo de razón para permiso de almacenamiento para CSV")
        AlertDialog(
            onDismissRequest = { showCsvPermissionRationale = false },
            title = { Text(text = "Permiso de almacenamiento") },
            text = { Text("Esta aplicación necesita acceso al almacenamiento para guardar el CSV del historial de detecciones.") },
            confirmButton = {
                TextButton(onClick = {
                    Log.d(TAG, "Usuario decidió abrir configuración para habilitar permisos para CSV")
                    showCsvPermissionRationale = false
                    // Abrir la configuración de la aplicación para que el usuario pueda habilitar el permiso manualmente
                    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                        data = Uri.fromParts("package", context.packageName, null)
                    }
                    context.startActivity(intent)
                }) {
                    Text("Abrir Configuración")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    Log.d(TAG, "Usuario canceló el diálogo de permiso para CSV")
                    showCsvPermissionRationale = false
                }) {
                    Text("Cancelar")
                }
            }
        )
    }
}
@Composable
fun ReportContent(
    detections: List<DetectionHistory>,
    onChartsCaptured: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState())
    ) {
        // Título del reporte
        Text(
            text = "Historial de Detecciones",
            style = MaterialTheme.typography.titleLarge,
            color = Color(0xFF49602D),
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Lista de detecciones
        detections.forEach { detection ->
            DetectionItem(detection = detection)
            Divider(color = Color.LightGray, thickness = 1.dp)
        }

        Spacer(modifier = Modifier.height(50.dp))
    }
}


@Composable
fun DetectionItem(detection: DetectionHistory) {
    Column(modifier = Modifier.padding(vertical = 8.dp)) {
        Text(
            text = "Fecha: ${detection.date}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Persona: ${detection.person_name}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Detección: ${detection.detection}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Recomendación: ${detection.recommendation}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Trabajo Cultural: ${detection.cultural_work}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Lote: ${detection.lote_name}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
        Text(
            text = "Finca: ${detection.farm_name}",
            style = MaterialTheme.typography.bodyMedium,
            color = Color(0xFF3F3D3D)
        )
    }
}
