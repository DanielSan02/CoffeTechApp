package com.example.coffetech.view.PlotMap

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.UnitDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.PlotMap.PlotViewModel
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

@Composable
fun GoogleMapView(
    location: LatLng, // Latitud y longitud inicial
    onLocationSelected: (LatLng) -> Unit // Callback para manejar el cambio de ubicación
) {
    // Estado de la posición de la cámara del mapa
    val cameraPositionState = rememberCameraPositionState {
        position = com.google.android.gms.maps.model.CameraPosition.fromLatLngZoom(location, 15f)
    }

    GoogleMap(
        modifier = Modifier
            .fillMaxWidth()
            .height(300.dp),
        cameraPositionState = cameraPositionState,
        onMapClick = { latLng: LatLng ->
            onLocationSelected(latLng) // Actualizar la ubicación seleccionada
        }
    ) {
        Marker(
            state = MarkerState(position = location)
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddLocationPlot(
    navController: NavController,
    farmId: Int,
    viewModel: PlotViewModel = viewModel()
) {
    val context = LocalContext.current
    val location by viewModel.location.collectAsState()
    val locationPermissionGranted by viewModel.locationPermissionGranted.collectAsState()
    val plotRadiusValue = viewModel.plotRadius.collectAsState().value.toDoubleOrNull()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val currentLocation = location

    // Obtener los estados del ViewModel
    val plotRadius by viewModel.plotRadius.collectAsState()
    val selectedUnit by viewModel.selectedUnit.collectAsState()
    val areaUnits by viewModel.areaUnits.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()

    // Estado para saber si los permisos fueron denegados permanentemente
    var isPermissionDeniedPermanently by remember { mutableStateOf(false) }

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

    // Crear el cliente de ubicación
    val fusedLocationClient = remember {
        LocationServices.getFusedLocationProviderClient(context)
    }

    // Lanzar permisos cada vez que el usuario pulsa el botón, sin importar si ya los denegó
    LaunchedEffect(Unit) {
        if (!viewModel.checkLocationPermission(context)) {
            permissionLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    // Obtener la ubicación actual cuando los permisos estén concedidos
    LaunchedEffect(locationPermissionGranted) {
        if (locationPermissionGranted) {
            try {
                val lastLocation = fusedLocationClient.lastLocation.await()
                if (lastLocation != null) {
                    val latLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                    viewModel.onLocationChange(latLng)
                } else {
                    val cancellationTokenSource = CancellationTokenSource()
                    val currentLocation = fusedLocationClient.getCurrentLocation(
                        Priority.PRIORITY_HIGH_ACCURACY,
                        cancellationTokenSource.token
                    ).await()
                    if (currentLocation != null) {
                        val latLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                        viewModel.onLocationChange(latLng)
                    } else {
                        viewModel.setErrorMessage("No se pudo obtener la ubicación actual.")
                    }
                }
            } catch (e: SecurityException) {
                viewModel.setErrorMessage("Error de permisos de ubicación.")
            } catch (e: Exception) {
                viewModel.setErrorMessage("Error al obtener la ubicación: ${e.message}")
            }
        }
    }

    // Estructura de la UI usando Scaffold para manejar el Snackbar
    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                if (locationPermissionGranted) {
                    // Mostrar la vista principal solo si los permisos están concedidos
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
                                    text = "Crear Lote",
                                    textAlign = TextAlign.Center,
                                    fontWeight = FontWeight.W600,
                                    fontSize = 25.sp,
                                    color = Color(0xFF49602D),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Título para ubicación
                                Text(
                                    text = "Ubicación",
                                    textAlign = TextAlign.Start,
                                    fontWeight = FontWeight.W500,
                                    fontSize = 20.sp,
                                    color = Color(0xFF49602D),
                                    modifier = Modifier.fillMaxWidth()
                                )

                                Spacer(modifier = Modifier.height(16.dp))

                                // Mostrar el mapa si los permisos están concedidos
                                if (currentLocation != null) {
                                    GoogleMapView(
                                        location = currentLocation,
                                        onLocationSelected = { latLng ->
                                            viewModel.onLocationChange(latLng)
                                        }
                                    )
                                } else {
                                    // Mostrar un indicador de carga o mensaje
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Mostrar mensaje de error si lo hay
                                if (viewModel.errorMessage.collectAsState().value.isNotEmpty()) {
                                    Text(
                                        text = viewModel.errorMessage.collectAsState().value,
                                        color = Color.Red,
                                        modifier = Modifier.padding(top = 8.dp)
                                    )
                                }

                                Spacer(modifier = Modifier.height(16.dp))

                                // Botón para guardar
                                ReusableButton(
                                    text = if (viewModel.isLoading.collectAsState().value) "Guardando..." else "Guardar",
                                    onClick = {
                                        viewModel.onSubmit()
                                        viewModel.clearErrorMessage()

                                        if (plotRadiusValue == null || plotRadiusValue <= 0 || plotRadiusValue > 10000) {
                                            viewModel.setErrorMessage("El radio debe ser un número mayor a 0 y menor a 10000.")
                                        } else if (selectedUnit.isEmpty()) {
                                            viewModel.setErrorMessage("Seleccione unidad de medida.")
                                        } else {
                                            viewModel.savePlotData()
                                        }
                                    },
                                    modifier = Modifier
                                        .size(width = 160.dp, height = 48.dp)
                                        .align(Alignment.CenterHorizontally),
                                    buttonType = ButtonType.Green,
                                    enabled = true
                                )
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
                                // Redirigir a la configuración de la aplicación
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

                        // Botón de "Volver" para regresar al FarmInformationView luego de cambiar los permisos en la configuración de la aplicación
                        ReusableTextButton(
                            navController = navController,
                            text = "Volver",
                            modifier = Modifier.align(Alignment.CenterHorizontally),
                            destination = "FarmInformationView/$farmId"
                        )
                    }
                }
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun AddLocationPlotPreview() {
    CoffeTechTheme {
        AddLocationPlot(
            navController = NavController(LocalContext.current),
            farmId = 1
        )
    }
}
