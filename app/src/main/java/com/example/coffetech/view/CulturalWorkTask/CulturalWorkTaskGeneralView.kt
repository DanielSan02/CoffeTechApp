package com.example.coffetech.view.CulturalWorkTask


import android.content.Context
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
import com.example.coffetech.model.CulturalWorkTask
import com.example.coffetech.model.GeneralCulturalWorkTask
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.view.common.HeaderFooterView
import com.example.coffetech.viewmodel.CulturalWorkTask.GeneralCulturalWorkTaskViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CulturalWorkTaskGeneralView(
    navController: NavController,
    viewModel: GeneralCulturalWorkTaskViewModel = viewModel()
) {
    val context = LocalContext.current

    // Cargar las tareas cuando el Composable se monta
    LaunchedEffect(Unit) {
        viewModel.loadTasks(context)
    }

    // Obtener los estados del ViewModel
    val tasks by viewModel.filteredTasks.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    // Estados de los filtros
    val farmOptions by viewModel.farmOptions.collectAsState()
    val selectedFarm by viewModel.selectedFarm.collectAsState()
    val plotOptions by viewModel.plotOptions.collectAsState()
    val selectedPlot by viewModel.selectedPlot.collectAsState()
    val selectedOrder by viewModel.selectedOrder.collectAsState()

    HeaderFooterView(
        title = "Mis Tareas",
        currentView = "Labores",
        navController = navController
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
            ) {
                if (isLoading) {
                    // Indicador de carga
                    Column(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        CircularProgressIndicator()
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = "Cargando tareas de labor cultural...",
                            color = Color.Black
                        )
                    }
                } else if (errorMessage != null) {
                    // Mostrar mensaje de error
                    Text(
                        text = errorMessage ?: "",
                        color = Color.Red,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {

                    // Barra de búsqueda
                    ReusableSearchBar(
                        query = searchQuery,
                        onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                        text = "Buscar tarea por 'Asignado a'",
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Dropdowns para filtrar y ordenar
                    CulturalTaskFilterDropdowns(
                        farmOptions = farmOptions,
                        selectedFarm = selectedFarm,
                        onFarmSelected = { viewModel.selectFarm(it) },
                        plotOptions = plotOptions,
                        selectedPlot = selectedPlot,
                        onPlotSelected = { viewModel.selectPlot(it) },
                        selectedOrder = selectedOrder,
                        onOrderSelected = { viewModel.selectOrder(it) },
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Lista de tareas culturales
                    if (tasks.isEmpty()) {
                        Text(
                            text = "No hay tareas de labor cultural para mostrar",
                            color = Color.Gray,
                            modifier = Modifier.padding(vertical = 16.dp)
                        )
                    } else {
                        LazyColumn {
                            items(tasks) { task ->
                                CulturalWorkTaskGeneralCard(
                                    task = task,
                                    farmName = task.farm_name,
                                    plotName = task.plot_name
                                )
                                Spacer(modifier = Modifier.height(8.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun CulturalWorkTaskGeneralViewPreview() {
    CoffeTechTheme {
        val navController = rememberNavController()
        val viewModel: GeneralCulturalWorkTaskViewModel = viewModel()

        // Simulación de tareas predefinidas para la vista previa
        LaunchedEffect(Unit) {
            viewModel.addTestTasks(
                listOf(
                    GeneralCulturalWorkTask(
                        cultural_work_task_id = 1,
                        cultural_works_name = "Recolección de Café",
                        collaborator_id = 1,
                        collaborator_name = "Daniel Pruebas",
                        owner_name = "Natalia Rodríguez Mu",
                        status = "Por hacer",
                        task_date = "2024-10-29",
                        farm_name = "Finca 1",
                        plot_name = "Lote 1"
                    ),
                    GeneralCulturalWorkTask(
                        cultural_work_task_id = 2,
                        cultural_works_name = "Poda de Árboles",
                        collaborator_id = 2,
                        collaborator_name = "Otros Colaboradores",
                        owner_name = "María García",
                        status = "Terminado",
                        task_date = "2024-10-27",
                        farm_name = "Finca 2",
                        plot_name = "Lote 2"
                    )
                )
            )
        }

        CulturalWorkTaskGeneralView(
            navController = navController,
            viewModel = viewModel
        )
    }
}