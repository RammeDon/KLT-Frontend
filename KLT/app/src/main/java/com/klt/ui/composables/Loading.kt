package com.klt.ui.composables

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation.NavController


@Composable
fun Loading(
    modifier: Modifier = Modifier
) {
    // HERE WE CAN ADD A LOADING SCREEN IF WE WANT
    Text(text = "Loading..")
}
