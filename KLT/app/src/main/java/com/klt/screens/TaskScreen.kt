package com.klt.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.composables.DualLazyWindow
import com.klt.ui.navigation.ActiveTask

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        Column(modifier = Modifier) {
            Text(text = "Tasks", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(text = "Click on a task to open it", fontSize = 14.sp)
            Spacer(Modifier.padding(vertical = 8.dp))
            DualLazyWindow(
                navController = navController,
                leftButtonText = "Completed",
                rightButtonText = "Uncompleted",
                leftLazyItems = listOfTasks,
                rightLazyItems = listOfTasks,
                leftIcons = Icons.Outlined.Done,
                rightIcons = Icons.Outlined.ArrowForward,
                leftDestination = ActiveTask.route,
                rightDestination = ActiveTask.route
            )
        }

    }
}
