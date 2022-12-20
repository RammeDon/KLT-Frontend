package com.klt.screens


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.Slider
import androidx.compose.material.SliderDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.navigation.Tasks
import com.klt.util.ITask
import com.klt.util.TimeManager
import kotlinx.coroutines.delay
import java.util.concurrent.TimeUnit


object ThisTask : ITask {
    override val id: String = "1"
    override var taskName: String = "Move Boxes"
    override val goals: Array<ITask.IGoal> = emptyArray()
    override val completedAtLeastOnceToday: Boolean = false
}


@Composable
fun ActiveTaskScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    // Variables for this composable

    val task: ITask = ThisTask //TODO: Add it as an Parameter
    var sliderValue by remember { mutableStateOf(0.0F) }
    var taskActive by remember { mutableStateOf(false) }
    var taskPaused by remember { mutableStateOf(false) }
    var timeElapsed by remember { mutableStateOf(0L) }


    LaunchedEffect(
        key1 = timeElapsed,
        key2 = taskActive,
        key3 = taskPaused
    ) {
        if (taskActive && !taskPaused) {
            delay(100L)
            timeElapsed += 100L
        }
    }

    fun taskCompletedSubHeaderString(): String {
        if (task.completedAtLeastOnceToday)
            return "This task has been completed least as of once today"
        return "This task has not yet been completed as of today"
    }

    /** Function that is called when Swipe finished to the right */
    fun onStartTask() {
        taskActive = true
    }

    /** Function that is called when Swipe finished to the left */
    fun onStopTask() {
        taskActive = false
    }

    /** Function that is called when "Pause" Button is pressed */
    fun onPauseTask() {
        taskPaused = !taskPaused
        // TODO: Add onPause Functionalities
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
                smallText = taskCompletedSubHeaderString()
            )

            Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 10).dp))

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .wrapContentSize()
            ) {

                // Date container
                Column(modifier = Modifier.wrapContentSize()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp,
                        text = TimeManager.getCurrentNameOfDayAsString()
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        text = TimeManager.getCurrentDateAsString()
                    )
                }

                Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 5).dp))

                // Time elapsed container
                Column(modifier = Modifier.wrapContentSize()) {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        fontSize = 12.sp,
                        text = if (!taskActive) "Task is not active" else if (taskPaused) "Task is Paused" else "Task is Active"
                    )

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = getTimeElapsedAsString(),
                        textAlign = TextAlign.Center,
                        fontSize = 40.sp
                    )
                }

                Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 20).dp))

                // Call to Action container
                Column {

                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = if (taskActive) "Slide to the left to end the task" else "Slide to the right to start the task",
                        textAlign = TextAlign.Center,
                        color = Color.LightGray,
                        fontSize = 12.sp
                    )

                    Slider(
                        modifier = Modifier
                            .width(200.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .background(Color.White),
                        value = sliderValue,
                        colors = SliderDefaults.colors(
                            thumbColor = Color.Red,
                            activeTrackColor = Color.Red
                        ),
                        valueRange = 0f..1f,
                        onValueChange = {
                            sliderValue = it
                        },
                        onValueChangeFinished = {
                            sliderValue = if (sliderValue < 0.5F) 0.0F else 1.0F
                            if (sliderValue == 1.0F) onStartTask()
                            else onStopTask()
                        }
                    )

                    Button(
                        onClick = { onPauseTask() },
                        modifier = Modifier
                            .width(200.dp)
                            .align(alignment = Alignment.CenterHorizontally)
                            .height(50.dp),
                        enabled = taskActive
                    ) {
                        Text(
                            text = if (taskPaused) "Resume" else "Pause",
                            modifier = Modifier.padding(horizontal = 22.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 20).dp))

            }
        }
    }
}
