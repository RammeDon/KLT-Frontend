package com.klt.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.DualLazyWindow

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val selectedColor = Color(0XFFbf9494)
    val unSelectedColor = Color(0xFFC5C5C5)
    var completedTasksSelected by remember { mutableStateOf(false) }
    var completedColor by remember { mutableStateOf(unSelectedColor) }
    var unCompletedColor by remember { mutableStateOf(selectedColor) }

    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {
        DualLazyWindow(navController = navController)
    }
}
