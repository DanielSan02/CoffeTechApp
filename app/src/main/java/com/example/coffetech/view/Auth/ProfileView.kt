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
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.ProfileViewModel
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.platform.LocalContext
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableFieldLabel
import com.example.coffetech.common.TopBarWithBackArrow

/**
 * Composable function that renders the profile editing screen.
 * It allows the user to view and edit their profile information, such as their name,
 * and navigate to change their password.
 *
 * @param modifier A [Modifier] to adjust the layout or appearance of the view.
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [ProfileViewModel] used to manage the state and logic for the profile view.
 */
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
    val isLoading by viewModel.isLoading
    val scrollState = rememberScrollState()
    val ErrorMessage by viewModel.nameErrorMessage

    // Load user data when the screen is first displayed
    LaunchedEffect(Unit) {
        viewModel.loadUserData(context)
    }

    Column(modifier = modifier.fillMaxSize()) {
        // Top bar with a back arrow for navigation
        TopBarWithBackArrow(
            onBackClick = { navController.navigate(Routes.StartView) },
            title = "Editar Perfil"
        )

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Profile image
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

            // Fields for editing the profile
            Column(
                modifier = Modifier.padding(16.dp),
            ) {
                // Name field
                ReusableFieldLabel(text = "Nombre")
                ReusableTextField(
                    value = name,
                    onValueChange = { viewModel.onNameChange(it) },
                    placeholder = "Nombre",
                    margin = 0.dp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Email field (disabled)
                ReusableFieldLabel(text = "Correo")
                ReusableTextField(
                    value = email,
                    onValueChange = { },
                    placeholder = "Correo",
                    enabled = false,
                    margin = 0.dp
                )
                Spacer(modifier = Modifier.height(16.dp))

                // Change password button
                ReusableFieldLabel(text = "Contraseña")
                TextButton(
                    onClick = { navController.navigate(Routes.ChangePasswordView) },
                    modifier = Modifier.align(Alignment.Start)
                ) {
                    Text("Cambiar contraseña", color = Color(0xFF49602D))
                }

                // Display error message if any
                if (errorMessage.isNotEmpty()) {
                    Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
                }
            }

            // Display name error message if any
            if (ErrorMessage.isNotEmpty()) {
                Text(text = ErrorMessage, color = Color.Red, modifier = Modifier.padding(top = 4.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Save button for updating profile
            SaveButton(
                isLoading = isLoading,
                isProfileUpdated = isProfileUpdated,
                name = name,
                onSaveClick = { viewModel.saveProfile(context) { /* Success action */ } }
            )
        }
    }
}

/**
 * Composable function that renders a button to save profile changes.
 *
 * @param isLoading A Boolean indicating whether the save process is in progress.
 * @param isProfileUpdated A Boolean indicating whether the profile has been updated and needs saving.
 * @param name The current name of the user.
 * @param onSaveClick A lambda function that triggers when the save button is clicked.
 */
@Composable
fun SaveButton(
    isLoading: Boolean,
    isProfileUpdated: Boolean,
    name: String,
    onSaveClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onSaveClick() }, // Disable click if loading
        colors = ButtonDefaults.buttonColors(
            containerColor = if (isProfileUpdated && name.isNotBlank()) Color(0xFF49602D) else Color(0xFF49602D).copy(alpha = 0.5f),
            contentColor = Color.White
        ),
        modifier = Modifier
            .width(200.dp)
            .padding(vertical = 16.dp),
        enabled = isProfileUpdated && name.isNotBlank() && !isLoading // Disable if not updated, name is blank, or loading
    ) {
        if (isLoading) {
            Text("Guardando...") // Display loading text
        } else {
            Text("Guardar") // Normal button text
        }
    }
}

/**
 * Preview function for the ProfileView.
 * It simulates the profile editing screen in a preview environment to visualize the layout.
 */
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
