package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.*
import com.example.coffetech.model.Plot
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.farm.FarmInformationViewModel


@Composable
fun FarmInformationView(
    navController: NavController,
    farmId: Int,
    viewModel: FarmInformationViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    // Obtener el contexto para acceder a SharedPreferences o cualquier otra fuente del sessionToken
    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }

    // Llamar a loadFarmData y loadPlots cuando la vista se cargue
    LaunchedEffect(farmId) {
        sessionToken?.let {
            viewModel.loadFarmData(farmId, it, context)
            viewModel.loadPlots(farmId, it) // Llama a la función para cargar los lotes
        } ?: run {
            viewModel.setErrorMessage("Session token no encontrado. Por favor, inicia sesión.")
        }
    }

    // Obtener los estados del ViewModel
    val farmName by viewModel.farmName.collectAsState()
    val farmArea by viewModel.farmArea.collectAsState()
    val unitOfMeasure by viewModel.unitOfMeasure.collectAsState()
    val selectedRole by viewModel.selectedRole.collectAsState()
    val collaboratorName by viewModel.collaboratorName.collectAsState()
    val lotes by viewModel.lotes.collectAsState() // Aquí están los lotes cargados
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()

    // Verificar permisos del usuario
    val userHasPermissionToEdit = viewModel.hasPermission("edit_farm")
    val userHasPermissionToDelete = viewModel.hasPermission("delete_farm")
    val userHasPermissionCollaborators = viewModel.hasPermission("add_operador_farm") || viewModel.hasPermission("agregar_operador_farm")

    val displayedFarmName = if (farmName.length > 21) {
        farmName.take(17) + "..." // Si tiene más de 21 caracteres, corta y añade "..."
    } else {
        farmName // Si es menor o igual a 21 caracteres, lo dejamos como está
    }

    // Vista principal
    HeaderFooterSubView(
        title = "Mi Finca",
        currentView = "Fincas",
        navController = navController
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
        ) {
            // Contenido desplazable
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState()) // Hacer la columna scrolleable verticalmente
            ) {
                if (isLoading) {
                    // Mostrar un indicador de carga
                    CircularProgressIndicator()
                    Text(
                        text = "Cargando datos de la finca...",
                        color = Color.Black,
                    )
                } else if (errorMessage.isNotEmpty()) {
                    // Mostrar el error si ocurrió algún problema
                    Text(text = errorMessage, color = Color.Red)
                } else {

                    // Mostrar el rol seleccionado
                    Text(text = "Rol: ${selectedRole ?: "Sin rol"}", color = Color.Black)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Componente reutilizable de Información General
                    GeneralInfoCard(
                        farmName = displayedFarmName,
                        farmArea = farmArea,
                        farmUnitMeasure = unitOfMeasure,
                        onEditClick = { viewModel.onEditFarm(navController, farmId, farmName, farmArea, unitOfMeasure) },
                        showEditButton = userHasPermissionToEdit // Solo muestra el botón si tiene el permiso
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(159.dp), // Altura de 159dp como en el diseño
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        ActionCard(
                            buttonText = "Colaboradores", // Texto para el primer botón
                            onClick = {
                                navController.navigate("CollaboratorView/$farmId/$farmName")
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(end = 7.5.dp) // Mitad del padding para separar los botones
                        )
                        ActionCard(
                            buttonText = "Reportes", // Texto para el segundo botón
                            onClick = {
                                // Acción para el botón de reportes
                            },
                            modifier = Modifier
                                .weight(1f)
                                .padding(start = 7.5.dp) // Mitad del padding para separar los botones
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de Lotes usando el LotesList personalizado
                    LotesList(
                        lotes = lotes, // Pasamos directamente la lista de objetos Plot
                        modifier = Modifier.fillMaxWidth(),
                        onLoteClick = { lote ->
                            navController.navigate(
                                "PlotInformationView/${lote.plot_id}/${lote.name}/${lote.coffee_variety_name}/${lote.latitude}/${lote.longitude}/${lote.altitude}/$farmName"
                            )
                        }
                    )



                    Spacer(modifier = Modifier.height(16.dp))
                }
            }

            // Botón flotante alineado al fondo derecho
            CustomFloatingActionButton(
                onAddLoteClick = {
                    // Navegamos a CreatePlotInformationView con el farmId
                    navController.navigate("${Routes.CreatePlotInformationView}/$farmId")
                },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmInformationViewPreview() {
    CoffeTechTheme {
        FarmInformationView(
            navController = NavController(LocalContext.current),
            farmId = 1 // Valor simulado de farmId para la previsualización
        )
    }
}