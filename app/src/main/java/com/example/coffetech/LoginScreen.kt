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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.ui.theme.CoffeTechTheme



@Composable
fun LoginScreen(modifier: Modifier.Companion = Modifier, navController: NavController) {
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

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
            LoginLogoImage()
            LoginTitle()
            WelcomeText()
            LoginEmailField(email = email, onEmailChange = { email = it })
            LoginPasswordField(password = password, onPasswordChange = { password = it })
            ForgotPasswordButton(navController = navController)
            LoginButton()
            LoginToRegisterButton(navController = navController)
        }
    }
}

@Composable
fun LoginLogoImage() {
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
fun LoginTitle() {
    Text(
        text = "!Bienvenido!",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 40.sp,
            fontWeight = FontWeight.W800),
        color = Color(0xFF31373E),
        modifier = Modifier.padding(top = 60.dp)
    )
}

@Composable
fun WelcomeText() {
    Text(
        text = "Por favor inicia sesión para continuar",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 17.sp,
            fontWeight = FontWeight.W300),
        modifier = Modifier.padding(bottom = 50.dp, top = 16.dp)
    )
}

@Composable
fun LoginEmailField(email: String, onEmailChange: (String) -> Unit) {

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

        ),modifier = Modifier
            .padding(bottom = 1.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(4.dp))


    )
}


@Composable
fun LoginPasswordField(password: String, onPasswordChange: (String) -> Unit) {
    TextField(
        value = password,
        onValueChange = onPasswordChange,
        placeholder = { Text("Contraseña") },
        visualTransformation = PasswordVisualTransformation(),
        colors = TextFieldDefaults.colors(
            focusedPlaceholderColor = Color.Gray,
            unfocusedPlaceholderColor = Color.Gray,
            focusedContainerColor = Color.White,
            unfocusedContainerColor = Color.White,
            disabledContainerColor = Color.White,
            focusedIndicatorColor = Color.Transparent, // Para quitar la linea del textfield
            unfocusedIndicatorColor = Color.Transparent // Para quitar la linea del textfield

        ), modifier = Modifier
            .padding(top = 5.dp)
            .border(2.dp, Color.Gray, RoundedCornerShape(4.dp))
    )
}

@Composable
fun ForgotPasswordButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.ForgotScreen)
        },
        modifier = Modifier
            .padding(bottom = 16.dp),
    ) {
        Text("Olvide la contraseña",
            color = Color(0xFF49602D))

    }
}

@Composable
fun LoginButton() {
    Button(
        onClick = { /* handle login */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("Iniciar sesión")
    }
}

@Composable
fun LoginToRegisterButton(navController: NavController) {
    TextButton(
        onClick = {
            navController.navigate(Routes.RegisterScreen)
        },
        modifier = Modifier.padding(bottom = 16.dp)
    ) {
        Text("¿No tienes cuenta? Registrarse",
            color = Color(0xFF49602D))
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    CoffeTechTheme {
        LoginScreen(navController = NavController(LocalContext.current))
        }
}
