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
import androidx.lifecycle.viewmodel.compose.viewModel // Importación correcta para obtener ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableLargeText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.viewmodel.Auth.LoginViewModel
/**
 * Composable function that renders the login screen.
 * This screen allows the user to input their email and password, and log into the app.
 *
 * @param modifier A [Modifier] for adjusting the layout or appearance of the view.
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [LoginViewModel] used to manage the state and logic for login.
 */
@Composable
fun LoginView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: LoginViewModel = viewModel()
) {
    val email by viewModel.email
    val password by viewModel.password
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

            // Displays a welcome message
            ReusableLargeText(text = "!Bienvenido!", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            // Displays a description text asking the user to log in
            ReusableDescriptionText(text = "Por favor inicia sesión para continuar")

            // Input field for the user's email
            ReusableTextField(
                value = email,
                onValueChange = { viewModel.onEmailChange(it) },
                placeholder = "Correo Electrónico"
            )

            // Input field for the user's password, hidden for security
            ReusableTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "Contraseña",
                isPassword = true
            )

            // Display an error message if one exists
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            // Button to navigate to the forgot password screen
            ForgotPasswordButton(navController = navController)

            // Button to log in the user, showing loading status when logging in
            LoginButton(
                isLoading = isLoading,
                onLoginClick = { viewModel.loginUser(navController, context) }
            )

            // Button to navigate to the register screen for new users
            LoginToRegisterButton(navController = navController)
        }
    }
}

/**
 * Composable function that renders a button to navigate to the forgot password screen.
 *
 * @param navController The [NavController] used for navigation.
 */
@Composable
fun ForgotPasswordButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.ForgotPasswordView)
        },
        modifier = Modifier.padding(bottom = 16.dp),
    ) {
        Text("Olvide la contraseña", color = Color(0xFF49602D))
    }
}

/**
 * Composable function that renders the login button.
 * The button triggers the login process when clicked.
 *
 * @param isLoading A Boolean indicating whether the login process is currently in progress.
 * @param onLoginClick A lambda function that triggers when the button is clicked.
 */
@Composable
fun LoginButton(
    isLoading: Boolean,
    onLoginClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onLoginClick() }, // Disables the button when loading
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 16.dp),
        enabled = !isLoading // Disables the button when loading
    ) {
        if (isLoading) {
            Text("Iniciando sesión...") // Loading text
        } else {
            Text("Iniciar sesión") // Normal text
        }
    }
}

/**
 * Composable function that renders a button to navigate to the registration screen.
 *
 * @param navController The [NavController] used for navigation.
 */
@Composable
fun LoginToRegisterButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.RegisterView)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("¿No tienes cuenta? Registrarse", color = Color(0xFF49602D))
    }
}

/**
 * Preview function for the LoginView.
 * It simulates the login screen in a preview environment to visualize the layout.
 */
@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CoffeTechTheme {
        LoginView(navController = NavController(LocalContext.current))
    }
}
