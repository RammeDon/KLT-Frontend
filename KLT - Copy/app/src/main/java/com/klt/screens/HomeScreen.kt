package com.klt.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.navigation.Login

@Composable
fun HomeScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    // "Home" parent Box
    Box(modifier = modifier.then(Modifier.fillMaxSize())) {
        Text(
            "KLT: Home"
        )
        TextButton(modifier = Modifier.align(Alignment.Center), onClick = {
            navController.navigate(Login.route)
        }) {
            Text("Return to Login", fontSize = 32.sp)
        }
    }
}
