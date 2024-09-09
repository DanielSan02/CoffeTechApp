// FarmViewModel.kt

package com.example.coffetech.viewmodel.farm

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import com.example.coffetech.R

class FarmViewModel : ViewModel() {
    var isMenuVisible = { mutableStateOf(false) }
    private set
}
