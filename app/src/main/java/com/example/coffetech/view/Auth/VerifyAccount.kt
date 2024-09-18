// VerifyAccountView.kt

package com.example.coffetech.view.Auth

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
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
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.common.ReusableLargeText
import com.example.coffetech.common.ReusableTextField
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.viewmodel.Auth.VerifyAccountViewModel

/**
 * Composable function that renders the account verification screen.
 * The user inputs the verification code received via email to verify their account.
 *
 * @param modifier A [Modifier] for adjusting the layout or appearance of the view.
 * @param navController The [NavController] used for navigation between screens.
 * @param viewModel The [VerifyAccountViewModel] that manages the state and logic for the account verification process.
 */
@Composable
fun VerifyAccountView(
    modifier: Modifier = Modifier,
    navController: NavController,
    viewModel: VerifyAccountViewModel = viewModel() // Using ViewModel for handling state
) {
    val token by viewModel.token
    val errorMessage by viewModel.errorMessage
    val context = LocalContext.current
    val isLoading by viewModel.isLoading

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Title for the verification screen
            ReusableLargeText(text = "Verifica tu cuenta", modifier = Modifier.padding(top = 30.dp, bottom = 30.dp))

            // Description for entering the verification code
            ReusableDescriptionText(text = "Por favor, introduce el código para verificar tu correo")

            // Input field for the user to enter the verification token
            ReusableTextField(
                value = token,
                onValueChange = { viewModel.onTokenChange(it) },
                placeholder = "Código"
            )

            // Button to verify the account
            VerifyButton(
                isLoading = isLoading,
                onVerifyClick = { viewModel.verifyUser(navController, context) }
            )

            // Display an error message if one exists
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }
        }
    }
}

/**
 * Composable function that renders the button to submit the verification code.
 * The button is disabled while the verification process is in progress.
 *
 * @param isLoading A Boolean indicating whether the verification process is currently in progress.
 * @param onVerifyClick A lambda function triggered when the button is clicked, initiating the verification process.
 */
@Composable
fun VerifyButton(
    isLoading: Boolean,
    onVerifyClick: () -> Unit
) {
    Button(
        onClick = { if (!isLoading) onVerifyClick() }, // Only trigger if not loading
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xFF49602D),
            contentColor = Color.White),
        modifier = Modifier.padding(bottom = 16.dp, top = 16.dp),
        enabled = !isLoading // Disable button while loading
    ) {
        if (isLoading) {
            Text("Verificando...") // Show loading text
        } else {
            Text("Verificar Correo") // Normal button text
        }
    }
}

/**
 * Preview function for the VerifyAccountView.
 * It simulates the account verification screen in a preview environment to visualize the layout.
 */
@Preview(showBackground = true)
@Composable
fun VerifyAccountPreview() {
    CoffeTechTheme {
        VerifyAccountView(navController = NavController(LocalContext.current))
    }
}
