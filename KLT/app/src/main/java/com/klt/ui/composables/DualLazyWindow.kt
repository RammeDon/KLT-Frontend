package com.klt.ui.composables

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.screens.KLTItem

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable

fun DualLazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    leftButtonText: String,
    rightButtonText: String,
    leftLazyItems: List<KLTItem>,
    rightLazyItems: List<KLTItem>,
    leftIcons: ImageVector? = null,
    rightIcons: ImageVector? = null,
    leftDestination: String,
    rightDestination: String

) {
    val buttonSelectedColor = Color(0XFF746C70)
    val buttonUnSelectedColor = Color(0xFFC5C5C5)
    var leftButtonSelected by remember { mutableStateOf(false) }
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
                            leftButtonSelected = true
                            leftButtonColor = buttonSelectedColor
                            rightButtonColor = buttonUnSelectedColor
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = leftButtonColor)
                    ) {
                        Text(
                            text = leftButtonText,
                            color = if (leftButtonSelected) Color.White else Color.Black
                        )
                    }
                    Spacer(modifier = Modifier.width(5.dp))
                    Button(
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f),
                        onClick = {
                            leftButtonSelected = false
                            rightButtonColor = buttonSelectedColor
                            leftButtonColor = buttonUnSelectedColor
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = rightButtonColor)
                    ) {
                        Text(
                            text = rightButtonText,
                            color = if (!leftButtonSelected) Color.White else Color.Black
                        )
                    }
                }
            }, content = {
                Box(
                    modifier = Modifier
                        .padding(top = 10.dp)
                ) {
                    if (leftButtonSelected) {
                        LazyWindow(
                            navController = navController,
                            destination = leftDestination, // change it so that it takes the destination parameter as value
                            items = leftLazyItems,
                            repeats = 3,
                            color = Color(0XFFC85250),
                            icon = leftIcons,
                        )
                    } else {
                        LazyWindow(
                            navController = navController,
                            destination = rightDestination,
                            items = rightLazyItems,
                            repeats = 15,
                            color = Color.LightGray,
                            icon = rightIcons
                        )
                    }
                }
            }
        )
    }
}