// FarmView.kt

package com.example.coffetech.view.farm

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
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
import com.example.coffetech.common.FarmItem
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableRoleDropdown
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterView
import com.example.coffetech.viewmodel.farm.FarmViewModel

// FarmView Function
@Composable
fun FarmView(
    navController: NavController,
    viewModel: FarmViewModel = viewModel() // Inyecta el ViewModel aquí
) {
    // Obtenemos los estados del ViewModel
    val farms by viewModel.farms.collectAsState()
    val query by viewModel.searchQuery
    val selectedRole by viewModel.selectedRole
    val expanded by viewModel.isDropdownExpanded

    // Llamamos a BaseScreen que contiene la lógica del top bar y bottom bar
    HeaderFooterView(
        title = "Mis Fincas",
        currentView = "Fincas",
        navController = navController
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFEFEFEF))
                .padding(16.dp)
        ) {
            // Barra de búsqueda reutilizable
            ReusableSearchBar(
                query = TextFieldValue(query),
                onQueryChanged = { viewModel.onSearchQueryChanged(it.text) },
                text = "Buscar finca por nombre"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Menú desplegable de roles reutilizable
            ReusableRoleDropdown(
                expanded = expanded,
                onExpandedChange = { viewModel.setDropdownExpanded(it) },
                expandedArrowDropUp = painterResource(id = R.drawable.arrowdropup_icon),
                arrowDropDown = painterResource(id = R.drawable.arrowdropdown_icon),
                onRoleSelected = { role ->
                    viewModel.selectRole(role)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Espacio para las fincas

            // Mostrar lista de fincas
            LazyColumn {
                items(farms) { farm ->
                    FarmItem(
                        farm = farm,
                        onClick = { viewModel.onFarmClick(farm) }
                    )
                }
            }

            FloatingActionButtonGroup(
                onMainButtonClick = { navController.navigate("CreateFarmView") }, // Navega a CreateFarmView
                mainButtonIcon = painterResource(id = R.drawable.plus_icon)
            )
        }
    }
}



// Aquí usamos @Preview para previsualizar FarmView
@Preview(showBackground = true)
@Composable
fun FarmViewPreview() {
    CoffeTechTheme {
        FarmView(navController = NavController(LocalContext.current))
    }
}