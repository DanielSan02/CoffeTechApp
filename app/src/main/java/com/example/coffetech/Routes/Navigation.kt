// Navigation.kt

package com.example.coffetech.navigation

import NotificationView
import android.content.Context // Importar el Context correcto
import android.net.Uri
import androidx.activity.compose.BackHandler
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.coffetech.Routes.Routes
import com.example.coffetech.utils.SharedPreferencesHelper
import com.example.coffetech.view.Auth.LoginView
import com.example.coffetech.view.Auth.RegisterView
import com.example.coffetech.view.Auth.ForgotPasswordView
import com.example.coffetech.view.Auth.ConfirmTokenForgotPasswordView
import com.example.coffetech.view.Auth.VerifyAccountView
import com.example.coffetech.view.Auth.ChangePasswordView
import com.example.coffetech.view.Auth.NewPasswordView
import com.example.coffetech.view.Auth.ProfileView
import com.example.coffetech.view.Auth.RegisterPasswordView
import com.example.coffetech.view.Auth.StartView
import com.example.coffetech.view.Plot.PlotInformationView
import com.example.coffetech.view.Collaborator.AddCollaboratorView
import com.example.coffetech.view.Collaborator.CollaboratorView
import com.example.coffetech.view.Collaborator.EditCollaboratorView
import com.example.coffetech.view.Plot.CreateMapPlotView
import com.example.coffetech.view.Plot.CreatePlotInformationView
import com.example.coffetech.view.Plot.EditMapPlotView
import com.example.coffetech.view.Plot.EditPlotInformationView
import com.example.coffetech.view.farm.CreateFarmView
import com.example.coffetech.view.farm.FarmEditView
import com.example.coffetech.view.farm.FarmInformationView
import com.example.coffetech.view.farm.FarmView
import com.example.coffetech.viewmodel.Plot.CreatePlotInformationViewModel


/**
 * Composable function that sets up the app's navigation using the Navigation component in Jetpack Compose.
 * This function defines the navigation graph, handling different routes for various views in the app.
 *
 * @param context The [Context] used for accessing SharedPreferences and other system resources.
 */
