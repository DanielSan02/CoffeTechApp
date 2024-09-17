// FarmView.kt

package com.example.coffetech.view.farm

import FarmViewModel
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.example.coffetech.common.FarmItemCard
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableRoleDropdown
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.common.RoleDropdown
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterView
import androidx.compose.ui.Alignment

// FarmView Function
@Composable
fun FarmView(
    navController: NavController,
    viewModel: FarmViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        viewModel.loadFarms(context)
        viewModel.loadRolesFromSharedPreferences(context) // Cargar roles desde SharedPreferences
    }

    // Obtenemos los estados del ViewModel
    val farms by viewModel.farms.collectAsState()
    val query by viewModel.searchQuery
    val selectedRole by viewModel.selectedRole // Puede ser String?
    val expanded by viewModel.isDropdownExpanded
    val isLoading by viewModel.isLoading
    val errorMessage by viewModel.errorMessage
    val roles by viewModel.roles.collectAsState()

    HeaderFooterView(
        title = "Mis Fincas",
        currentView = "Fincas",
        navController = navController
    ) {
        // Usamos Box para apilar la lista y el botón flotante
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
                // Buscador
                ReusableSearchBar(
                    query = query,
                    onQueryChanged = { viewModel.onSearchQueryChanged(it) },
                    text = "Buscar finca por nombre"
                )

                Spacer(modifier = Modifier.height(16.dp))

                // RoleDropdown para seleccionar el rol
                RoleDropdown(
                    selectedRole = selectedRole, // Puede ser null
                    onRoleChange = { viewModel.selectRole(it) }, // Ahora acepta String?
                    roles = roles,
                    expanded = expanded,
                    onExpandedChange = { viewModel.setDropdownExpanded(it) },
                    expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                    arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon)
                )

                Spacer(modifier = Modifier.height(16.dp))

                if (isLoading) {
                    // Mostrar un indicador de carga
                    Text("Cargando fincas...")
                } else if (errorMessage.isNotEmpty()) {
                    // Mostrar el mensaje de error
                    Text(text = errorMessage, color = Color.Red)
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(farms) { farm ->
                            Column {
                                FarmItemCard(
                                    farmName = farm.name,
                                    farmRole = farm.role,
                                    onClick = {
                                        viewModel.onFarmClick(farm)
                                        navController.navigate("FarmInformationView")
                                        //Aqui debe ir el id de farm que sera la finca donde se navegara${farm.id}}
                                    }
                                )
                                Spacer(modifier = Modifier.height(8.dp)) // Espacio entre las cards
                            }
                        }
                    }
                }
            }

            // Botón flotante
            FloatingActionButtonGroup(
                onMainButtonClick = { navController.navigate("CreateFarmView") },
                mainButtonIcon = painterResource(id = R.drawable.plus_icon),
                modifier = Modifier
                    .align(Alignment.BottomEnd) // Colocamos el botón en la esquina inferior derecha
                    .padding(16.dp) // Espaciado desde los bordes
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
fun FarmViewPreview() {
    CoffeTechTheme {
        FarmView(navController = NavController(LocalContext.current))
    }
}
