package com.klt.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.drawers.BottomDrawer
import com.klt.ui.composables.DualLazyWindow
import com.klt.ui.composables.KLTDivider
import com.klt.ui.navigation.ActiveTask
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {},
) {
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val scope = rememberCoroutineScope()
    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        //sheetBackgroundColor = colorResource(R.color.KLT_DarkGray1),
        sheetPeekHeight = 30.dp,
        topBar = {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = CustomerSelected.name,
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                KLTDivider()
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    ClickableText(
                        text = AnnotatedString("Add Task"),
                        modifier = Modifier.padding(top = 14.dp),
                        onClick = {
                            coroutine.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                    )
                    IconButton(
                        onClick = {
                            coroutine.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                        modifier = Modifier.padding(end = 30.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "")
                    }
                }

            }
        },
        sheetContent = { BottomDrawer(sheetState = scaffoldState.bottomSheetState) }) {
        Box(
            modifier = Modifier
                .padding(20.dp)
                .padding(top = 0.dp)
        ) {
            Column {
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
