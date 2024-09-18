// RegisterScreen.kt (View)

package com.example.coffetech.view.Auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableLargeText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.RegisterViewModel

/**
 * Composable function that renders the registration screen.
 * It allows the user to input their name, email, and password to create an account.
 *
 * @param modifier A [Modifier] for adjusting the layout or appearance of the view.
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [RegisterViewModel] used to manage the state and logic for the registration process.
 */
@Composable
fun RegisterView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: RegisterViewModel = viewModel()
) {
    val name by viewModel.name
    val email by viewModel.email
    val password by viewModel.password
    val confirmPassword by viewModel.confirmPassword
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val keyboardController = LocalSoftwareKeyboardController.current
    val isLoading by viewModel.isLoading

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Displays the app logo
            LogoImage()

            // Large header text for registration
            ReusableLargeText(text = "Crea tu Cuenta", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            // Input fields for name, email, password, and confirm password
            ReusableTextField(
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                placeholder = "Nombre"
            )

            ReusableTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = "Correo Electrónico"
            )

            ReusableTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "Contraseña",
                isPassword = true
            )

            ReusableTextField(
                value = confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                placeholder = "Confirmar Contraseña",
                isPassword = true
            )

            // Display an error message if one exists
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            // Button to submit registration details
            RegisterButton(
                isLoading = isLoading,
                onRegisterClick = { viewModel.registerUser(navController, context) }
            )

            // Button to navigate to the login screen
            ToLoginButton(navController = navController)
        }
    }
}

/**
 * Composable function that renders the registration button.
 * The button becomes disabled while the registration process is in progress.
 *
 * @param isLoading A Boolean indicating whether the registration process is in progress.
 * @param onRegisterClick A lambda function triggered when the button is clicked, initiating the registration process.
 */
@Composable
fun RegisterButton(
    isLoading: Boolean,
    onRegisterClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onRegisterClick() }, // Only trigger if not loading
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp),
        enabled = !isLoading // Disable button when loading
    ) {
        if (isLoading) {
            Text("Registrandose...") // Show loading text while in progress
        } else {
            Text("Registrarse") // Normal text
        }
    }
}

/**
 * Composable function that renders a button to navigate to the login screen.
 *
 * @param navController The [NavController] used for navigation.
 */
@Composable
fun ToLoginButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.LoginView) },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("¿Ya tienes cuenta? Inicia sesión", color = Color(0xFF49602D)) // Text to navigate to the login screen
    }
}

/**
 * Preview function for the RegisterView.
 * It simulates the registration screen in a preview environment to visualize the layout.
 */
@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    CoffeTechTheme {
        RegisterView(navController = NavController(LocalContext.current))
    }
}
