// FarmInformationView.kt

package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.common.*
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.farm.FarmInformationViewModel

@Composable
fun FarmInformationView(
    navController: NavController,
    viewModel: FarmInformationViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    // Obtenemos los estados del ViewModel
    val query by viewModel.searchQuery
    val farmName by viewModel.farmName.collectAsState()
    val farmArea by viewModel.farmArea.collectAsState()
    val collaboratorName by viewModel.collaboratorName.collectAsState()
    val selectedRole by viewModel.selectedRole.collectAsState()
    val lotes by viewModel.lotes.collectAsState()

    // Simular la obtención de datos
    viewModel.fetchFarmData()

    HeaderFooterSubView(
        title = "Nombre de Finca",
        currentView = "Fincas",
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
                .padding(16.dp)
        ) {
            // Mostrar el rol seleccionado

            // Barra de búsqueda reutilizable
            ReusableSearchBar(
                query = TextFieldValue(""),
                onQueryChanged = { viewModel.onSearchQueryChanged(it.text) },
                text = "Buscar lote por nombre"
            )

            Spacer(modifier = Modifier.height(16.dp))

            SelectedRoleDisplay(roleName = selectedRole)

            // Componente reutilizable de Información General
            GeneralInfoCard(
                farmName = farmName, // Usamos los datos obtenidos del ViewModel
                farmArea = farmArea,
                onEditClick = { viewModel.onEditFarm(navController) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Componente reutilizable de Colaboradores
            CollaboratorsCard(
                collaboratorName = collaboratorName, // Usamos los datos obtenidos del ViewModel
                onAddClick = { viewModel.onAddCollaborator(navController) }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Lista de Lotes
            LotesList(lotes = lotes)

            Spacer(modifier = Modifier.height(16.dp))


            // FloatingActionButton para agregar lotes
            CustomFloatingActionButton(
                onAddLoteClick = { viewModel.onAddLote(navController) }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FarmInformationViewPreview() {
    CoffeTechTheme {
        FarmInformationView(navController = NavController(LocalContext.current))
    }
}
