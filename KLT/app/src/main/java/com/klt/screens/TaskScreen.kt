package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController

@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        /*TODO */
        // template column
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Screen: ${navController.currentBackStackEntry?.destination?.route}",
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
