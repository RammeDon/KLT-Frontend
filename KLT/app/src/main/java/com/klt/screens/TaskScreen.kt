package com.klt.screens

import android.annotation.SuppressLint
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.ArrowForward
import androidx.compose.material.icons.outlined.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.drawers.BottomDrawer
import com.klt.ui.composables.*
import com.klt.ui.navigation.ActiveTask
import com.klt.ui.navigation.Clients
import com.klt.ui.navigation.Tasks
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

private object HardCodedCustomer: ICustomer {
    override val id: String = "63a06ac56cd6333d54a4d465"
    override val customerName: String = "Customer"
}

/* Item used to populate the task list */
private class TaskItem: ITask {
    override var taskName: String = ""
    override val goals: Array<ITask.IGoal> = arrayOf()
    override var requireOrderNumber: Boolean = false
    override var id: String = ""

}


@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    customer: ICustomer = HardCodedCustomer, // THIS SHOULD BE PASSED FROM CUSTOMER SCREEN
    OnSelfClick: () -> Unit = {},
) {


    val coroutine = rememberCoroutineScope()

    var haveFetchTasks by remember { mutableStateOf(false) }
    val tasks = remember { mutableStateListOf<IKLTItem>() }

    for (i in tasks) {
        Log.d("KLT_API_CONNECTOR", i.name)
    }

    val onFetchTasks: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val itemsArray = data.getJSONArray("tasks")
                for (i in 0 until itemsArray.length()) {
                    val item = itemsArray.getJSONObject(i)
                    val t = TaskItem()
                    t.id = item.getString("_id")
                    t.taskName = item.getString("name")
                    t.requireOrderNumber = item.getBoolean("requiresOrderNumber")
                    tasks.add(t)
                }
                haveFetchTasks = true
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

    // Fetch task's
    LaunchedEffect(tasks) {
        if (!haveFetchTasks) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getTasksFromCustomer(
                    token = LocalStorage.getToken(context),
                    customerId = customer.id,
                    onRespond = onFetchTasks
                )
            }
        }


    }




    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)


    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = scaffoldState.bottomSheetState.isExpanded,
        //sheetBackgroundColor = colorResource(R.color.KLT_DarkGray1),
        sheetPeekHeight = 0.dp,
        topBar = {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {

                ScreenSubTitle(
                    navController = navController,
                    onBackNavigation = Clients.route,
                    bigText = CustomerSelected.name,
                    smallText = ""
                )
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    ClickableText(
                        text = AnnotatedString("Add a custom task"),
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
        sheetContent = {
            BottomDrawer(
                content = { CreateTaskComposable(BottomSheetStateCurrent = sheetState) }
            )
        }) {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Column {
                DualLazyWindow(
                    navController = navController,
                    leftButtonText = "Tasks",
                    rightButtonText = "favourite Tasks",
                    leftLazyItems = tasks,
                    rightLazyItems = tasks,
                    leftIcons = Icons.Outlined.Done,
                    rightIcons = Icons.Outlined.ArrowForward,
                    leftDestination = ActiveTask.route,
                    rightDestination = ActiveTask.route,
                    modifier = Modifier.padding(top = 10.dp)
                )
            }
        }
    }
}
