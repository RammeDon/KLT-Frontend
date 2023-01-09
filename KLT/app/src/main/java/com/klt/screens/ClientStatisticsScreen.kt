package com.klt.screens

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
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
import com.klt.ui.composables.BarGraph
import com.klt.ui.composables.KLTDivider
import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.navigation.ClientStatistics.customer
import com.klt.ui.navigation.Customers
import com.klt.ui.navigation.Statistics
import com.klt.ui.navigation.Tasks
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientStatisticsScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    customer: ICustomer = Tasks.customer!!,
    OnSelfClick: () -> Unit = {}
) {
/*    val coroutine = rememberCoroutineScope()
    var haveFetchStats by remember { mutableStateOf(false) }
    var haveFetchMon by remember { mutableStateOf(false) }
    var haveFetchTues by remember { mutableStateOf(false) }
    var haveFetchWed by remember { mutableStateOf(false) }
    var haveFetchThur by remember { mutableStateOf(false) }
    var haveFetchFri by remember { mutableStateOf(false) }
    var haveFetchSat by remember { mutableStateOf(false) }
    var haveFetchSun by remember { mutableStateOf(false) }
    val weekdayTimes = remember {mutableStateListOf<Long>()}
    val TaskTimes = remember {mutableStateListOf<Long>()}
    val idList = remember {mutableStateListOf<String>()}
    val Tasks = remember {mutableStateListOf<String>()}
    val pauseReasons = remember {mutableStateListOf<String>()}
    val onFetchStats: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                for (i in 0 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val id = item.getString("_id")
                    if (idList.contains(id)) {
                        val index = idList.indexOf(id)
                        val newTime = TaskTimes[index] + item.getLong("timeSpentOnTask")
                        TaskTimes.removeAt(index)
                        TaskTimes.add(index, newTime)
                    } else {
                        idList.add(id)
                        Tasks.add(item.getString("task"))
                        TaskTimes.add(item.getLong("timeSpentOnTask"))
                    }
                    val pause = item.getJSONArray("pauses")
                    val task = item.getString("task")
                    for (i in 0 until pause.length()) {
                        val reason = pause.getString(1)
                        pauseReasons.add(task + ", " + reason)
                    }
                }
                haveFetchStats = true
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

    LaunchedEffect(TaskTimes) {
        if (!haveFetchStats) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-02T00:00:00.000000",
                    endDate = "2023-01-09T00:00:00.000000",
                    onRespond = onFetchStats
                )
            }
        }
    }

    val onFetchMon: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(0, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[0] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(0)
                    weekdayTimes.add(0,total)
                }
                haveFetchMon = true
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

    val onFetchTues: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(1, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[1] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(1)
                    weekdayTimes.add(1,total)
                }
                haveFetchTues = true
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

    val onFetchWed: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(2, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[2] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(2)
                    weekdayTimes.add(2,total)
                }
                haveFetchWed = true
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

    val onFetchThur: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(3, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[3] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(3)
                    weekdayTimes.add(3,total)
                }
                haveFetchThur = true
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

    val onFetchFri: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(4, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[4] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(4)
                    weekdayTimes.add(4,total)
                }
                haveFetchFri = true
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

    val onFetchSat: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(5, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[5] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(5)
                    weekdayTimes.add(5,total)
                }
                haveFetchSat = true
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

    val onFetchSun: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val DataArray = data.getJSONArray("data")
                weekdayTimes.add(6, DataArray.getJSONObject(0).getLong("timeSpentOnTask"))
                for (i in 1 until DataArray.length()) {
                    val item = DataArray.getJSONObject(i)
                    val total = weekdayTimes[6] + item.getLong("timeSpentOnTask")
                    weekdayTimes.removeAt(6)
                    weekdayTimes.add(6,total)
                }
                haveFetchSun = true
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

    LaunchedEffect(weekdayTimes) {
        if (!haveFetchMon) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-02T00:00:00.000000",
                    endDate = "2023-01-03T00:00:00.000000",
                    onRespond = onFetchMon
                )
            }
        }
        if (!haveFetchTues) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-03T00:00:00.000000",
                    endDate = "2023-01-04T00:00:00.000000",
                    onRespond = onFetchTues
                )
            }
        }
        if (!haveFetchWed) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-04T00:00:00.000000",
                    endDate = "2023-01-05T00:00:00.000000",
                    onRespond = onFetchWed
                )
            }
        }
        if (!haveFetchThur) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-05T00:00:00.000000",
                    endDate = "2023-01-06T00:00:00.000000",
                    onRespond = onFetchThur
                )
            }
        }
        if (!haveFetchFri) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-06T00:00:00.000000",
                    endDate = "2023-01-07T00:00:00.000000",
                    onRespond = onFetchFri
                )
            }
        }
        if (!haveFetchSat) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-07T00:00:00.000000",
                    endDate = "2023-01-08T00:00:00.000000",
                    onRespond = onFetchSat
                )
            }
        }
        if (!haveFetchSun) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-08T00:00:00.000000",
                    endDate = "2023-01-09T00:00:00.000000",
                    onRespond = onFetchSun
                )
            }
        }
    }
*/


    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {

        //need:
        //Time spent on tasks for client per weekday
        //Time spent on tasks generally
        //Tasks
        //pause notes

        /*TODO */
        // template column

        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            ScreenSubTitle(
                navController = navController,
                onBackNavigation = Statistics.route,
                bigText = customer.name,
                smallText = ""
            )
            KLTDivider()
            
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                //Bar graph for amount of time spent on client jobs over a week
                item {
                    Box(modifier = Modifier) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Time spent on tasks for the client over a week",
                            textAlign = TextAlign.Center
                        )
//                        BarGraph(dataset = weekdayTimes, variablename = "Weekday", variables = listOf("Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"))

                    }
                }

                //Bar graph for amount of time spent on jobs
                item {
                    Box(modifier = Modifier) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Time spent on each Task",
                            textAlign = TextAlign.Center
                        )
//                        BarGraph(dataset = TaskTimes, variablename = "Tasks", variables = Tasks)
                    }
                }

                //Suggestions and emerging problem
                item {
                    Box(modifier = Modifier) {
                        Text(
                            modifier = Modifier.fillMaxWidth(),
                            text = "Reasons for Pausing of Tasks",
                            textAlign = TextAlign.Center
                        )
  //                      LazyColumn( modifier = Modifier) {
  //                          items(pauseReasons) {
  //                                  pauseReason -> Text(text = pauseReason)
  //                          }
                        }
                    }
                    
                }

            }
        }

    }

