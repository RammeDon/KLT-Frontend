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
import androidx.compose.ui.unit.dp
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

/* Item used to populate the goals */
private class GoalItem: ITask.IGoal {
    override var name: String = ""
    override var value: Any? = null
    override var unit: String = ""
    override var type: ITask.GoalDataTypes = ITask.GoalDataTypes.Text
}

/* Item used to populate the task list */
private class TaskItem: ITask {
    override var taskName: String = ""
    override var goals: MutableList<ITask.IGoal> = mutableListOf()
    override var requireOrderNumber: Boolean = false
    override var id: String = ""
    override var pinned: Boolean = false
}



@OptIn(ExperimentalMaterialApi::class)
@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun TaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current,
    customer: ICustomer = Tasks.customer!!,
    OnSelfClick: () -> Unit = {},
) {



    val coroutine = rememberCoroutineScope()
    var haveFetchTasks by remember { mutableStateOf(false) }
    val allTasks = remember { mutableStateListOf<IKLTItem>() }
    val pinnedTasks = remember { mutableStateListOf<IKLTItem>() }

    val onFetchTasks: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val itemsArray = data.getJSONArray("tasks")
                val ls = LocalStorage.getLocalStorageData(context)
                for (i in 0 until itemsArray.length()) {
                    val item = itemsArray.getJSONObject(i)
                    val t = TaskItem()
                    val goalsArr = item.getJSONArray("goals")
                    for (j in 0 until goalsArr.length()) {
                        val goal = goalsArr.getJSONObject(j)
                        val g = GoalItem()
                        g.name = goal.getString("name")
                        g.type = ITask.GoalDataTypes.valueOf(goal.getString("type"))
                        g.unit = goal.getString("unit")
                        Log.d("KLT_API_CONNECTOR", g.name)
                        t.goals.add(g)
                    }

                    t.id = item.getString("_id")
                    t.taskName = item.getString("name")
                    t.requireOrderNumber = item.getBoolean("requireOrderNumber")
                    t.pinned = ls.pinnedTasks.contains(t.id)
                    allTasks.add(t)
                    if (t.pinned) pinnedTasks.add(t)
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

    // On pin task
    val onPin: (item: IKLTItem?) -> Unit = {
        if (it != null && it is ITask){
            val ls = LocalStorage.getLocalStorageData(context)
            if (!it.pinned) {
                pinnedTasks.add(it)
                ls.pinnedTasks.add(it.id)
                it.pinned = true
            } else {
                pinnedTasks.remove(it)
                ls.pinnedTasks.remove(it.id)
                it.pinned = false
            }
            LocalStorage.saveLocalStorageData(context, ls)

            // Force re-render
            allTasks.add(it)
            allTasks.removeLast()
        }
    }

    // Fetch task's
    LaunchedEffect(allTasks) {
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
                    bigText = customer.name,
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
                content = { CreateTaskComposable(
                    BottomSheetStateCurrent = sheetState,
                    customer = customer,
                    onTaskCreated = {
                        allTasks.clear()
                        pinnedTasks.clear()
                        ApiConnector.getTasksFromCustomer(
                            token = LocalStorage.getToken(context),
                            customerId = customer.id,
                            onRespond = onFetchTasks
                        )
                    }
                ) }
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
                    leftLazyItems = allTasks,
                    rightLazyItems = pinnedTasks,
                    leftIcons = Icons.Outlined.Done,
                    rightIcons = Icons.Outlined.ArrowForward,
                    leftDestination = ActiveTask.route,
                    rightDestination = ActiveTask.route,
                    modifier = Modifier.padding(top = 10.dp),
                    job = onPin
                )
            }
        }
    }
}
