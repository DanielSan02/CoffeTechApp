// ProfileView.kt
package com.example.coffetech.view.Auth

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.TopBarWithHamburger
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.ProfileViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableFieldLabel
import com.example.coffetech.common.TopBarWithBackArrow

@Composable
fun ProfileView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: ProfileViewModel = viewModel()
) {
    val context = LocalContext.current
    val name by viewModel.name
    val email by viewModel.email
    val errorMessage by viewModel.errorMessage
    val isProfileUpdated by viewModel.isProfileUpdated
    val scrollState = rememberScrollState()

    // Cargar los datos del usuario al iniciar la vista
    LaunchedEffect(Unit) {
        viewModel.loadUserData(context)
    }

    Column(modifier = modifier.fillMaxSize()) {
        TopBarWithBackArrow(
            onBackClick = { navController.popBackStack() },
            title = "Editar Perfil"
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .padding(bottom = 16.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "Profile Picture",
                    modifier = Modifier.fillMaxSize()
                )
            }

            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                ReusableFieldLabel(text = "Nombre")
                ReusableTextField(
                    value = name, // El nombre ya se cargará desde el ViewModel
                    onValueChange = { viewModel.onNameChange(it) },
                    placeholder = "Nombre",
                    margin = 0.dp
                )
                Spacer(modifier = Modifier.height(16.dp))

                ReusableFieldLabel(text = "Correo")
                ReusableTextField(
                    value = email, // El correo ya se cargará desde el ViewModel
                    onValueChange = { },
                    placeholder = "Correo",
                    enabled = false, // Deshabilitado ya que el correo no debe ser editable
                    margin = 0.dp
                )
                Spacer(modifier = Modifier.height(16.dp))

                ReusableFieldLabel(text = "Contraseña")
                TextButton(
                    onClick = { navController.navigate(Routes.ChangePasswordView) },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Cambiar contraseña", color = Color(0xFF49602D))
                }

                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }

            // Botón Guardar
            Button(
                onClick = {
                    viewModel.saveProfile(context) {
                    }
                },
                enabled = isProfileUpdated, // Habilitar solo si el nombre ha cambiado
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
                modifier = Modifier
                    .width(200.dp)
                    .padding(vertical = 16.dp)
            ) {
                Text("Guardar")
            }

            TextButton(
                onClick = { /*viewModel.deleteAccount { navController.navigate("login") }*/ },
                modifier = Modifier.padding(bottom = 16.dp)
            ) {
                Text("Eliminar cuenta", color = Color.Red)
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun ProfileViewPreview() {
    // Wrapping in your app's theme
    CoffeTechTheme {
        // Creating a mock NavController for the preview
        val navController = NavController(LocalContext.current)

        // Creating a mock ViewModel with some sample data
        val viewModel = ProfileViewModel().apply {
            onNameChange("Daniela Beltrán")

        }

        // Rendering the ProfileView with the mock data
        ProfileView(
            navController = navController,
            viewModel = viewModel
        )
    }
}