package com.klt.ui.composables

import android.util.Log
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
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
import com.klt.screens.ActiveTaskState
import com.klt.util.ITask

@Composable
fun OnTaskDone(
    modifier: Modifier = Modifier,
    task: ITask,
    activeTaskState: ActiveTaskState,
    timeTaken: String,
    onTaskReportDone: () -> Unit
) {

    val orderNumber = if (activeTaskState.orderNumber == "") "No order number" else activeTaskState.orderNumber
    var btnEnabled: Boolean by remember { mutableStateOf(false) }

    fun isAllFieldPopulated(): Boolean {
        val enabled = true
        for (g in task.goals) {
            if (g.value == null || g.value == ""){
                return false
            }
        }

        return enabled
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .wrapContentSize()
            .then(modifier)) {

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            fontSize = 40.sp,
            text = "Task done!"
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 12.sp,
            text = "Order number: $orderNumber"
        )

        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            color = Color.LightGray,
            fontSize = 12.sp,
            text = "Task time: $timeTaken"
        )

        Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 20).dp))

        task.goals.map { thisGoal ->
            GoalInput(goal = thisGoal, onUpdate = {
                thisGoal.value = it
                btnEnabled = isAllFieldPopulated()
            })
        }

        Button(
            enabled = btnEnabled,
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.KLT_Red)
            ),
            modifier = Modifier
                .padding(vertical = 30.dp)
                .fillMaxWidth(),
            onClick = onTaskReportDone)
        {
            Text(
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                text = "Confirm"
            )
        }
    }
}