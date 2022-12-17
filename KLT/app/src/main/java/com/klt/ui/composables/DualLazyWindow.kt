package com.klt.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
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
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import com.klt.screens.listOfTasks
import com.klt.ui.navigation.Login
import com.klt.ui.navigation.Tasks

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun DualLazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
) {
    val buttonSelectedColor = colorResource(id = R.color.KLT_DarkGray1)
    val buttonUnSelectedColor = Color(0xFFC5C5C5)
    var completedTasksSelected by remember { mutableStateOf(false) }
    var leftButtonColor by remember { mutableStateOf(buttonUnSelectedColor) }
    var rightButtonColor by remember { mutableStateOf(buttonSelectedColor) }

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
                            leftButtonColor = buttonSelectedColor
                            rightButtonColor = buttonUnSelectedColor

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = leftButtonColor)
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
                            rightButtonColor = buttonSelectedColor
                            leftButtonColor = buttonUnSelectedColor

                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = rightButtonColor)
                    ) {
                        Text(text = "Uncompleted")
                    }
                }
            }, content = {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .background(color = Color(0xFFE9E9E9))
                ) {
                    if (completedTasksSelected) {
                        LazyWindow(
                            navController = navController,
                            destination = Login.route, // change it so that it takes the destination parameter as value
                            items = listOfTasks,
                            repeats = 1,
                            color = Color(0XFFC85250),
                            icon = Icons.Outlined.Done,
                            modifier = Modifier.background(
                                color = Color(0XFFAEAEAE),
                                shape = RoundedCornerShape(5.dp)
                            )
                        )
                    } else {
                        LazyWindow(
                            navController = navController,
                            destination = Tasks.route,
                            items = listOfTasks,
                            repeats = 15,
                            color = Color.LightGray,
                            icon = Icons.Outlined.ArrowForward,
                            modifier = Modifier.background(
                                color = Color(0XFFAEAEAE),
                                shape = RoundedCornerShape(5.dp)
                            )
                        )
                    }
                }
            }
        )
    }
}