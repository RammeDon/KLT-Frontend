package com.klt.drawers

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.klt.R
import com.klt.screens.CustomerSelected
import com.klt.util.Measurements

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomDrawer(
    modifier: Modifier = Modifier,
    sheetState: BottomSheetState
) {
    val height = LocalConfiguration.current.screenHeightDp / 1.6
    val textFieldWidth = LocalConfiguration.current.screenWidthDp / 1.65
    val taskListHeight = LocalConfiguration.current.screenWidthDp / 1.4
    var taskNumber by remember { mutableStateOf(1) }
    var taskName by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp) // Remove this
            .background(
                shape = RoundedCornerShape(topEnd = 10.dp, topStart = 10.dp),
                color = colorResource(R.color.KLT_WhiteGray2)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 10.dp)
                .padding(bottom = 10.dp)
        ) {
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                thickness = 7.dp,
                modifier = Modifier
                    .padding(horizontal = 130.dp)
                    .background(
                        shape = RoundedCornerShape(5.dp),
                        color = colorResource(R.color.KLT_DarkGray2)
                    )
            )
            Spacer(modifier = Modifier.height(30.dp))
            Text(
                text = "Create new task for ${CustomerSelected.name}",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(10.dp))
            Text(text = "Task name")
            OutlinedTextField(
                value = taskName,
                onValueChange = { taskName = it },
                modifier = Modifier
                    .height(50.dp)
                    .width(textFieldWidth.dp),
                placeholder = {
                    Text(
                        text = "Task Name...",
                        color = colorResource(id = R.color.KLT_DarkGray1)
                    )
                },
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
                    unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
                )
            )
            Spacer(modifier = Modifier.height(10.dp))

            LazyColumn(
                verticalArrangement = Arrangement.spacedBy(10.dp),
                modifier = Modifier.height(taskListHeight.dp),
                content = {
                    for (i in 1..taskNumber) {
                        item {
                            CreateTaskGoal(
                                Modifier,
                                i,
                                textFieldWidth,
                                taskNumberUpdater = { newTaskNumber -> taskNumber = newTaskNumber },
                                totalTasks = taskNumber
                            )
                        }
                    }
                    item {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            ClickableText(
                                text = AnnotatedString("Add New Goal"),
                                onClick = { taskNumber++ },
                                modifier = Modifier.alpha(0.6f)
                            )
                        }

                    }
                })
            Spacer(modifier = Modifier.height(10.dp))
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Button(
                    onClick = { /*TODO*/ },
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.KLT_Red)),
                ) {
                    Text(text = "Create New Task", color = Color.White)
                }
            }
        }
    }
}


@Composable
fun CreateTaskGoal(
    modifier: Modifier = Modifier,
    taskNumber: Int,
    textFieldWidth: Double,
    taskNumberUpdater: (Int) -> Unit,
    totalTasks: Int
) {
    var taskDescription by remember { mutableStateOf("") }
    val textFieldWidth = LocalConfiguration.current.screenWidthDp / 1.65

    Box(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier
                .height(60.dp)
        )
        {

            OutlinedTextField(
                value = taskDescription,
                onValueChange = { taskDescription = it },
                label = {
                    Text(
                        text = "Task $taskNumber",
                        color = colorResource(id = R.color.KLT_DarkGray1)
                    )
                },
                modifier = Modifier.width(textFieldWidth.dp),
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
                    unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
                )
            )
            Spacer(modifier = Modifier.width(5.dp))
            DropDownMenu()
            Spacer(modifier = Modifier.width(10.dp))
            IconButton(
                onClick = { taskNumberUpdater(totalTasks - 1) },
                modifier = Modifier
                    .width(40.dp)
                    .padding(top = 10.dp)
                    .border(
                        BorderStroke(1.dp, colorResource(id = R.color.KLT_DarkGray1)),
                        shape = RoundedCornerShape(5.dp)
                    ),
            ) {
                Icon(imageVector = Icons.Outlined.Delete, contentDescription = "delete-icon")
            }
        }
    }
}


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(modifier: Modifier = Modifier) {
    var expanded by remember { mutableStateOf(false) }
    var selectedItem by remember { mutableStateOf(Measurements.KG) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.widthIn(0.dp, 90.dp)
    ) {
        OutlinedTextField(
            value = selectedItem.toString(),
            onValueChange = {},
            readOnly = true,
            label = { },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(
                    expanded = expanded
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
                unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
            )
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            Measurements.values().forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    selectedItem = selectedOption
                    expanded = false
                }) {
                    Text(text = selectedOption.toString())
                }
            }
        }
    }

}
