package com.example.coffetech

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.ui.theme.CoffeTechTheme

@Composable
fun ForgotPassword(modifier: Modifier.Companion = Modifier, navController: NavController) {

    var email by remember { mutableStateOf("") }

    Box(
        modifier = modifier
            .fillMaxSize()
            .padding(50.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ForgotTitle()
            ForgotText()
            ForgotEmailField(email = email, onEmailChange = { email = it })
            ForgotButton()
            ForgotBack(navController = navController)
            }

        }
    }


@Composable
fun ForgotTitle() {
    Text(
        text = "Restablecer contraseña",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 40.sp,
            fontWeight = FontWeight.W800),
            textAlign = TextAlign.Center,
        color = Color(0xFF31373E),
        modifier = Modifier.padding(top = 10.dp, bottom = 40.dp)
    )
}

@Composable
fun ForgotText() {
    Text(
        text = "Te enviaremos un email con las instrucciones para restablecer tu contraseña",
        style = TextStyle(
            fontFamily = FontFamily.Default,
            fontSize = 17.sp,
            fontWeight = FontWeight.W300),
            textAlign = TextAlign.Center,
        modifier = Modifier.padding(bottom = 50.dp, top = 5.dp, start= 50.dp, end = 40.dp)
    )
}

@Composable
fun ForgotEmailField(email: String, onEmailChange: (String) -> Unit) {

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
            padding(bottom = 50.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(10.dp))

        }
    )
}

@Composable
fun ForgotButton() {
    Button(
        onClick = { /* handle login */ },
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF49602D)),
        modifier = Modifier.padding(bottom = 5.dp)
    ) {
        Text("Enviar correo")
    }
}

@Composable
fun ForgotBack(navController: NavController) {
    TextButton(
        onClick = { navController.navigate(Routes.LoginScreen) },
        modifier = Modifier
            .padding(bottom = 5.dp),
    ) {
        Text("Volver",
            color = Color(0xFF49602D))

    }
}

@Preview(showBackground = true)
@Composable
fun ForgotPasswordPreview() {
    CoffeTechTheme {
        ForgotPassword(navController = NavController(LocalContext.current))
    }
}