package com.klt.screens


import androidx.compose.foundation.layout.*
import androidx.compose.material.Slider
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.OneLineInputForm
import com.klt.ui.composables.DeviationForm

import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.composables.TaskTimer
import com.klt.ui.navigation.Tasks
import com.klt.util.ITask
import kotlinx.coroutines.delay
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

/** Data class for storing the data to create an task entry */
data class TaskEntryData(
    var orderNumber: String = "",
    var TaskTimestamp: MutableList<TaskTimestamp> = mutableListOf()
)

enum class TaskViewState() {
    ORDER_NUMBER,
    TASK,
    DEVIATION,
    COMPLETE
}

@Composable
fun ActiveTaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {


    // Variables for this composable
    var sliderValue by remember { mutableStateOf(0F) }
    var taskActive by remember { mutableStateOf(false) }
    var taskPaused by remember { mutableStateOf(false) }
    var timeElapsed by remember { mutableStateOf(0L) }


    val taskEntryData = TaskEntryData()
    val task: ITask = ThisTask // TODO: ADD as an parameters
    var state: TaskViewState by remember { mutableStateOf(
        if (task.requireOrderNumber) TaskViewState.ORDER_NUMBER else TaskViewState.TASK
    )}

    LaunchedEffect(
        key1 = timeElapsed,
        key2 = taskActive,
        key3 = taskPaused,
    ) {

        if (taskActive && !taskPaused) {
            delay(100L)
            timeElapsed += 100L
        }
    }

    /** Function that is called when Swipe finished to the right */
    fun onStartTask() {
        sliderValue = 1F
        taskActive = true
    }

    /** Function that is called when Swipe finished to the left */
    fun onStopTask() {
        sliderValue = 0F
        taskActive = false
    }

    /** Function that is called when "Pause" Button is pressed */
    fun onPauseTask() {
        if (!taskPaused) state = TaskViewState.DEVIATION
        taskPaused = !taskPaused
    }

    /** Function that is called when the deviation was confirmed */
    fun onDeviation(deviation: String) {
        state = TaskViewState.TASK
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
                            taskEntryData.orderNumber = it
                            state = TaskViewState.TASK
                        },
                        placeholder = "Order number..",
                        modifier = Modifier.padding(horizontal = 40.dp)
                    )
                }
                TaskViewState.TASK -> {
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
                TaskViewState.DEVIATION -> { DeviationForm( onConfirm = {onDeviation(it)} ) }
                TaskViewState.COMPLETE -> TODO()
            }


            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 20).dp))
        }
    }
}
