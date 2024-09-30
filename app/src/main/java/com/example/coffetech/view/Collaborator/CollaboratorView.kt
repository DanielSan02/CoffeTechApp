package com.example.coffetech.view.Collaborator

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.example.coffetech.common.CollaboratorInfoCard
import com.example.coffetech.common.FarmItemCard
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.common.RoleDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterView
import com.example.coffetech.viewmodel.Collaborator.Collaborator
import com.example.coffetech.viewmodel.Collaborator.CollaboratorViewModel

/**
 * Composable function that renders the farm management screen.
 * This screen allows the user to view, search, and filter farms by role, as well as navigate to create a new farm or view details of an existing one.
 *
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [FarmViewModel] used to manage the state and logic for the farm view.
 */
@Composable
fun CollaboratorView(
    navController: NavController,
    farmId: Int,  // Añadir farmId
    farmName: String,  // Añadir farmName
    viewModel: CollaboratorViewModel = viewModel() // Injects the ViewModel here
) {
    val context = LocalContext.current

    // Load the farms and roles when the composable is first displayed
    LaunchedEffect(farmId) {
        viewModel.loadRolesFromSharedPreferences(context)
        viewModel.loadCollaborators(context, farmId)
    }

    // Retrieve the current state from the ViewModel
    val collaborators by viewModel.collaborators.collectAsState()
    val collaboratorName by viewModel.collaboratorName.collectAsState()
    val query by viewModel.searchQuery
    val selectedRole by viewModel.selectedRole
    val expanded by viewModel.isDropdownExpanded
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val roles by viewModel.roles.collectAsState()

    // Header and Footer layout with content in between

    HeaderFooterView(
        title = "Mis Colaboradores",
        currentView = "Fincas",
        navController = navController
    ) {
        // Main content box with the list of farms and floating action button
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

                // Search bar for filtering farms by name
                ReusableSearchBar(
                    query = query,
                    onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    text = "Buscar colaborador por nombre"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // Dropdown menu for selecting user role
                RoleDropdown(
                    selectedRole = selectedRole, // Can be null
                    onRoleChange = { viewModel.selectRole(it) }, // Role change handler
                    roles = roles,
                    expanded = expanded,
                    onExpandedChange = { viewModel.setDropdownExpanded(it) },
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon)
                )



                Spacer(modifier = Modifier.height(16.dp))

                // Conditional UI based on the state of loading or error
                if (isLoading) {
                    Text("Cargando colaboradores...") // Show loading message
                } else if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red) // Show error message if any
                } else {

                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(collaborators) { collaborator ->
                            Column {
                                val cleanedCollaboratorName = collaborator.name.replace(Regex("\\s+"), " ")
                                val cleanedCollaboratorRole = collaborator.role.replace(Regex("\\s+"), " ")
                                val cleanedCollaboratorEmail = collaborator.email.replace(Regex("\\s+"), " ")

                                // Card for each farm in the list
                                CollaboratorInfoCard(
                                    collaboratorName = cleanedCollaboratorName,
                                    collaboratorRole = cleanedCollaboratorRole,
                                    collaboratorEmail = cleanedCollaboratorEmail,
                                    onEditClick = {
                                        viewModel.onEditCollaborator(
                                            navController = navController,
                                            farmId = farmId,
                                            collaboratorName = cleanedCollaboratorName,
                                            collaboratorEmail = cleanedCollaboratorEmail,
                                            selectedRole = cleanedCollaboratorRole
                                        )
                                    }
                                )


                                Spacer(modifier = Modifier.height(8.dp)) // Space between cards
                            }
                        }
                    }
                }
            }

            // Floating action button for creating a new farm
            FloatingActionButtonGroup(
                onMainButtonClick = {
                    navController.navigate("AddCollaboratorView/$farmId/$farmName")
                },
                mainButtonIcon = painterResource(id = R.drawable.plus_icon),
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            )
        }
    }
}

/**
 * Preview function for the FarmView.
 * It simulates the farm management screen in a preview environment to visualize the layout.
 */

@Preview(showBackground = true)
@Composable
fun CollaboratorViewPreview() {
    CoffeTechTheme {
        CollaboratorView(navController = NavController(LocalContext.current),
            farmName = "Finca Ejemplo",
            farmId= 1,)
    }
}

