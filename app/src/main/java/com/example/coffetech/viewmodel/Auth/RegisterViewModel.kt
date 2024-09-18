// RegisterViewModel.kt (ViewModel)

package com.example.coffetech.viewmodel.Auth

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.model.RegisterRequest
import com.example.coffetech.model.RegisterResponse
import com.example.coffetech.model.RetrofitInstance
import com.example.coffetech.utils.SharedPreferencesHelper
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
/**
 * ViewModel for managing the state and logic of the user registration flow.
 * This ViewModel handles user input validation, performing the registration request, and navigation.
 */
class RegisterViewModel : ViewModel() {

    // State variables for managing user input, error messages, and loading status
    var name = mutableStateOf("")
        private set

    var email = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var confirmPassword = mutableStateOf("")
        private set

    var errorMessage = mutableStateOf("")
        private set

    var isLoading = mutableStateOf(false)
        private set

    /**
     * Updates the name value when the user inputs a new name.
     *
     * @param newName The new name entered by the user.
     */
    fun onNameChange(newName: String) {
        name.value = newName
    }

    /**
     * Updates the email value when the user inputs a new email.
     *
     * @param newEmail The new email entered by the user.
     */
    fun onEmailChange(newEmail: String) {
        email.value = newEmail
    }

    /**
     * Updates the password value when the user inputs a new password.
     *
     * @param newPassword The new password entered by the user.
     */
    fun onPasswordChange(newPassword: String) {
        password.value = newPassword
    }

    /**
     * Updates the confirm password value when the user inputs a new confirmation password.
     *
     * @param newConfirmPassword The new confirm password entered by the user.
     */
    fun onConfirmPasswordChange(newConfirmPassword: String) {
        confirmPassword.value = newConfirmPassword
    }

    /**
     * Registers the user by sending a registration request to the backend API.
     * It first validates the inputs for name, email, and password. If valid, it makes the registration request.
     * If successful, navigates to the account verification screen. Otherwise, it displays relevant error messages.
     *
     * @param navController The [NavController] used for navigation between screens.
     * @param context The [Context] used for displaying Toast messages.
     */
    fun registerUser(navController: NavController, context: Context) {
        // Check if all required fields are filled
        if (name.value.isBlank() || email.value.isBlank() || password.value.isBlank() || confirmPassword.value.isBlank()) {
            errorMessage.value = "Todos los campos son obligatorios"
            return
        }

        // Validate the password and email
        val (isValidPassword, passwordMessage) = validatePassword(password.value, confirmPassword.value)
        val isValidEmail = validateEmail(email.value)

        if (!isValidEmail) {
            errorMessage.value = "Correo electrónico no válido"
        } else if (!isValidPassword) {
            errorMessage.value = passwordMessage
        } else {
            errorMessage.value = "" // Clear error message

            isLoading.value = true // Set loading state to true

            val registerRequest = RegisterRequest(name.value, email.value, password.value, confirmPassword.value)

            // Make the registration request to the backend
            RetrofitInstance.api.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                    isLoading.value = false // Stop loading

                    if (response.isSuccessful) {
                        val responseBody = response.body()
                        responseBody?.let {
                            if (it.status == "success") {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()

                                // Navigate to the account verification screen
                                navController.navigate(Routes.VerifyAccountView) {
                                    popUpTo(Routes.RegisterView) { inclusive = true }
                                }

                            } else {
                                Toast.makeText(context, it.message, Toast.LENGTH_LONG).show()
                            }
                        }
                    } else {
                        val errorBody = response.errorBody()?.string()
                        errorBody?.let {
                            val errorJson = JSONObject(it)
                            val errorMessage = errorJson.getString("message")
                            Toast.makeText(context, errorMessage, Toast.LENGTH_LONG).show()
                        } ?: run {
                            val unknownErrorMessage = "Error desconocido al registrar usuario"
                            Toast.makeText(context, unknownErrorMessage, Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                    val failureMessage = "Fallo en la conexión: ${t.message}"
                    Toast.makeText(context, failureMessage, Toast.LENGTH_LONG).show()
                }
            })
        }
    }

    /**
     * Validates the password and confirm password. It ensures that the passwords match and meet security criteria.
     *
     * @param password The password entered by the user.
     * @param confirmPassword The confirmation password entered by the user.
     * @return A pair where the first value is a boolean indicating whether the validation was successful,
     * and the second value is an error message if applicable.
     */
    private fun validatePassword(password: String, confirmPassword: String): Pair<Boolean, String> {
        val specialCharacterPattern = Regex(".*[!@#\$%^&*(),.?\":{}|<>].*")
        val uppercasePattern = Regex(".*[A-Z].*")

        return when {
            password != confirmPassword -> Pair(false, "Las contraseñas no coinciden")
            password.length < 8 -> Pair(false, "La contraseña debe tener al menos 8 caracteres")
            !specialCharacterPattern.containsMatchIn(password) -> Pair(false, "La contraseña debe contener al menos un carácter especial")
            !uppercasePattern.containsMatchIn(password) -> Pair(false, "La contraseña debe contener al menos una letra mayúscula")
            else -> Pair(true, "Contraseña válida")
        }
    }

    /**
     * Validates the email format using Android's email pattern matcher.
     *
     * @param email The email to validate.
     * @return `true` if the email is valid, `false` otherwise.
     */
    private fun validateEmail(email: String): Boolean {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}
