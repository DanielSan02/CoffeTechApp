// EditMapPlotView.kt
package com.example.coffetech.view.Plot

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextButton
import com.example.coffetech.viewmodel.Plot.EditMapPlotViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.launch

@Composable
fun EditMapPlotView(
    navController: NavController,
    plotId: Int,
    initialLatitude: Double,
    initialLongitude: Double,
    initialAltitude: Double,
    viewModel: EditMapPlotViewModel = viewModel()
) {
    val context = LocalContext.current
    val locationPermissionGranted by viewModel.locationPermissionGranted.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()
    val altitude by viewModel.altitude.collectAsState()
    val isAltitudeLoading by viewModel.isAltitudeLoading.collectAsState()

    // Estado para saber si los permisos fueron denegados permanentemente
    var isPermissionDeniedPermanently by remember { mutableStateOf(false) }

    val updateSuccess by viewModel.updateSuccess.collectAsState()

    // Manejar la navegación después de una actualización exitosa
    if (updateSuccess) {
        LaunchedEffect(updateSuccess) {
            navController.popBackStack()
        }
    }

    // Crear el lanzador de permisos
    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { isGranted: Boolean ->
        if (isGranted) {
            viewModel.updateLocationPermissionStatus(isGranted)
        } else {
            val activity = context as? Activity
            if (activity != null) {
                isPermissionDeniedPermanently = !ActivityCompat.shouldShowRequestPermissionRationale(
                    activity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            }

            coroutineScope.launch {
                if (isPermissionDeniedPermanently) {
                    snackbarHostState.showSnackbar("Se necesitan permisos de localización")
                } else {
                    snackbarHostState.showSnackbar("Se necesitan permisos de localización")
                    navController.popBackStack()
                }
            }
        }
    }

    // Lanzar permisos y establecer ubicación inicial
    LaunchedEffect(Unit) {
        if (!viewModel.checkLocationPermission(context)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        } else {
            viewModel.updateLocationPermissionStatus(true)
        }

        // Establecer la ubicación inicial en el ViewModel
        viewModel.setInitialLocation(initialLatitude, initialLongitude, initialAltitude)
    }

    // Interceptar el botón de back para navegar con los datos
    BackHandler {
        navController.popBackStack()
    }

    // Estructura de la UI usando Scaffold para manejar el Snackbar
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                if (locationPermissionGranted) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color(0xFF101010))
                            .padding(10.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.95f)
                                .background(Color.White, RoundedCornerShape(16.dp))
                                .padding(horizontal = 20.dp, vertical = 16.dp)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // Botón de cerrar o volver (BackButton)
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.End
                                ) {
                                    BackButton(
                                        navController = navController,
                                        modifier = Modifier.size(32.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Título de la pantalla
                                Text(
                                    text = "Editar Ubicación",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 30.sp,
                                    color = Color(0xFF49602D),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(30.dp))

                                // Título para ubicación
                                Text(
                                    text = "Ubicación",
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 22.sp,
                                    color = Color(0xFF49602D),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Mapa
                                val location = LatLng(latitude, longitude)
                                GoogleMapView(
                                    location = location,
                                    onLocationSelected = { latLng ->
                                        viewModel.onLocationChange(latLng)
                                    }
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Mostrar latitud, longitud y altitud debajo del mapa
                                Column(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalAlignment = Alignment.CenterHorizontally
                                ) {
                                    Text(
                                        text = "Latitud: ${String.format("%.6f", latitude)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = "Longitud: ${String.format("%.6f", longitude)}",
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    Text(
                                        text = when {
                                            isAltitudeLoading -> "Obteniendo altitud..."
                                            altitude != null -> "Altitud: $altitude metros"
                                            else -> "No se pudo obtener la altitud"
                                        },
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        color = Color.Black
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                if (viewModel.errorMessage.collectAsState().value.isNotEmpty()) {
                                    Text(
                                        text = viewModel.errorMessage.collectAsState().value,
                                        color = Color.Red,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                ReusableButton(
                                    text = if (viewModel.isLoading.collectAsState().value) "Guardando..." else "Guardar",
                                    onClick = {
                                        viewModel.onUpdatePlotLocation(context, plotId)
                                    },
                                    modifier = Modifier
                                        .size(width = 160.dp, height = 48.dp)
                                        .align(Alignment.CenterHorizontally),
                                    buttonType = ButtonType.Green,
                                    enabled = altitude != null && !isAltitudeLoading && !viewModel.isLoading.collectAsState().value // Deshabilitar si la altitud no está lista
                                )
                                if (viewModel.errorMessage.collectAsState().value.isNotEmpty()) {
                                    Text(
                                        text = "Intente agregar el lote luego: ${viewModel.errorMessage.collectAsState().value}",
                                        color = Color.Red,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }


                            }
                        }
                    }
                } else if (isPermissionDeniedPermanently) {
                    // Mostrar el botón para abrir la configuración si los permisos están denegados permanentemente
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Los permisos de localización fueron denegados. Habilítalos manualmente desde la configuración.",
                            color = Color.Red,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                        Button(
                            onClick = {
                                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                                    data = Uri.fromParts("package", context.packageName, null)
                                }
                                context.startActivity(intent)
                            },
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Abrir Configuración")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        ReusableTextButton(
                            navController = navController,
                            text = "Volver",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            destination = ""
                        )
                    }
                }
            }
        }
    )
}
