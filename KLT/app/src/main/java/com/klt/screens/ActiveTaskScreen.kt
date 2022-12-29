package com.klt.screens


import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.gson.annotations.SerializedName
import com.klt.ui.composables.OneLineInputForm
import com.klt.ui.composables.DeviationForm

import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.composables.TaskTimer
import com.klt.ui.navigation.Tasks
import com.klt.util.ITask
import com.klt.util.LocalStorage
import kotlinx.coroutines.delay
import java.io.Serializable
import java.time.Duration
import java.time.LocalDateTime
import java.util.Date
import java.util.concurrent.TimeUnit


object ThisTask : ITask {
    override val id: String = "1"
    override var taskName: String = "Move Boxes"
    override val goals: Array<ITask.IGoal> = emptyArray()
    override val requireOrderNumber: Boolean = true
    override val completedAtLeastOnceToday: Boolean = false
}

data class TaskTimestamp(
    val start: Date,
    val end: Date,
    val comment: String
)

enum class TaskViewState() {
    ORDER_NUMBER,
    TASK,
    DEVIATION,
    COMPLETE
}

class ActiveTaskState: Serializable {
    @SerializedName("id")
    var id: String = ""
    @SerializedName("orderNumber")
    var orderNumber: String = ""
    @SerializedName("start")
    var start: String = ""
    @SerializedName("end")
    var end: String = ""
    @SerializedName("pauses")
    var pauses: MutableList<Pause> = mutableListOf()

    class Pause: Serializable {
        @SerializedName("start")
        var start: String = ""
        @SerializedName("end")
        var end: String = ""
        @SerializedName("reason")
        var reason: String = ""
    }
}

@Composable
fun ActiveTaskScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
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
        sliderValue = 0F
        taskActive = false
        timeElapsed = 0
        taskPaused = false
        // remove this task to local storage as active
        localStorageData = LocalStorage.getLocalStorageData(context)
        localStorageData.activeTasks.removeAll { it.id == task.id }
        LocalStorage.saveLocalStorageData(context, localStorageData)
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
        return String.format("%02d:%02d:%02d", hours, minutes, secs)
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

            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 10).dp))

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
                TaskViewState.COMPLETE -> TODO()
            }


            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 20).dp))
        }
    }
}
