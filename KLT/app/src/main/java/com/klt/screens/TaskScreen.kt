package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.composables.CollapsableLazyWindow
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

                Text(text = "Tasks", fontSize = 26.sp, fontWeight = FontWeight.Bold)
                Text(text = "Click on a task to open it", fontSize = 14.sp)
                Spacer(Modifier.padding(vertical = 8.dp))

                Column(
                    modifier = Modifier.fillMaxSize()
                ) {
                    CollapsableLazyWindow(
                        navController = navController,
                        destination = Tasks.route,
                        items = listOfClients,
                        repeats = 25,
                        color = Color.LightGray,
                        icon = Icons.Outlined.ArrowForward,
                        windowTitle = "Uncompleted Tasks",
                        defaultCollapsed = false

                    )
                    Spacer(modifier = Modifier.height(15.dp))
                    CollapsableLazyWindow(
                        navController = navController,
                        destination = Tasks.route, // we don't yet have an ActiveTask screen
                        items = listOfTasks,
                        repeats = 15,
                        color = Color.Red,
                        icon = Icons.Outlined.Done,
                        windowTitle = "Completed Tasks",
                        defaultCollapsed = true
                    )
                }
            }
        }
    }
}
