package com.example.coffetech.view.Auth

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.coffetech.R
import com.example.coffetech.common.FloatingActionButtonGroup
import com.example.coffetech.common.ReusableButton
import com.example.coffetech.common.ReusableSearchBar
import com.example.coffetech.ui.theme.CoffeTechTheme
import com.example.coffetech.view.common.HeaderFooterView

import com.example.coffetech.viewmodel.Auth.StartViewModel

@SuppressLint("SuspiciousIndentation")
/**
 * Composable function that renders the Start screen of the CoffeeTech app.
 * This view displays the main content and incorporates a header and footer with navigation.
 *
 * @param navController The [NavController] used for navigation between different screens.
 * @param viewModel The [StartViewModel] that manages the state and logic for the start screen.
 */
@Composable
fun StartView(
    navController: NavController,
    viewModel: StartViewModel = viewModel() // Injects the ViewModel here
) {
    var isMenuVisible by remember { mutableStateOf(false) }
    var query by remember { mutableStateOf(TextFieldValue("")) }

    // Profile image used in the header or other parts of the UI
    val profileImage: Painter = painterResource(id = R.drawable.menu_icon)

    // Calls HeaderFooterView which contains the top bar and bottom bar logic
    HeaderFooterView(
        title = "CoffeeTech",
        currentView = "Inicio",
        navController = navController
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFF2F2F2)) // Applies background color to the central content
                .padding(16.dp) // Adds padding if necessary
        ) {
            // Central content for the Start screen
            Text(
                text = "Contenido central", // Placeholder for central content
                color = Color.Black,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

/**
 * Preview function for the StartView.
 * It simulates the Start screen in a preview environment to visualize the layout.
 */
@Preview(showBackground = true)
@Composable
fun StartViewPreview() {
    CoffeTechTheme {
        StartView(navController = NavController(LocalContext.current))
    }
}
