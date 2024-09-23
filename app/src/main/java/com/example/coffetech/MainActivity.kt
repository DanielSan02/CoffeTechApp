package com.example.coffetech

import CommonDataViewModel
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.navigation.AppNavHost


import android.Manifest
import android.content.pm.PackageManager
import android.os.Build

import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge

import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.navigation.AppNavHost
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : ComponentActivity() {

    // Obtener la instancia del ViewModel usando la propiedad delegada by viewModels()
    private val commonDataViewModel: CommonDataViewModel by viewModels()
    // Llamada para solicitar permisos
    private val requestNotificationPermissionLauncher =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
            if (isGranted) {
                Log.d("MainActivity", "Permiso de notificaciones concedido")
            } else {
                Log.d("MainActivity", "Permiso de notificaciones denegado")
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        // Llamar a la función que verifica la versión y actualiza los datos si es necesario
        commonDataViewModel.updateDataIfVersionChanged(this)

        // Solicitar permiso para notificaciones si es necesario (Android 13 o superior)
        requestNotificationPermissionIfNeeded()

        // Obtener el token de FCM
        getFCMToken()

        // Establecer el contenido de la interfaz de usuario utilizando Compose
        setContent {
            CoffeTechTheme {
                AppNavHost(context = this)
            }
        }
    }
    private fun requestNotificationPermissionIfNeeded() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) { // Android 13 (API 33)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Si el permiso no ha sido otorgado, lo solicitamos.
                requestNotificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

    private fun getFCMToken() {
        // Obtener el token de FCM
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (!task.isSuccessful) {
                Log.w("MainActivity", "Fetching FCM registration token failed", task.exception)
                return@addOnCompleteListener
            }

            // Obtener el token de registro
            val token = task.result
            Log.d("MainActivity", "FCM Token: $token")

            // Aquí puedes enviar el token a tu servidor backend si es necesario
        }
    }
}

