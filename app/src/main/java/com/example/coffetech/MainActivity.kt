package com.example.coffetech

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffetech.Routes.Routes
import com.example.coffetech.viewAuth.ForgotPassword
import com.example.coffetech.viewAuth.LoginScreen
import com.example.coffetech.viewAuth.RegisterScreen
import com.example.coffetech.viewAuth.VerifyAccount
import com.example.coffetech.viewAuth.AlertSend
import com.example.coffetech.viewFinca.FincaScreen


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            NavHost(navController = navController, startDestination = Routes.FincaScreen) {
                composable(Routes.LoginScreen){
                   LoginScreen(navController =  navController)
                }

                composable(Routes.RegisterScreen){
                    RegisterScreen(navController = navController)
                }

                composable(Routes.ForgotScreen){
                    ForgotPassword(navController = navController)
                }

                composable(Routes.VerifyAccount){
                    VerifyAccount(navController = navController)
                }

                composable(Routes.AlertSend){
                    AlertSend(navController = navController)
                }

                composable(Routes.FincaScreen){
                    FincaScreen(navController = navController)
                }


            }
        }

    }
}