@Composable
fun AppNavHost(context: Context) {
    // Create the NavController to handle navigation between screens
    val navController = rememberNavController()

    // Helper for managing SharedPreferences (e.g., login state)
    val sharedPreferencesHelper = SharedPreferencesHelper(context)

    // Check if the user is logged in based on saved session data
    val isLoggedIn = sharedPreferencesHelper.isLoggedIn()

    // BackHandler to disable the default back navigation behavior
    BackHandler {
        // No action performed, back gesture is disabled
    }

    // Navigation host for defining the navigation graph and initial destination
    NavHost(
        navController = navController,
        startDestination = if (isLoggedIn) Routes.StartView else Routes.LoginView // Start at StartView if logged in, otherwise LoginView
    ) {

        /**
         * Composable destination for the LoginView.
         */
        composable(Routes.LoginView) {
            LoginView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the RegisterView.
         */


        composable(
            route = "${Routes.RegisterView}?name={name}&email={email}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType; defaultValue = "" },
                navArgument("email") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            RegisterView(navController = navController, name = name, email = email)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the ForgotPasswordView.
         */
        composable(Routes.ForgotPasswordView) {
            ForgotPasswordView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the ConfirmTokenForgotPasswordView.
         */
        composable(Routes.ConfirmTokenForgotPasswordView) {
            ConfirmTokenForgotPasswordView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the VerifyAccountView.
         */
        composable(Routes.VerifyAccountView) {
            VerifyAccountView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the NewPasswordView with a token as an argument.
         * This view handles the password reset process.
         *
         * @param token The token required for resetting the password.
         */
        composable(
            route = "${Routes.NewPasswordView}/{token}",
            arguments = listOf(navArgument("token") { type = NavType.StringType })
        ) { backStackEntry ->
            val token = backStackEntry.arguments?.getString("token") ?: ""
            NewPasswordView(navController = navController, token = token)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the FarmView, which displays a list of farms.
         */
        composable(Routes.FarmView) {
            FarmView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }


        /**
         * Composable destination for the FarmEditView, allowing users to edit farm details.
         */
        composable(
            route = "${Routes.FarmEditView}/{farmId}/{farmName}/{farmArea}/{unitOfMeasure}",
            arguments = listOf(
                navArgument("farmId") { type = NavType.IntType },
                navArgument("farmName") { type = NavType.StringType },
                navArgument("farmArea") { type = NavType.StringType },
                navArgument("unitOfMeasure") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            val farmName = backStackEntry.arguments?.getString("farmName") ?: ""
            val farmArea = backStackEntry.arguments?.getString("farmArea") ?: ""
            val unitOfMeasure = backStackEntry.arguments?.getString("unitOfMeasure") ?: ""
            BackHandler {
                // No action performed, back gesture is disabled
            }
            FarmEditView(
                navController = navController,
                farmId = farmId,
                farmName = farmName,
                farmArea = farmArea,
                unitOfMeasure = unitOfMeasure
            )
        }

        /**
         * Composable destination for the FarmInformationView, which displays detailed information about a farm.
         */
        composable("FarmInformationView/{farmId}") { backStackEntry ->
            val farmId = backStackEntry.arguments?.getString("farmId")?.toIntOrNull() ?: 0
            FarmInformationView(navController = navController, farmId = farmId)
            BackHandler {
                // No action performed, back gesture is disabled
            }
        }


        /**
         * Composable destination for the CreateFarmView, allowing users to create a new farm.
         */
        composable(Routes.CreateFarmView) {
            CreateFarmView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the StartView, the main screen that users see after logging in.
         */
        composable(Routes.StartView) {
            StartView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the ProfileView, which allows users to view and edit their profile.
         */
        composable(Routes.ProfileView) {
            ProfileView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        /**
         * Composable destination for the ChangePasswordView, which allows users to change their password.
         */
        composable(Routes.ChangePasswordView) {
            ChangePasswordView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        composable(
            route = "${Routes.RegisterPasswordView}/{name}/{email}",
            arguments = listOf(
                navArgument("name") { type = NavType.StringType },
                navArgument("email") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val name = backStackEntry.arguments?.getString("name") ?: ""
            val email = backStackEntry.arguments?.getString("email") ?: ""
            RegisterPasswordView(navController = navController, name = name, email = email)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }


        composable(Routes.NotificationView) {
            NotificationView(navController = navController)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        composable(
            route = "${Routes.CollaboratorView}/{farmId}/{farmName}/{role}",
            arguments = listOf(
                navArgument("farmId") { type = NavType.IntType },
                navArgument("farmName") { type = NavType.StringType },
                navArgument("role") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            val farmName = backStackEntry.arguments?.getString("farmName") ?: ""
            val role = backStackEntry.arguments?.getString("role") ?: ""

            CollaboratorView(navController = navController, farmId = farmId, farmName = farmName, role = role)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }

        composable(
            route = "${Routes.AddCollaboratorView}/{farmId}/{farmName}/{role}",
            arguments = listOf(
                navArgument("farmId") { type = NavType.IntType },
                navArgument("farmName") { type = NavType.StringType },
                navArgument("role") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            val farmName = backStackEntry.arguments?.getString("farmName") ?: ""
            val role = backStackEntry.arguments?.getString("role") ?: ""

            AddCollaboratorView(navController = navController, farmId = farmId, farmName = farmName, role = role)
            BackHandler {
                // Prevents back navigation gesture here
            }
        }
        composable(
            route = "${Routes.EditCollaboratorView}/{farmId}/{collaboratorId}/{collaboratorName}/{collaboratorEmail}/{selectedRole}/{role}",
            arguments = listOf(
                navArgument("farmId") { type = NavType.IntType },
                navArgument("collaboratorId") { type = NavType.IntType },
                navArgument("collaboratorName") { type = NavType.StringType },
                navArgument("collaboratorEmail") { type = NavType.StringType },
                navArgument("selectedRole") { type = NavType.StringType },
                navArgument("role") { type = NavType.StringType }

            )
        ) { backStackEntry ->
            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            val collaboratorId = backStackEntry.arguments?.getInt("collaboratorId") ?: 0
            val collaboratorName = backStackEntry.arguments?.getString("collaboratorName") ?: ""
            val collaboratorEmail = backStackEntry.arguments?.getString("collaboratorEmail") ?: ""
            val selectedRole = backStackEntry.arguments?.getString("selectedRole") ?: ""
            val role = backStackEntry.arguments?.getString("role") ?: ""

            BackHandler {
                // No action performed, back gesture is disabled
            }
            // Llama a la vista `EditCollaboratorView` con los parámetros necesarios
            EditCollaboratorView(
                navController = navController,
                farmId = farmId,
                collaboratorId = collaboratorId,
                collaboratorName = collaboratorName,
                collaboratorEmail= collaboratorEmail,
                selectedRole = selectedRole,
                role = role
            )
        }



        composable(
            route = "createPlotInformationView/{farmId}?plotName={plotName}&selectedVariety={selectedVariety}",
            arguments = listOf(
                navArgument("farmId") { type = NavType.IntType },
                navArgument("plotName") { type = NavType.StringType; defaultValue = "" },
                navArgument("selectedVariety") { type = NavType.StringType; defaultValue = "" }
            )
        ) { backStackEntry ->
            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            val plotName = backStackEntry.arguments?.getString("plotName") ?: ""
            val selectedVariety = backStackEntry.arguments?.getString("selectedVariety") ?: ""
            BackHandler {
                // No action performed, back gesture is disabled
            }
            CreatePlotInformationView(
                navController = navController,
                farmId = farmId,
                plotName = plotName,
                selectedVariety = selectedVariety
            )
        }

        composable(
            route = "createMapPlotView/{farmId}/{plotName}/{selectedVariety}",
            arguments = listOf(
                navArgument("farmId") { type = NavType.IntType },
                navArgument("plotName") { type = NavType.StringType },
                navArgument("selectedVariety") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            val plotName = backStackEntry.arguments?.getString("plotName") ?: ""
            val selectedVariety = backStackEntry.arguments?.getString("selectedVariety") ?: ""
            BackHandler {
                // No action performed, back gesture is disabled
            }
            CreateMapPlotView(
                navController = navController,
                farmId = farmId,
                plotName = plotName,
                selectedVariety = selectedVariety
            )
        }


        composable(
            route = "PlotInformationView/{plotId}/{farmName}/{farmId}",
            arguments = listOf(
                navArgument("plotId") { type = NavType.IntType },

                navArgument("farmName") { type = NavType.StringType },
                navArgument("farmId") { type = NavType.IntType },


                )
        ) { backStackEntry ->
            val plotId = backStackEntry.arguments?.getInt("plotId") ?: 0

            val farmName = backStackEntry.arguments?.getString("farmName") ?: ""

            val farmId = backStackEntry.arguments?.getInt("farmId") ?: 0
            BackHandler {
                // No action performed, back gesture is disabled
            }
            PlotInformationView(
                navController = navController,
                plotId = plotId,
                farmId = farmId,
                farmName = farmName
            )
        }

        composable(
            route = "${Routes.EditPlotInformationView}/{plotId}/{plotName}/{selectedVariety}",
            arguments = listOf(
                navArgument("plotId") { type = NavType.IntType },
                navArgument("plotName") { type = NavType.StringType },
                navArgument("selectedVariety") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val plotId = backStackEntry.arguments?.getInt("plotId") ?: 0
            val plotName = backStackEntry.arguments?.getString("plotName") ?: ""
            val selectedVariety = backStackEntry.arguments?.getString("selectedVariety") ?: ""
            BackHandler {
                // No action performed, back gesture is disabled
            }
            EditPlotInformationView(
                navController = navController,
                plotId = plotId,
                plotName = Uri.decode(plotName),
                selectedVariety = Uri.decode(selectedVariety)
            )
        }

        // Agrega esta ruta en Navigation.kt
        composable(
            route = "${Routes.EditMapPlotView}/{plotId}/{latitude}/{longitude}/{altitude}",
            arguments = listOf(
                navArgument("plotId") { type = NavType.IntType },
                navArgument("latitude") { type = NavType.StringType },
                navArgument("longitude") { type = NavType.StringType },
                navArgument("altitude") { type = NavType.StringType }
            )
        ) { backStackEntry ->
            val plotId = backStackEntry.arguments?.getInt("plotId") ?: 0
            val latitude = backStackEntry.arguments?.getString("latitude")?.toDoubleOrNull() ?: 0.0
            val longitude = backStackEntry.arguments?.getString("longitude")?.toDoubleOrNull() ?: 0.0
            val altitude = backStackEntry.arguments?.getString("altitude")?.toDoubleOrNull() ?: 0.0

            EditMapPlotView(
                navController = navController,
                plotId = plotId,
                initialLatitude = latitude,
                initialLongitude = longitude,
                initialAltitude = altitude
            )
        }


    }}
