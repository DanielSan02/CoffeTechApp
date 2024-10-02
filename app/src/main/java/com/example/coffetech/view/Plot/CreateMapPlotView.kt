package com.example.coffetech.view.Plot

import android.Manifest
import android.app.Activity
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.BackButton
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableTextButton
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.example.coffetech.viewmodel.Plot.PlotViewModel
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



@Composable
fun CreateMapPlotView(
    navController: NavController,
    farmId: Int,
    plotName: String,
    selectedVariety: String,
    viewModel: PlotViewModel = viewModel()
) {
    val context = LocalContext.current
    val location by viewModel.location.collectAsState()
    val locationPermissionGranted by viewModel.locationPermissionGranted.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()
    val currentLocation = location
    val latitude by viewModel.latitude.collectAsState()
    val longitude by viewModel.longitude.collectAsState()
    val altitude by viewModel.altitude.collectAsState()

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
                    navController.popBackStack() // Regresa a FarmInformationView
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

    // Interceptar el botón de back para navegar con los datos
    BackHandler {
        navController.navigate(
            "createPlotInformationView/$farmId?plotName=${Uri.encode(plotName)}&selectedVariety=${Uri.encode(selectedVariety)}"
        )
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

                                if (currentLocation != null) {
                                    GoogleMapView(
                                        location = currentLocation,
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
                                            text = "Latitud: ${latitude.take(10)}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = "Longitud: ${longitude.take(10)}",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                        Spacer(modifier = Modifier.height(8.dp))
                                        Text(
                                            text = if (altitude != null) "Altitud: $altitude metros" else "Obteniendo altitud...",
                                            fontWeight = FontWeight.Bold,
                                            fontSize = 16.sp,
                                            color = Color.Black
                                        )
                                    }

                                } else {
                                    CircularProgressIndicator(
                                        modifier = Modifier.align(Alignment.CenterHorizontally)
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
                                        viewModel.onCreatePlot(
                                            navController = navController,
                                            context = context,
                                            farmId = farmId,
                                            plotName = plotName,
                                            coffeeVarietyName = selectedVariety
                                        )
                                    },
                                    modifier = Modifier
                                        .size(width = 160.dp, height = 48.dp)
                                        .align(Alignment.CenterHorizontally),
                                    buttonType = ButtonType.Green,
                                    enabled = true
                                )


                                ReusableTextButton(
                                    navController = navController,
                                    destination = "createPlotInformationView/$farmId?plotName=${Uri.encode(plotName)}&selectedVariety=${Uri.encode(selectedVariety)}",
                                    text = "Volver",
                                    modifier = Modifier
                                        .size(width = 160.dp, height = 48.dp)
                                        .align(Alignment.CenterHorizontally)
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
        CreateMapPlotView(
            navController = NavController(LocalContext.current),
            farmId = 1,
            plotName= "",
            selectedVariety= ""
        )
    }
}
