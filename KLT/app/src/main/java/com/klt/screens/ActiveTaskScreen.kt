package com.klt.screens


import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import com.klt.ui.composables.*
import com.klt.ui.navigation.Clients

import com.klt.ui.navigation.Tasks
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.util.*
import java.util.concurrent.TimeUnit


// TEMPORARY DATA - THIS ARE IMPORTED USING COMPOSE PARAMETER
class g1() : ITask.IGoal {
    override val name: String = "If something broke, what?"
    override var value: Any? = null
    override val unit: String = ""
    override val type: ITask.GoalDataTypes = ITask.GoalDataTypes.Text
}

class g2() : ITask.IGoal {
    override val name: String = "How many pallets was moved"
    override var value: Any? = null
    override val unit: String = "ST"
    override val type: ITask.GoalDataTypes = ITask.GoalDataTypes.Number
}

class g3() : ITask.IGoal {
    override val name: String = "How many stickers was placed"
    override var value: Any? = null
    override val unit: String = "ST"
    override val type: ITask.GoalDataTypes = ITask.GoalDataTypes.Number
}

class g4() : ITask.IGoal {
    override val name: String = "Did anything break?"
    override var value: Any? = null
    override val unit: String = ""
    override val type: ITask.GoalDataTypes = ITask.GoalDataTypes.Boolean
}

object ThisTask : ITask {
    override val id: String = "63a06af777a75cb3d428e52b"
    override var taskName: String = "Move Boxes"
    override val goals: Array<ITask.IGoal> = arrayOf(g2(), g3(), g4(), g1())
    override val requireOrderNumber: Boolean = true
    override val completedAtLeastOnceToday: Boolean = false
}

/** Enum for defining this views states */
enum class TaskViewState() {
    ORDER_NUMBER,
    TASK,
    DEVIATION,
    COMPLETE
}



class ActiveTaskState: Serializable {
    @SerializedName("taskId")
    var id: String = ""
    @SerializedName("orderNumber")
    var orderNumber: String = ""
    @SerializedName("start")
    var start: String = ""
    @SerializedName("end")
    var end: String = ""
    @SerializedName("pauses")
    var pauses: MutableList<Pause> = mutableListOf()
    @SerializedName("timeSummary")
    var timeSummary: Long = 0L
    @SerializedName("goals")
    var goals: MutableList<Goals> = mutableListOf()

    class Pause: Serializable {
        @SerializedName("start")
        var start: String = ""
        @SerializedName("end")
        var end: String = ""
        @SerializedName("reason")
        var reason: String = ""
    }

    class Goals: Serializable {
        @SerializedName("name")
        var name: String = ""
        @SerializedName("value")
        var value: Any = ""
        @SerializedName("unit")
        var unit: String = ""
        @SerializedName("dataType")
        var dataTypes: String = ""
    }
}

