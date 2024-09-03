package com.example.coffetech.viewAuth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.coffetech.common.LogoImage
import com.example.coffetech.common.ReusableDescriptionText
import com.example.coffetech.ui.theme.CoffeTechTheme


@Composable
fun AlertSend(navController: NavController) {
    Surface(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFF5F5F5)), // Color de fondo claro
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            LogoImage()
            Spacer(modifier = Modifier.height(54.dp))
            ReusableDescriptionText(text = "Hemos enviado un correo electrónico para restablecer tu contraseña", fontSize = 20)
            Spacer(modifier = Modifier.height(16.dp))
            GoToEmailButton()
        }
    }
}

@Composable
fun GoToEmailButton() {
    TextButton(onClick = { /* Acción para ir al correo */ }) {
        Text(
            text = "Ir al correo",
            color = Color(0xFF4A4A4A), // Mismo color que el texto
            fontSize = 16.sp,
            fontWeight = FontWeight.Medium
        )
    }
}

@Preview(showBackground = true)
@Composable
fun AlertSendPreview() {
    CoffeTechTheme {
        AlertSend(navController = NavController(LocalContext.current))
    }
}

