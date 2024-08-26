package com.example.coffetech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.LoginScreen) {
                composable(Routes.LoginScreen){
                   LoginScreen(navController =  navController)
                }
                composable(Routes.RegisterScreen){
                    RegisterScreen(navController = navController)
                }
                composable(Routes.ForgotScreen){
                    ForgotPassword(navController = navController)
                }


            }
        }

    }
}


