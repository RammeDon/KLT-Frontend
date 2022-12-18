package com.klt.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.drawers.BottomDrawer
import com.klt.ui.composables.DualLazyWindow
import com.klt.ui.navigation.ActiveTask

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val something: Any
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        //sheetBackgroundColor = colorResource(R.color.KLT_DarkGray1),
        sheetPeekHeight = 30.dp,
        sheetContent = {
            BottomDrawer()
        }) {
        Box(
            modifier = Modifier
                .padding(20.dp)
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
}
