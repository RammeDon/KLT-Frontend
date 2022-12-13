package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun ClientScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {

        Text(text = "Clients", fontSize = 26.sp, fontWeight = FontWeight.Bold)
        Text(text = "Click on a client to show its tasks", fontSize = 14.sp)
        Spacer(Modifier.padding(vertical = 8.dp))

        Row(
            modifier = Modifier
                .padding(vertical = 1.dp)
                .fillMaxWidth()
        ) {
            Divider(Modifier.width(150.dp), color = Color(0xFFD10000), thickness = 2.dp)
            Text(text = "Pinned Clients", fontSize = 14.sp)
            Divider(Modifier.width(150.dp), color = Color(0xFFD10000), thickness = 2.dp)
        }
    }
}
