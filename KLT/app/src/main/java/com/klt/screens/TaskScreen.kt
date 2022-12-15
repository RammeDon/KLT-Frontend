package com.klt.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.LazyWindow
import com.klt.ui.navigation.Login
import com.klt.ui.navigation.Tasks

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val selectedColor = Color(0XFFbf9494)
    val unSelectedColor = Color(0xFFC5C5C5)
    var completedTasksSelected by remember { mutableStateOf(false) }
    var completedColor by remember { mutableStateOf(unSelectedColor) }
    var unCompletedColor by remember { mutableStateOf(selectedColor) }

    Box(
        modifier = Modifier
            .padding(20.dp)
    ) {

        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp)
                        .padding(horizontal = 10.dp)
                ) {

                    Button(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        onClick = {
                            completedTasksSelected = true
                            completedColor = selectedColor
                            unCompletedColor = unSelectedColor

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = completedColor)
                    ) {
                        Text(text = "Completed")

                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        onClick = {
                            completedTasksSelected = false
                            unCompletedColor = selectedColor
                            completedColor = unSelectedColor

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = unCompletedColor)
                    ) {
                        Text(text = "Uncompleted")
                    }
                }
            }, content = {
                Box(modifier = Modifier.padding(top = 10.dp)) {
                    if (completedTasksSelected) {
                        LazyWindow(
                            navController = navController,
                            destination = Login.route, // change it so that it takes the destination parameter as value
                            items = listOfTasks,
                            repeats = 1,
                            color = Color(0XFFC85250),
                            icon = Icons.Outlined.Done
                        )
                    } else {
                        LazyWindow(
                            navController = navController,
                            destination = Tasks.route,
                            items = listOfTasks,
                            repeats = 15,
                            color = Color.LightGray,
                            icon = Icons.Outlined.ArrowForward,
                        )
                    }
                }


            }
        )
    }


}
