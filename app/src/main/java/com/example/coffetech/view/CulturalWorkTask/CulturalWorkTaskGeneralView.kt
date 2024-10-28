package com.example.coffetech.view.CulturalWorkTask


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items // Importación correcta
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes

import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.cultural.CulturalWorkTask
import com.example.coffetech.viewmodel.cultural.CulturalWorkTaskViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturalWorkTaskGeneralView(
    navController: NavController,
    farmId: Int,
    farmName: String,
    plotId: Int,
    plotName: String,
    viewModel: CulturalWorkTaskViewModel = viewModel()
) {
    val context = LocalContext.current

    // Cargar las tareas cuando el composable se monta
    LaunchedEffect(farmName, plotName) {
        viewModel.loadTasks(farmName, plotName)
    }

    // Estados del ViewModel
    val tasks by viewModel.filteredTasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val stateFilter by viewModel.stateFilter.collectAsState()
    val assignedToFilter by viewModel.assignedToFilter.collectAsState()
    val dateOrder by viewModel.dateOrder.collectAsState()
    val expandedState by viewModel.isEstadoDropdownExpanded.collectAsState()
    val expandedAssigned by viewModel.isAssignedDropdownExpanded.collectAsState()
    val expandedDateOrder by viewModel.isDateOrderDropdownExpanded.collectAsState()
    val estados by viewModel.estadoOptions.collectAsState()
    val assignedToOptions by viewModel.assignedToOptions.collectAsState()
    val dateOrderOptions by viewModel.dateOrderOptions.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState() // Obtener la consulta de búsqueda

    // Iconos para los dropdowns
    val expandedArrowDropUp: Painter = painterResource(id = R.drawable.arrowdropup_icon)
    val arrowDropDown: Painter = painterResource(id = R.drawable.arrowdropdown_icon)

    HeaderFooterSubView(
        title = "Tarea labor cultural",
        currentView = "Labores",
        navController = navController,
        onBackClick = { navController.navigate("${Routes.PlotInformationView}/$plotId$farmName$farmId") },
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)
                    .verticalScroll(rememberScrollState())
            ) {
                if (isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cargando tareas de labor cultural...",
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red)
                } else {
                    // Barra de búsqueda conectada al ViewModel
                    ReusableSearchBar(
                        query = searchQuery,
                        onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                        text = "Buscar Tarea por nombre",
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(16.dp))


                    Row(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        // Dropdown para filtrar por estado
                        GenericDropdown(
                            modifier = Modifier.weight(1f),
                            selectedOption = stateFilter,
                            onOptionSelected = { nuevoEstado ->
                                viewModel.setStateFilter(nuevoEstado)
                            },
                            options = estados,
                            expanded = expandedState,
                            onExpandedChange = { isExpanded ->
                                viewModel.setEstadoDropdownExpanded(isExpanded)
                            },
                            label = "Estado",
                            expandedArrowDropUp = expandedArrowDropUp,
                            arrowDropDown = arrowDropDown
                        )

                        // Dropdown para filtrar por asignación
                        GenericDropdown(
                            modifier = Modifier.weight(1f),
                            selectedOption = assignedToFilter,
                            onOptionSelected = { nuevoAsignado ->
                                viewModel.setAssignedToFilter(nuevoAsignado)
                            },
                            options = assignedToOptions,
                            expanded = expandedAssigned,
                            onExpandedChange = { isExpanded ->
                                viewModel.setAssignedDropdownExpanded(isExpanded)
                            },
                            label = "Asignado",
                            expandedArrowDropUp = expandedArrowDropUp,
                            arrowDropDown = arrowDropDown
                        )
                    }

                    Text(text = "Finca: $farmName", color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(text = "Lote: $plotName", color = Color.Black)

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de tareas culturales
                    if (tasks.isEmpty()) {
                        Text("No hay tareas de labor cultural para mostrar", color = Color.Gray)
                    } else {
                        LazyColumn { // Reemplaza el forEach con LazyColumn para mejor rendimiento
                            items(tasks) { task ->
                                CulturalWorkTaskGeneralCard(task = task, onClick = {
                                    Toast.makeText(
                                        context,
                                        "Clicked on ${task.name}",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }

            // Botón flotante para agregar una nueva tarea cultural
            FloatingActionButtonGroup(
                onMainButtonClick = { navController.navigate("CreateFarmView") },
                mainButtonIcon = painterResource(id = R.drawable.plus_icon),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CulturalWorkTaskCard2Preview() {
    CoffeTechTheme {
        CulturalWorkTaskCard(
            task = CulturalWorkTask(
                id = 1,
                name = "Recolección de Café",
                assignedTo = "me",
                assignedToName = "Juan Pérez",
                state = "Por hacer",
                date = 1672531199000L
            ),
            onClick = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
fun CulturalWorkTaskGeneralViewPreview() {
    CoffeTechTheme {
        // Simulación de un NavController para previsualización
        val navController = rememberNavController()

        // Lista de tareas predefinidas para la vista previa
        val preloadedTasks = listOf(
            CulturalWorkTask(
                id = 1,
                name = "Recolección de Café",
                assignedTo = "me",
                assignedToName = "Juan Pérez",
                state = "Por hacer",
                date = 1672531199000L
            ),
            CulturalWorkTask(
                id = 2,
                name = "Poda de Árboles",
                assignedTo = "otros colaboradores",
                assignedToName = "María García",
                state = "Terminado",
                date = 1672617599000L
            ),
            CulturalWorkTask(
                id = 3,
                name = "Aplicación de Fertilizantes",
                assignedTo = "me",
                assignedToName = "Juan Pérez",
                state = "Por hacer",
                date = 1672703999000L
            )
        )

        // Crear una instancia del ViewModel con tareas predefinidas
        val previewViewModel = CulturalWorkTaskViewModel(initialTasks = preloadedTasks)

        // Llamada a la vista de previsualización con el ViewModel predefinido
        CulturalWorkTaskGeneralView(
            navController = navController,
            farmId = 1, // Valores simulados
            farmName = "Finca Ejemplo",
            plotId = 1, // Valores simulados
            plotName = "Plot A",
            viewModel = previewViewModel
        )
    }
}
