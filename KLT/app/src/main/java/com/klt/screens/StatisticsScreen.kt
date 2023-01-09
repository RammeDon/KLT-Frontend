package com.klt.screens

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.klt.ui.composables.LazyWindow
import com.klt.ui.navigation.ClientStatistics
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

private class Customer: ICustomer {
    override var id: String = "-1"
    override var hasIcon: Boolean = true
    override var name: String = "NAME"
    override var pinned: Boolean = false
}
@OptIn(ExperimentalMaterialApi::class)
@Composable
fun StatisticsScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val coroutine = rememberCoroutineScope()
    var haveFetchStats by remember { mutableStateOf(false) }
    var haveFetchCustomers by remember { mutableStateOf(false) }
    val idList = remember { mutableStateListOf<String>() }
    val listOfCustomers = remember { mutableStateListOf<String>() }
    val allCustomers = remember { mutableStateListOf<IKLTItem>() }
    val allTime = remember { mutableStateListOf<Long>() }

    val onFetchTasksA: (ApiResult) -> Unit = {
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
                        val newTime = allTime[index]+item.getLong("timeSpentOnTask")
                        allTime.removeAt(index)
                        allTime.add(index,newTime)

                    }else {
                        idList.add(id)
                        listOfCustomers.add(item.getString("customer"))
                        allTime.add(item.getLong("timeSpentOnTask"))
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

    LaunchedEffect(listOfCustomers) {
        if (!haveFetchStats) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-02T00:00:00.000000",
                    endDate = "2023-01-09T00:00:00.000000",
                    onRespond = onFetchTasksA
                )
            }
        }
    }

    val onFetchTasksB: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val itemsArray = data.getJSONArray("customers")
                val ls = LocalStorage.getLocalStorageData(context)
                for (i in 0 until itemsArray.length()) {
                    val item = itemsArray.getJSONObject(i)
                    val c = Customer()
                    c.id = item.getString("_id")
                    c.name = item.getString("name")
                    c.pinned = ls.pinnedCustomers.contains(c.id)
                    allCustomers.add(c)
                }
                haveFetchCustomers = true
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

    // Fetch customer's
    LaunchedEffect(allCustomers) {
        if (!haveFetchCustomers) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getAllCustomers(
                    token = LocalStorage.getToken(context),
                    onRespond = onFetchTasksB
                )
            }
        }
    }
    
    var haveFetchStatistics by remember { mutableStateOf(false) }
    var haveFetchMon by remember { mutableStateOf(false) }
    var haveFetchTues by remember { mutableStateOf(false) }
    var haveFetchWed by remember { mutableStateOf(false) }
    var haveFetchThur by remember { mutableStateOf(false) }
    var haveFetchFri by remember { mutableStateOf(false) }
    var haveFetchSat by remember { mutableStateOf(false) }
    var haveFetchSun by remember { mutableStateOf(false) }
    val weekdayTimes = remember {mutableStateListOf<Long>()}
    val TaskTimes = remember {mutableStateListOf<Long>()}
    val Tasks = remember {mutableStateListOf<String>()}
    val pauseReasons = remember {mutableStateListOf<String>()}
    val onFetchStatistics: (ApiResult) -> Unit = {
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
                haveFetchStatistics = true
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
        if (!haveFetchStatistics) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getStats(
                    token = LocalStorage.getToken(context),
                    startDate = "2023-01-02T00:00:00.000000",
                    endDate = "2023-01-09T00:00:00.000000",
                    onRespond = onFetchStatistics
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

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        /*TODO */
        // template column
        Column(verticalArrangement = Arrangement.SpaceEvenly) {
            Text(
                text = "Statistics: Select a client",
                fontSize = 26.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                textAlign = TextAlign.Center
            )
            KLTDivider()
            BarGraph(
                dataset = allTime,
                variablename = "Clients",
                variables = listOfCustomers
            )
            Box(modifier = Modifier) {
                LazyWindow(
                    navController = navController,
                    destination = ClientStatistics.route,
                    items = allCustomers,
                    color = Color.LightGray
                )
            }
            
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