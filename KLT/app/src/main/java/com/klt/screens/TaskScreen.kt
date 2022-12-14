package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.composables.LazyWindow
import com.klt.ui.composables.TitledDivider
import com.klt.ui.navigation.Tasks

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
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
            ) {

                Text(text = "Clients", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text(text = "Click on a client to show its tasks", fontSize = 14.sp)
                Spacer(Modifier.padding(vertical = 8.dp))
                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    TitledDivider(title = "Pinned Clients")
                    LazyWindow(
                        navController = navController,
                        destination = Tasks.route,
                        items = listOfClients,
                        repeats = 20,
                        color = Color.LightGray
                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    TitledDivider(title = "All Clients")
                    LazyWindow(
                        navController = navController,
                        destination = Tasks.route, // we don't yet have an ActiveTask screen
                        items = listOfTasks,
                        repeats = 20,
                        color = Color.Red
                    )
                }
            }
        }
    }
}
