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
import com.klt.ui.composables.TextFieldTitle

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    TextFieldTitle("Username")

    // placeholder text
    Text(
        text = "Screen: ForgotPassword",
        textAlign = TextAlign.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(top = (LocalConfiguration.current.screenHeightDp / 2.5).dp)
    )

}
