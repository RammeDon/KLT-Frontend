package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klt.R
import com.klt.util.TimeManager

@Composable
fun TaskTimer(
    modifier: Modifier = Modifier,
    taskActive: Boolean,
    taskPaused: Boolean,
    sliderValue: Float,
    elapsedTime: String,
    onStartTask: () -> Unit,
    onStopTask: () -> Unit,
    onPauseTask: () -> Unit
) {

    var localSliderValue by remember { mutableStateOf(sliderValue) }

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
                text = elapsedTime,
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
                value = localSliderValue,
                colors = SliderDefaults.colors(
                    thumbColor = Color.Red,
                    activeTrackColor = Color.Red
                ),
                valueRange = 0f..1f,
                onValueChange = { localSliderValue = it },
                onValueChangeFinished = {
                    localSliderValue = if (localSliderValue < 0.5F) 0.0F else 1.0F
                    if (localSliderValue == 1.0F) onStartTask()
                    else onStopTask()
                }
            )



            Button(
                onClick = { onPauseTask() },
                modifier = Modifier
                    .width(200.dp)
                    .align(alignment = Alignment.CenterHorizontally)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.KLT_Red)
                ),
                enabled = taskActive
            ) {
                Text(
                    color = Color.White,
                    text = if (taskPaused) "Resume" else "Pause",
                    modifier = Modifier.padding(horizontal = 22.dp)
                )
            }
        }
    }
}