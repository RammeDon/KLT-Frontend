package com.klt.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    // placeholder text
    Text(
        text = "Screen: ForgotPassword",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = (LocalConfiguration.current.screenHeightDp / 2.5).dp)
    )
}
