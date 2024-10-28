package com.example.coffetech.view.CulturalWorkTask


import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes

import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.CulturalWorkTask.CulturalWorkTaskGeneralViewModel
import com.example.coffetech.viewmodel.cultural.CulturalWorkTask

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturalWorkTaskGeneralView(
    navController: NavController,
    farmId: Int,
    farmName: String,
    plotId: Int,
    plotName: String,
    viewModel: CulturalWorkTaskGeneralViewModel = viewModel()
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
    val expandedState by viewModel.isEstadoDropdownExpanded.collectAsState()
    val expandedAssigned by viewModel.isAssignedDropdownExpanded.collectAsState()
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
               /* if (isLoading) {
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
                } else {*/

                    // Barra de búsqueda conectada al ViewModel
                    ReusableSearchBar(
                        query = searchQuery,
                        onQueryChanged = { },
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
                            onOptionSelected = {},
                            options = estados,
                            expanded = expandedState,
                            onExpandedChange = {},
                            label = "Finca",
                            expandedArrowDropUp = expandedArrowDropUp,
                            arrowDropDown = arrowDropDown
                        )

                        // Dropdown para filtrar por asignación
                        GenericDropdown(
                            modifier = Modifier.weight(1f),
                            selectedOption = assignedToFilter,
                            onOptionSelected = {},
                            options = assignedToOptions,
                            expanded = expandedAssigned,
                            onExpandedChange = {},
                            label = "Lote",
                            expandedArrowDropUp = expandedArrowDropUp,
                            arrowDropDown = arrowDropDown
                        )

                        GenericDropdown(
                            modifier = Modifier.weight(1f),
                            selectedOption = stateFilter,
                            onOptionSelected = {},
                            options = estados,
                            expanded = expandedState,
                            onExpandedChange = {},
                            label = "Fecha",
                            expandedArrowDropUp = expandedArrowDropUp,
                            arrowDropDown = arrowDropDown
                        )
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de tareas culturales
                    if (tasks.isEmpty()) {
                        Text("No hay tareas de labor cultural para mostrar", color = Color.Gray)
                    } else {
                        LazyColumn { // Reemplaza el forEach con LazyColumn para mejor rendimiento
                            items(tasks) { task ->
                                CulturalWorkTaskGeneralCard(
                                    task = task,
                                    farmName = "Nombre de la Finca", // Reemplaza con el nombre de la finca adecuado
                                    plotName = "Nombre del Lote" // Reemplaza con el nombre del lote adecuado
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                //}
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun CulturalWorkTaskCard2Preview() {
    CoffeTechTheme {
        CulturalWorkTaskGeneralCard(
            task = CulturalWorkTask(
                id = 1,
                name = "Recolección de Café",
                assignedTo = "me",
                assignedToName = "Juan Pérez",
                state = "Por hacer",
                date = 1672531199000L
            ),
            farmName = "Finca 1",
            plotName = "Lote 1"
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


        // Llamada a la vista de previsualización con el ViewModel predefinido
        CulturalWorkTaskGeneralView(
            navController = navController,
            farmId = 1, // Valores simulados
            farmName = "Finca Ejemplo",
            plotId = 1, // Valores simulados
            plotName = "Plot A"
        )
    }
}
