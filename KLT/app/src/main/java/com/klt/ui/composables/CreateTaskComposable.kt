package com.klt.ui.composables

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.klt.R
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.Serializable

/*
 *
 *  I was not sure whether or not this would qualify as a "screen" per sé, as I think it is
 * possible to simply have it as an overlay over the existing context. Can you let me know your
 * thoughts on this? If the "swipe up" ends up being too complex we can always simplify and
 * simply build a different view/screen for this feature.
 *
 */

data class Task(
    @SerializedName("taskName")
    override var taskName: String = "",
    @SerializedName("goals")
    override val goals: MutableList<ITask.IGoal> = mutableStateListOf(),
    @SerializedName("pinned")
    override var pinned: Boolean = false,
    @SerializedName("requireOrderNumber")
    override var requireOrderNumber: Boolean = false,
    @SerializedName("customerId")
    override val id: String = "",
    @SerializedName("taskType")
    val taskType: String = "Daily"

) : ITask, Serializable

data class TaskGoal(
    @SerializedName("name")
    override var name: String,
    @SerializedName("unit")
    override var unit: String,
    @SerializedName("type")
    override var type: ITask.GoalDataTypes,
    @SerializedName("value")
    override var value: Any?
) : ITask.IGoal, Serializable


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateTaskComposable(
    customer: ICustomer,
    context: Context = LocalContext.current,
    BottomSheetStateCurrent: BottomSheetState,
    onTaskCreated: () -> Unit
) {

    val taskListHeight = LocalConfiguration.current.screenWidthDp / 1.4

    val task by remember {
        mutableStateOf(
            Task(id = customer.id)
        )
    }

    var taskName by remember { mutableStateOf(task.taskName) }
    var requireOrderNumber by remember { mutableStateOf(task.requireOrderNumber) }
    val coroutine = rememberCoroutineScope()


    // Called on response
    val onAPIRespond: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                onTaskCreated()
                coroutine.launch {
                    BottomSheetStateCurrent.collapse()
                }
                Looper.prepare()
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
            HttpStatus.UNAUTHORIZED -> {
                Looper.prepare()
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
            HttpStatus.FAILED -> {
                Looper.prepare()
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }
    }


    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Create new task for ${customer.name}",
        textAlign = TextAlign.Center,
        fontSize = 20.sp,
        modifier = Modifier.fillMaxWidth(),
    )

    Spacer(modifier = Modifier.height(10.dp))

    Text(
        text = "Task name",
        modifier = Modifier.padding(bottom = 10.dp)
    )

    Column() {
        OutlinedTextField(
            value = taskName,
            onValueChange = {
                taskName = it
                task.taskName = it
            },
            modifier = Modifier
                .height(50.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(
                    text = "Task Name..",
                    color = colorResource(id = R.color.KLT_DarkGray1)
                )
            },
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
                unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
            )
        )


        Box(
            contentAlignment = Alignment.CenterStart,
            modifier = Modifier.fillMaxWidth()
        ){

            Row() {
                Text(
                    text = "Requires order number?",
                    modifier = Modifier.padding(top = 10.dp)
                )

                Checkbox(
                    checked = requireOrderNumber,
                    onCheckedChange = {
                        requireOrderNumber = !requireOrderNumber
                        task.requireOrderNumber = requireOrderNumber
                  },
                )
            }

        }
    }

    Spacer(modifier = Modifier.height(10.dp))

    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(10.dp),
        modifier = Modifier.height(taskListHeight.dp),
        content = {


            for ((index, item) in task.goals.withIndex()) {
                item {
                    CreateTaskGoal(
                        Modifier,
                        index + 1,
                        item as TaskGoal,
                        onRemove = { task.goals.removeAt(index) }
                    )
                }
            }

            item {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    ClickableText(
                        text = AnnotatedString("Add New Task Goal"),
                        onClick = { task.goals.add(
                            TaskGoal(
                                name = "",
                                unit = "",
                                type = ITask.GoalDataTypes.Number,
                                value = null
                            )
                        )},
                        modifier = Modifier
                            .alpha(0.6f)
                            .padding(top = 10.dp)
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
            onClick = {

                var allFieldsPopulated = (task.taskName != "" && task.goals.isNotEmpty())

                task.goals.map {
                    if (it.name == "") {
                        allFieldsPopulated = false
                    }
                }

                if (allFieldsPopulated) {

                    coroutine.launch(Dispatchers.IO) {
                        ApiConnector.createTask(
                            token = LocalStorage.getToken(context),
                            taskAsJson = Gson().toJson(task),
                            onRespond = onAPIRespond
                        )
                    }
                } else {
                    Toast.makeText(context, "Not all fields are populated!", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .align(alignment = Alignment.CenterHorizontally)
                .padding(vertical = 30.dp),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.KLT_Red)
            )
        ) {
            Text(
                color = Color.White,
                text = "Create New Task",
                modifier = Modifier.padding(
                    vertical = 10.dp,
                    horizontal = 22.dp
                )
            )
        }
    }
}




@Composable
fun CreateTaskGoal(
    modifier: Modifier,
    taskNumber: Int,
    taskGoal: TaskGoal,
    onRemove: () -> Unit,
) {

    var taskGoalName by remember { mutableStateOf(taskGoal.name) }
    var taskGoalUnit by remember { mutableStateOf(taskGoal.unit) }

    Box(modifier = Modifier
        .fillMaxWidth()
        .then(modifier)) {
        Column(modifier = Modifier.padding(top = 10.dp)) {

            Text(text = "Task goal $taskNumber")

            Box(
                modifier = Modifier
                    .wrapContentHeight()
                    .fillMaxWidth()
            ) {

                Column() {
                    Row() {
                        OutlinedTextField(
                            value = taskGoalName,
                            onValueChange = {
                                taskGoalName = it
                                taskGoal.name = it
                            },
                            label = {
                                Text(
                                    text = "Task goal name $taskNumber",
                                    color = colorResource(id = R.color.KLT_DarkGray1)
                                )
                            },
                            modifier = Modifier.padding(end = 10.dp).width(250.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
                                unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
                            )
                        )

                        OutlinedTextField(
                            value = taskGoalUnit,
                            onValueChange = {
                                taskGoalUnit = it
                                taskGoal.unit = it
                            },
                            label = {
                                Text(
                                    text = "Unit",
                                    color = colorResource(id = R.color.KLT_DarkGray1),
                                )
                            },
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
                                unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
                            )
                        )
                    }

                    Row() {

                        DropDownMenu(
                            taskGoal = taskGoal,
                            modifier = Modifier
                                .width(250.dp)
                                .fillMaxHeight()
                        )

                        IconButton(
                            onClick = { onRemove() },
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(top = 8.dp, end = 20.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Delete,
                                contentDescription = "delete-icon",
                                modifier = Modifier.height(55.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}



@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DropDownMenu(
    modifier: Modifier,
    taskGoal: TaskGoal,
) {
    var expanded by remember { mutableStateOf(false) }
    var goalInputType by remember { mutableStateOf(taskGoal.type) }

    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { expanded = !expanded },
        modifier = Modifier.then(modifier)
    ) {
        OutlinedTextField(
            value = goalInputType.toString(),
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
            ITask.GoalDataTypes.values().forEach { selectedOption ->
                // menu item
                DropdownMenuItem(onClick = {
                    goalInputType = selectedOption
                    taskGoal.type = selectedOption
                    expanded = false
                }) {
                    Text(text = selectedOption.toString())
                }
            }
        }
    }
}
