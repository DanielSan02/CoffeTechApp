package com.example.coffetech

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.ui.theme.CoffeTechTheme
import android.widget.Toast
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.coffetech.data.RegisterRequest
import com.example.coffetech.data.RegisterResponse
import com.example.coffetech.data.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response



@Composable
fun RegisterScreen(modifier: Modifier = Modifier, navController: NavController){

    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("")}
    var password by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var errorMessage by remember { mutableStateOf("") }

    Box (
        modifier = modifier
            .fillMaxSize()
            .padding(5.dp),
        contentAlignment = Alignment.Center
    ){
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ){
            LogoImage()
            RegisterTitle()
            RegisterNameField(name= name, onNameChange = { name = it })
            RegisterEmailField(email = email, onEmailChange = { email = it })
            PasswordField(password = password, onPasswordChange = { password = it })
            ConfirmPassword(confirmpassword = confirmPassword, onConfirmPasswordChange= { confirmPassword = it})
            RegisterButton(
                name = name,
                email = email,
                password = password,
                confirmPassword = confirmPassword,
                onValidationError = { errorMessage = it }
            )

            // Mostrar mensaje de error si existe
            if (errorMessage.isNotEmpty()) {
                Text(text = errorMessage, color = Color.Red, modifier = Modifier.padding(top = 8.dp))
            }

            ToLoginButton(navController = navController)
        }
    }
}
@Composable
fun RegisterTitle() {
    Text(
        text = "Crea tu Cuenta",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 40.sp,
            fontWeight = FontWeight.W800),
        color = Color(0xFF31373E),
        modifier = Modifier.padding(top = 60.dp, bottom = 40.dp)
    )
}
@Composable
fun RegisterNameField(name: String, onNameChange: (String) -> Unit) {

    TextField(

        value = name,
        onValueChange = onNameChange,
        placeholder = { Text("Nombre de usuario") },
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent, // Para quitar la linea del textfield
            unfocusedIndicatorColor = Color.Transparent // Para quitar la linea del textfield
        ),
        modifier = Modifier.run {
            padding(bottom = 10.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))

        }

    )
}
@Composable
fun ConfirmPassword(confirmpassword: String, onConfirmPasswordChange: (String) -> Unit) {
    TextField(
        value = confirmpassword,
        onValueChange = onConfirmPasswordChange,
        placeholder = { Text("Confirmar Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .padding(top = 10.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent, // Para quitar la linea del textfield
            unfocusedIndicatorColor = Color.Transparent // Para quitar la linea del textfield
        )
    )
}


@Composable
fun LogoImage() {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.size(width = 205.dp, height = 212.dp)
    ) {
        // Sombra
        Box(
            modifier = Modifier
                .size(width = 205.dp, height = 212.dp)
                .offset(y = 5.dp) // Sombra hacia abajo
                .graphicsLayer {
                    shadowElevation = 10.dp.toPx()
                    shape = CircleShape
                    clip = true // Para recortar la sombra segun su forma
                    alpha = 3f // Opacidad
                }
                .background(Color.Transparent)
        )

        // Logo sin sombra
        Image(
            painter = painterResource(R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier
                .size(width = 205.dp, height = 212.dp)
                .clip(CircleShape)
                .background(Color.White)
        )
    }
}


@Composable
fun RegisterEmailField(email: String, onEmailChange: (String) -> Unit) {

    TextField(

        value = email,
        onValueChange = onEmailChange,
        placeholder = { Text("Correo Electrónico") },
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent, // Para quitar la linea del textfield
            unfocusedIndicatorColor = Color.Transparent // Para quitar la linea del textfield
        ),
        modifier = Modifier.run {
            padding(bottom = 10.dp)
                .border(2.dp, Color.Gray, RoundedCornerShape(10.dp))

        }
    )
}




@Composable
fun PasswordField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        modifier = Modifier
            .border(2.dp, Color.Gray, RoundedCornerShape(10.dp)),
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent, // Para quitar la linea del textfield
            unfocusedIndicatorColor = Color.Transparent // Para quitar la linea del textfield
        )

    )
}

@Composable
fun RegisterButton(
    name: String,
    email: String,
    password: String,
    confirmPassword: String,
    onValidationError: (String) -> Unit
) {
    val context = LocalContext.current

    Button(
        onClick = {
            // Validar que todos los campos estén completos
            if (name.isBlank() || email.isBlank() || password.isBlank() || confirmPassword.isBlank()) {
                onValidationError("Todos los campos son obligatorios")
                return@Button
            }

            val (isValidPassword, passwordMessage) = validatePassword(password, confirmPassword)
            val isValidEmail = validateEmail(email)

            if (!isValidEmail) {
                onValidationError("Correo electrónico no válido")
            } else if (!isValidPassword) {
                onValidationError(passwordMessage)
            } else {
                onValidationError("") // Limpiar el mensaje de error

                // Crear la solicitud de registro
                val registerRequest = RegisterRequest(name, email, password, confirmPassword)

                // Enviar la solicitud al servidor
                RetrofitInstance.api.registerUser(registerRequest).enqueue(object : Callback<RegisterResponse> {
                    override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                        if (response.isSuccessful) {
                            // Manejar respuesta exitosa
                            Toast.makeText(context, "Usuario registrado con éxito", Toast.LENGTH_LONG).show()
                        } else {
                            // Manejar error en la respuesta
                            onValidationError("Error al registrar usuario: ${response.message()}")
                        }
                    }

                    override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                        // Manejar fallo en la solicitud
                        onValidationError("Error de red: ${t.localizedMessage}")
                    }
                })
            }
        },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 5.dp, top = 18.dp)
    ) {
        Text("Registrarse")
    }
}



@Composable
fun ToLoginButton(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.LoginScreen) },

        ) {
        Text("¿Ya tienes cuenta? Inicia sesión",
            color = Color(0xFF49602D))

    }
}
fun validatePassword(password: String, confirmPassword: String): Pair<Boolean, String> {
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

fun validateEmail(email: String): Boolean {
    return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()
}

@Preview(showBackground = true)
@Composable
fun RegisterScreenPreview() {
    CoffeTechTheme {
        RegisterScreen(navController = NavController(LocalContext.current))
    }
}