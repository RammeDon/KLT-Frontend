package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.klt.ui.composables.BarGraph


@Composable
fun ClientStatisticsScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        /*TODO */
        // template column
        Column(modifier = Modifier.fillMaxSize()) {
            Spacer(modifier = Modifier.weight(1f))
            //Bar graph for amount of time spent on client jobs over a week
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Time spent on tasks for the client over a week",
                textAlign = TextAlign.Center
            )
            BarGraph(dataset = listOf(534,8137,41237,87432,9285,2138,92438), variablename = "Weekday", variables = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"))
            //Bar graph for amount of time spent on jobs
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Time spent on each Task",
                textAlign = TextAlign.Center
            )
            BarGraph(dataset = listOf(7485,534,987,7465,15432), variablename = "Tasks", variables = listOf("Task A", "Task B", "Task C", "Task D", "Task E"))
            //Suggestions and emerging problem
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = "Time spent on each Task",
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.weight(1f))
        }
    }
}
