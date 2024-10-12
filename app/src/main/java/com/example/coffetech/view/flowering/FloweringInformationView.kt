package com.example.coffetech.view.flowering

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.*
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.farm.FarmInformationViewModel
import com.example.coffetech.viewmodel.flowering.FloweringInformationViewModel
import kotlinx.coroutines.flow.map

@Composable
fun FloweringInformationView(
    navController: NavController,
    plotId: Int,
    viewModel: FloweringInformationViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    // Obtener el contexto para acceder a SharedPreferences o cualquier otra fuente del sessionToken
    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }

    /*/ Llamar a loadFarmData y loadPlots cuando la vista se cargue
    LaunchedEffect(farmId) {
        sessionToken?.let {
            viewModel.loadFarmData(farmId, it, context)
            // Verificar permiso antes de cargar lotes
            if (viewModel.hasPermission("read_plots")) {
                viewModel.loadPlots(farmId, it)
            }
        } ?: run {
            viewModel.setErrorMessage("Session token no encontrado. Por favor, inicia sesión.")
        }
    }*/

    // Obtener los estados del ViewModel
    val selectedFloweringName by viewModel.selectedFloweringName
    val expanded by viewModel.isDropdownExpanded
    val plotName by viewModel.plotName.collectAsState()
    val flowering_type_name by viewModel.flowering_type_name.collectAsState()
    val status by viewModel.status.collectAsState()
    val flowering_date by viewModel.flowering_date.collectAsState()
    val flowerings by viewModel.flowerings.collectAsState() // Aquí están los lotes filtrados
    val isLoading by viewModel.isLoading.collectAsState()
    val flowerings_name by viewModel.flowerings_name.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val searchQuery by viewModel.searchQuery


    val displayedPlotName = if (plotName.length > 21) {
        plotName.take(17) + "..." // Si tiene más de 21 caracteres, corta y añade "..."
    } else {
        plotName // Si es menor o igual a 21 caracteres, lo dejamos como está
    }

    // Vista principal
    HeaderFooterSubView(
        title = "Floraciones",
        currentView = "Fincas",
        navController = navController,
        onBackClick = { navController.navigate("${Routes.PlotInformationView}/$plotId")  },
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
                /*if (isLoading) {
                    // Mostrar un indicador de carga
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Cargando datos de la finca...",
                        color = Color.Black,
                        modifier = Modifier.align(Alignment.CenterHorizontally)
                    )
                } else if (errorMessage.isNotEmpty()) {
                    // Mostrar el error si ocurrió algún problema
                    Text(text = errorMessage, color = Color.Red)
                } else {*/

                    // Mostrar el rol seleccionado
                    Text(text = "Nombre de lote: ${displayedPlotName.ifEmpty { "Sin Nombre de lote" }}", color = Color.Black)
                    Spacer(modifier = Modifier.height(16.dp))

                    // Search bar para filtrar

                        ReusableSearchBar(
                            query = searchQuery,
                            onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                            text = "Buscar floracion por nombre",
                            modifier = Modifier.fillMaxWidth()
                        )

                    Spacer(modifier = Modifier.height(16.dp))


                    // Componente reutilizable de Información General
                    FloweringGeneralInfoCard(
                        flowering_type_name = flowering_type_name,
                        status = status,
                        flowering_date = flowering_date,
                        onEditClick = {},
                        onInfoClick = {}
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    FloweringNameDropdown(
                    selectedFloweringName = selectedFloweringName,
                    onFloweringNameChange = { viewModel.selectFloweringName(it) },
                    flowerings = flowerings_name,
                    expanded = expanded,
                    onExpandedChange = { viewModel.setDropdownExpanded(it) },
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon)
                    )

                        // Lista de Lotes usando el LotesList personalizado
                        FloweringList(
                            flowerings = flowerings, // Utilizar la lista filtrada de lotes
                            modifier = Modifier.fillMaxWidth()
                        )

                    Spacer(modifier = Modifier.height(16.dp))
                //}
            }
                // Botón flotante alineado al fondo derecho
                CustomFloatingActionButton(
                    onAddClick = {},
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(16.dp)
                )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FloweringInformationViewPreview() {
    CoffeTechTheme {
        FloweringInformationView(
            navController = NavController(LocalContext.current),
            plotId = 1 // Valor simulado de farmId para la previsualización
        )
    }
}
