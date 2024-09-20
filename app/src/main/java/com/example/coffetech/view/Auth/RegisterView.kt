// RegisterScreen.kt (View)

package com.example.coffetech.view.Auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
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
import com.example.coffetech.common.ButtonType
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableTextButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.ReusableTittleLarge
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
    val isLoading by viewModel.isLoading

    BoxWithConstraints( // Detectamos el tamaño disponible
        modifier = modifier
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        val logoSize = if (maxHeight < 800.dp) 80.dp else 150.dp // Ajuste dinámico del tamaño del logo

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .padding(top = 25.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Displays the app logo con tamaño dinámico
            LogoImage(modifier = Modifier.size(logoSize))

            // Large header text for registration
            ReusableTittleLarge(
                text = "Crea tu Cuenta",
                modifier = Modifier.padding(top = 16.dp, bottom = 16.dp)
            )
            ReusableDescriptionText(text = "Por favor ingresa tus datos ")
            Spacer(modifier = Modifier.height(16.dp))

            // Input fields for name, email, password, and confirm password
            ReusableTextField(
                value = name,
                onValueChange = { viewModel.onNameChange(it) },
                placeholder = "Nombre o apodo"
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
                Text(
                    text = errorMessage,
                    color = Color.Red,
                    modifier = Modifier.padding(top = 8.dp)
                )
            }

            // Reemplazar RegisterButton por ReusableButton
            ReusableButton(
                text = if (isLoading) "Registrandose..." else "Registrarse",
                onClick = { viewModel.registerUser(navController, context) },
                buttonType = ButtonType.Green,
                enabled = !isLoading,
                modifier = Modifier.padding(bottom = 16.dp, top = 16.dp)
            )

            // Button to navigate to the login screen
            ReusableTextButton(
                navController = navController,
                text = "¿Ya tienes cuenta? Inicia sesión",
                maxWidth = 400.dp,
                destination = Routes.LoginView
            )
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
        Text(
            "¿Ya tienes cuenta? Inicia sesión",
            color = Color(0xFF49602D)
        ) // Text to navigate to the login screen
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
