package com.example.coffetech.view.healthcheck

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.CheckingInfoCard
import com.example.coffetech.common.CollaboratorInfoCard
import com.example.coffetech.common.DateDropdown
import com.example.coffetech.common.FarmItemCard
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableDeleteButton
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.common.RoleDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterSubView
import com.example.coffetech.viewmodel.healthcheck.DetectionHistoryViewModel


@Composable
fun DetectionHistoryView(
    navController: NavController,
    farmId: Int,  // Añadir farmId
    farmName: String,  // Añadir farmName
    plotName: String,
    viewModel: DetectionHistoryViewModel = viewModel() // Injects the ViewModel here
) {
    val context = LocalContext.current


    // Retrieve the current state from the ViewModel
    val checkings by viewModel.checkings.collectAsState()
    val query by viewModel.searchQuery
    val selectedDate by viewModel.selectedDate
    val expanded by viewModel.isDropdownExpanded
    //val userHasPermissionToDelete = viewModel.hasPermission("delete_collaborator")
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val dates by viewModel.dates.collectAsState()


    HeaderFooterSubView(
        title = "Historial de detecciones",
        currentView = "Fincas",
        navController = navController,
        onBackClick = { navController.navigate("${Routes.FarmInformationView}/$farmId") },
    ) {
        // Main content box with the list of farms and floating action button

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(19.dp)
            ) {

                DateDropdown(
                    selectedDate = selectedDate, // Can be null
                    onDateChange = { }, // Role change handler
                    dates = dates,
                    expanded = expanded,
                    onExpandedChange = { },
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon)
                )


                Spacer(modifier = Modifier.height(16.dp))


                Text(text = "Finca: $farmName", color = Color.Black)



                Spacer(modifier = Modifier.height(16.dp))

                Text(text = "Lote: ${plotName.ifEmpty { "Sin Lote" }}", color = Color.Black)

                Spacer(modifier = Modifier.height(16.dp))

                // Conditional UI based on the state of loading or error
                /*if (isLoading) {
                    Text("Cargando colaboradores...") // Show loading message
                } else if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red) // Show error message if any
                } else {*/

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(checkings) { checking ->
                            val checkingId = checking.checkingId
                            val date = checking.date
                            val collaboratorName = checking.nameCollaborator
                            // Verificar permisos de edición basados en el rol del colaborador

                            CheckingInfoCard(
                                checkingId = checkingId.toString(),
                                date = date,
                                collaboratorName = collaboratorName,
                                onEditClick = {},
                                showEditIcon = true,
                                // Pasar una bandera para mostrar u ocultar el ícono de edición
                            )

                            Spacer(modifier = Modifier.height(8.dp))
                        }

                    }

                //}
            }
        }
}

/**
 * Preview function for the FarmView.
 * It simulates the farm management screen in a preview environment to visualize the layout.
 */

@Preview(showBackground = true)
@Composable
fun DetectionHistoryViewPreview() {
    CoffeTechTheme {
        DetectionHistoryView(navController = NavController(LocalContext.current),
            farmName = "Finca Ejemplo",
            farmId= 1,
            plotName= "Lote 1")
    }
}




