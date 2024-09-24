package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
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
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.farm.FarmInformationViewModel
import com.example.coffetech.Routes.Routes


@Composable
fun FarmInformationView(
    navController: NavController,
    farmId: Int,
    viewModel: FarmInformationViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    // Obtener el contexto para acceder a SharedPreferences o cualquier otra fuente del sessionToken
    val context = LocalContext.current
    val sessionToken = remember { SharedPreferencesHelper(context).getSessionToken() }

    // Llamar a loadFarmData cuando la vista se cargue
    LaunchedEffect(farmId) {
        sessionToken?.let {
            viewModel.loadFarmData(farmId, it, context)
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
    val lotes by viewModel.lotes.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    //val searchQuery by viewModel.searchQuery.collectAsState()

    // Verificar si el usuario tiene permiso para editar la finca
    val userHasPermissionToEdit = viewModel.hasPermission("edit_farm")
// Verificar si el usuario tiene permiso para eliminar la finca
    val userHasPermissionToDelete = viewModel.hasPermission("delete_farm")
    val userHasPermissionCollaborators = viewModel.hasPermission("add_operador_farm") || viewModel.hasPermission("agregar_operador_farm")
    val displayedFarmName = if (farmName.length > 21) {
        farmName.take(17) + "..." // Si tiene más de 13 caracteres, corta y añade "..."
    } else {
        farmName // Si es menor o igual a 13 caracteres, lo dejamos como está
    }


    // Vista principal
    HeaderFooterSubView(
        title = "Mi Finca",
        currentView = "Fincas",
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
                .padding(16.dp)
        ) {


            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically // Centra los elementos verticalmente
            ) {
                // Botón de eliminar alineado a la izquierda
                Text(
                    text = "Finca: $farmName",
                    color = Color.Black,
                    maxLines = 3, // Limita a una línea
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f), // Hace que el texto ocupe el espacio restante y se alinee a la derecha
                )

                Spacer(modifier = Modifier.width(20.dp)) // Espacio entre el botón y el texto

                // Nombre de la finca alineado a la derecha
                if (userHasPermissionToDelete) {
                    ReusableDeleteButton(
                        contentDescription = "Eliminar Finca",
                        onClick = { /* Lógica para eliminar la finca */ },
                        modifier = Modifier.size(48.dp)
                    )
                }
            }
            // Barra de búsqueda reutilizable
            /*ReusableSearchBar(
                query = TextFieldValue(searchQuery),
                onQueryChanged = { viewModel.onSearchQueryChanged(it.text) },
                text = "Buscar lote por nombre"
            )*/


            if (isLoading) {
                // Mostrar un indicador de carga
                CircularProgressIndicator()
                Text(
                    text = "Cargando datos de la finca...",
                    color =Color.Black,
                )
            } else if (errorMessage.isNotEmpty()) {
                // Mostrar el error si ocurrió algún problema
                Text(text = errorMessage, color = Color.Red)
            } else {


                // Mostrar el rol seleccionado
                SelectedRoleDisplay(roleName = selectedRole ?: "Sin rol")

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

                // Componente reutilizable de Colaboradores
                if (userHasPermissionCollaborators){
                    CollaboratorsCard(
                        collaboratorName = collaboratorName,
                        onAddClick = {
                            navController.navigate("CollaboratorView/$farmId/$farmName")
                        }
                    )



                }


                Spacer(modifier = Modifier.height(16.dp))

                // Lista de Lotes
                LotesList(lotes = lotes)

                Spacer(modifier = Modifier.height(16.dp))

                // FloatingActionButton para agregar lotes
                CustomFloatingActionButton(
                    onAddLoteClick = { navController.navigate(Routes.AddLocationPlot) }
                )
            }
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
