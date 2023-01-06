package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.navigation.NavController
import com.klt.ui.composables.BarGraph
import com.klt.ui.composables.LazyWindow
import com.klt.ui.navigation.ClientStatistics
import com.klt.util.ApiConnector
import com.klt.util.ApiResult



@Composable
fun StatisticsScreen(
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

            LazyWindow(
                navController = navController,
                destination = ClientStatistics.route,
                items = listOfClients,
                color = Color.LightGray
            )
            Spacer(modifier = Modifier.weight(1f))
            BarGraph(
                dataset = listOf(403,2634,1643,54312,65423,2643),
                variablename = "Clients",
                variables = listOf("Client A", "Client B", "Client C", "Client D", "Client E", "Client F", )
            )
            Spacer(modifier = Modifier.weight(1f))
        }

    }
}
/*Display Checklist:
* box for each client
*   Bar graph of total time spent on jobs for client
*   Bar graph (or other graph format) on time spent per job
*   Thought: could we make a sort of timeline showing when jobs were worked on? Full line when actively worked on, dashed when paused
*   List of suggestions/emerging problems?
*
* Requirements:
*   • create admin with following features: visually present the information about clients and time it took for the jobs.
*   • create client with features that visualize the job done. The visualize is based on day, time and if there were any problems, and suggestions for further work.
* */