@Composable
fun ActiveTaskScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit,
) {

    val task: ITask = ThisTask // TODO: ADD as an parameters


    // Check if this task is already active
    var initState: ActiveTaskState? = null
    var localStorageData = LocalStorage.getLocalStorageData(context)
    for (t in localStorageData.activeTasks) {
        if (t.id == task.id) {
            initState = t
            break
        }
    }

    fun getActiveTaskState(): ActiveTaskState? {
        localStorageData = LocalStorage.getLocalStorageData(context)
        for (t in localStorageData.activeTasks) {
            if (t.id == task.id) {
                return t
            }
        }
        return null
    }

    fun areWeInPause(): Boolean {
        val activeTaskState = getActiveTaskState()
        return (
            activeTaskState != null &&
            activeTaskState.pauses.isNotEmpty() &&
            activeTaskState.pauses.last().end == ""
        )
    }

    fun summarizeTimeElapsed(endTime: LocalDateTime): Long {
        val activeTask: ActiveTaskState = getActiveTaskState() ?: return 0L
        val startTime = LocalDateTime.parse(activeTask.start)
        val elapsed = Duration.between(startTime, endTime).toMillis()
        var totalTimePaused = 0L
        for (pause in activeTask.pauses) {
            val pauseStart = LocalDateTime.parse(pause.start)
            val pauseEnd: LocalDateTime = if (pause.end == "") endTime else LocalDateTime.parse(pause.end)
            val pauseDuration = Duration.between(pauseStart, pauseEnd)
            totalTimePaused += pauseDuration.toMillis()
        }
        return  elapsed - totalTimePaused
    }

    fun getUpdateClock(): Long {
        return if (initState != null) {

            var timeFrom: LocalDateTime = LocalDateTime.now()

            if (areWeInPause()) {
                timeFrom = LocalDateTime.parse(getActiveTaskState()!!.pauses.last().start)
            }

            summarizeTimeElapsed(timeFrom)
        } else 0
    }



    // Variables for this composable
    var taskActive by remember { mutableStateOf(initState != null) }
    var sliderValue by remember { mutableStateOf(if(initState != null) 1.0F else 0.0F) }
    var taskPaused by remember { mutableStateOf(areWeInPause()) }
    var finalTimeElapsed by remember { mutableStateOf(0L) }
    var orderNumber by remember { mutableStateOf(getActiveTaskState()?.orderNumber ?: "") }
    var state: TaskViewState by remember { mutableStateOf(
        if (task.requireOrderNumber && orderNumber == "") TaskViewState.ORDER_NUMBER else TaskViewState.TASK
    )}
    var timeElapsed by remember { mutableStateOf(getUpdateClock()) }


    LaunchedEffect(
        key1 = timeElapsed,
        key2 = taskActive,
        key3 = taskPaused,
    ) {

        if (taskActive && !taskPaused) {
            delay(100L)
            timeElapsed= getUpdateClock()

        }

    }

    /** Function that is called when Swipe finished to the right */
    fun onStartTask() {
        sliderValue = 1F
        taskActive = true

        // Add this task to local storage as active
        val newActiveTask: ActiveTaskState = ActiveTaskState()
        newActiveTask.id = task.id
        newActiveTask.orderNumber = orderNumber
        newActiveTask.start = LocalDateTime.now().toString()
        localStorageData = LocalStorage.getLocalStorageData(context)
        localStorageData.activeTasks.add(newActiveTask)
        LocalStorage.saveLocalStorageData(context, localStorageData)
    }

    /** Function that is called when Swipe finished to the left */
    fun onStopTask() {
        state = TaskViewState.COMPLETE
        finalTimeElapsed = timeElapsed
        val activeTaskState = getActiveTaskState()!!
        activeTaskState.end = LocalDateTime.now().toString()
    }

    /** On send task entry respond */
    val onTaskEntryRespond: (ApiResult) -> Unit = { apiResult ->
        val data: JSONObject = apiResult.data()
        val msg: String = data.get("msg") as String
        when (apiResult.status()) {
            HttpStatus.SUCCESS -> {
                sliderValue = 0F
                taskActive = false
                timeElapsed = 0
                finalTimeElapsed = 0
                taskPaused = false
                state = TaskViewState.TASK
                localStorageData = LocalStorage.getLocalStorageData(context)
                localStorageData.activeTasks.removeAll { it.id == task.id }
                LocalStorage.saveLocalStorageData(context, localStorageData)
            }
            HttpStatus.UNAUTHORIZED -> {

            }
            HttpStatus.FAILED -> {

            }
        }
    }

    fun onTaskDone() {

        val taskEntry = getActiveTaskState()!!
        var timeFrom: LocalDateTime = LocalDateTime.now()
        if (areWeInPause()) {
            timeFrom = LocalDateTime.parse(getActiveTaskState()!!.pauses.last().start)
        }
        taskEntry.end = timeFrom.toString()
        taskEntry.timeSummary = finalTimeElapsed

        for (g in task.goals) {
            val goal = ActiveTaskState.Goals()
            goal.name = g.name
            goal.value = g.value!!
            goal.unit = g.unit
            goal.dataTypes = g.type.name
            taskEntry.goals.add(goal)
        }

        ApiConnector.sendTaskEntry(
            token = LocalStorage.getToken(context),
            jsonData = Gson().toJson(taskEntry),
            onRespond = onTaskEntryRespond
        )
    }


    /** Function that is called when "Pause" Button is pressed */
    fun onPauseTask() {
        if (!taskPaused) {
            state = TaskViewState.DEVIATION
        } else {
            val activeTaskState = getActiveTaskState()!!
            activeTaskState.pauses.last().end = LocalDateTime.now().toString()
            LocalStorage.saveLocalStorageData(context, localStorageData)
        }
        taskPaused = !taskPaused
    }

    /** Function that is called when the deviation was confirmed */
    fun onDeviation(deviation: String) {
        state = TaskViewState.TASK
        timeElapsed = getUpdateClock()
        val pause: ActiveTaskState.Pause = ActiveTaskState.Pause()
        pause.start = LocalDateTime.now().toString()
        pause.reason = deviation
        // store state to local storage
        val activeTaskState = getActiveTaskState()!!
        activeTaskState.pauses.add(pause)
        LocalStorage.saveLocalStorageData(context, localStorageData)
    }

    fun getTimeElapsedAsString(): String {
        val sec = timeElapsed / 1000L
        val hours = TimeUnit.SECONDS.toHours(sec)
        val minutes = TimeUnit.SECONDS.toMinutes(sec) % TimeUnit.HOURS.toMinutes(1)
        val secs = TimeUnit.SECONDS.toSeconds(sec) % TimeUnit.MINUTES.toSeconds(1)
        return String.format("%02d:%02d.%02d", hours, minutes, secs)
    }

    // The Layout -------------------------------------
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            ScreenSubTitle(
                navController = navController,
                onBackNavigation = Tasks.route,
                bigText = "Task: " + task.taskName,
                smallText = "taskCompletedSubHeaderString()"
            )

            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp /15).dp))

            when (state) {
                TaskViewState.ORDER_NUMBER -> {
                    OneLineInputForm(
                        text = "This task requires an order number",
                        onConfirm = {
                            orderNumber = it
                            state = TaskViewState.TASK
                        },
                        placeholder = "Order number..",
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )
                }
                TaskViewState.TASK -> {

                    Column {
                        TaskTimer(
                            taskActive = taskActive,
                            taskPaused = taskPaused,
                            elapsedTime = getTimeElapsedAsString(),
                            onStartTask = { onStartTask() },
                            onStopTask = { onStopTask() },
                            onPauseTask = { onPauseTask() },
                            sliderValue = sliderValue
                        )
                    }
                }
                TaskViewState.DEVIATION -> { DeviationForm( onConfirm = {onDeviation(it)} ) }
                TaskViewState.COMPLETE -> {
                    val coroutine = rememberCoroutineScope()
                    OnTaskDone(
                        task = task,
                        onTaskReportDone = {
                            coroutine.launch(Dispatchers.IO) {
                                onTaskDone()
                            }
                            navController.navigate(Clients.route)
                        },
                        activeTaskState = getActiveTaskState()!!,
                        timeTaken = getTimeElapsedAsString(),
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )}
            }


            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 20).dp))
        }
    }

}
