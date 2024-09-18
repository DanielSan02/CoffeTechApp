// NewPasswordView.kt

package com.example.coffetech.view.Auth

import android.util.Log
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableCancelButton
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.NewPasswordViewModel


/**
 * Composable function that renders the screen for setting a new password.
 * The user enters and confirms their new password. The token received from the reset
 * password email is used to validate the request.
 *
 * @param modifier A [Modifier] for adjusting the layout or appearance of the view.
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [NewPasswordViewModel] that manages the state and logic for resetting the password.
 * @param token The token used to validate the password reset request.
 */
@Composable
fun NewPasswordView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: NewPasswordViewModel = viewModel(),
    token: String // Token passed for password reset validation
) {

    val password by viewModel.password
    val confirmPassword by viewModel.confirmPassword
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current
    val scrollState = rememberScrollState()
    val isLoading by viewModel.isLoading

    // Check if the token is valid and log it
    if (token.isBlank()) {
        Log.e("NewPasswordView", "Token is null or empty.")
        Toast.makeText(context, "Error: Invalid token", Toast.LENGTH_SHORT).show()
        return // Stop the composition if the token is invalid
    } else {
        Log.d("NewPasswordView", "Token received successfully: $token")
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp)
            .background(Color(0xFFF2F2F2)),
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
            Spacer(modifier = Modifier.height(40.dp))

            // Instructional text prompting the user to enter a new password
            ReusableDescriptionText(text = "Ingrese su nueva contraseña", fontSize = 25)
            Spacer(modifier = Modifier.height(16.dp))

            // Input field for the new password, with password masking
            ReusableTextField(
                value = password,
                onValueChange = { viewModel.onPasswordChange(it) },
                placeholder = "Nueva Contraseña",
                isPassword = true
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Input field to confirm the new password, with password masking
            ReusableTextField(
                value = confirmPassword,
                onValueChange = { viewModel.onConfirmPasswordChange(it) },
                placeholder = "Confirmar Contraseña",
                isPassword = true
            )

            // Displays an error message if there is any
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Button to submit the new password reset request
            ResetPasswordButton(
                isLoading = isLoading,
                onResetClick = { viewModel.resetPassword(navController, context, token) }
            )

            // Button to cancel the password reset process and return to the login screen
            ReusableCancelButton(
                navController = navController,
                destination = Routes.LoginView // Navigates to login screen on cancel
            )
        }
    }
}

/**
 * Composable function that renders the reset password button.
 * The button becomes disabled if the password reset request is in progress.
 *
 * @param isLoading A Boolean indicating whether the password reset process is in progress.
 * @param onResetClick A lambda function triggered when the button is clicked, initiating the password reset request.
 */
@Composable
fun ResetPasswordButton(
    isLoading: Boolean,
    onResetClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onResetClick() },
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White
        ),
        modifier = Modifier.padding(bottom = 16.dp, top = 10.dp),
        enabled = !isLoading // Disable the button if the request is in progress
    ) {
        if (isLoading) {
            Text("Restableciendo...") // Display loading text
        } else {
            Text("Restablecer") // Normal button text
        }
    }
}

/**
 * Preview function for the NewPasswordView.
 * It simulates the new password reset screen with a sample token to visualize the layout.
 */
@Preview(showBackground = true)
@Composable
fun NewPasswordPreview() {
    CoffeTechTheme {
        // A sample token is provided for preview purposes
        NewPasswordView(navController = NavController(LocalContext.current), token = "sampleToken")
    }
}

