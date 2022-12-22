package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.drawers.BottomDrawer
import com.klt.ui.composables.CreateClientComposable
import com.klt.ui.composables.DualLazyWindow
import com.klt.ui.composables.KLTDivider
import com.klt.ui.navigation.Tasks
import com.klt.util.ApiConnector.getAllCustomers
import com.klt.util.ApiResult
import com.klt.util.ItemType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray

object CustomerSelected {
    var name = ""
    var id = ""
    var hasIcon = false
}

class CustomerOBJ{
    val firstName: String = ""
    val lastName: String = ""
    val email: String = ""
}

interface KLTItem {
    val name: String
    val id: String
    val hasIcon: Boolean
}

interface Customer : KLTItem //inherits all abstract properties from KLTItem
interface Task : KLTItem { // inherits all abstracts properties from KLTItem + specify some new ones
    val timeTaken: Long
    val timePaused: Long
    override val hasIcon: Boolean // force default true
        get() = true
}

// Client -> Client Interface -> KLT Item interface
private object Customer1 : Customer {
    // only needs to override values specified in super parent (KLTItem)
    override val name = "KLT Internal Client Example"
    override val id = "BABAGOCLIENT"
    override val hasIcon = true
}

private object Customer2 : Customer {
    // only needs to override values specified in super parent (KLTItem)
    override val name = "Krinova Client Example"
    override val id = "BABAGOCLIENT2"
    override val hasIcon = true
}

// Task -> Task Interface -> KLT Item interface
private object Task1 : Task {
    // needs to override values specified in super parent (KLTItem)
    override val name = "KLT Internal Task Example"
    override val id = "BABAGOTASK"

    // also needs to override values specified in direct parent (Task)
    override val timeTaken = 1L
    override val timePaused = 2L
}


private object Task2 : Task {
    // needs to override values specified in super parent (KLTItem)
    override val name = "Krinova Task Example"
    override val id = "BABAGOTASK2"

    // also needs to override values specified in direct parent (Task)
    override val timeTaken = 1L
    override val timePaused = 2L
}

val listOfClients = listOf(Customer1, Customer2)
val listOfTasks = listOf(Task1, Task2)

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val coroutine = rememberCoroutineScope()
    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
    val empty = JSONArray()
    var customers by remember { mutableStateOf(empty) }

    val onGetAllCustomers: (ApiResult) -> Unit ={
        customers = it.arrayData()
    }
    LaunchedEffect(customers) {
        coroutine.launch(Dispatchers.IO) {
            getAllCustomers(
                onRespond = { onGetAllCustomers(it) }
            )
        }
    }

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetGesturesEnabled = scaffoldState.bottomSheetState.isExpanded,
        sheetPeekHeight = 0.dp,
        topBar = {
            Column(verticalArrangement = Arrangement.SpaceEvenly) {
                Text(
                    text = "Clients: Select a client",
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 8.dp),
                    textAlign = TextAlign.Center
                )
                KLTDivider()
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    Spacer(modifier = Modifier.weight(1f))
                    ClickableText(
                        text = AnnotatedString("Add Client"),
                        modifier = Modifier.padding(top = 14.dp),
                        onClick = {
                            coroutine.launch(Dispatchers.IO) {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                    )
                    IconButton(
                        onClick = {
                            coroutine.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                        modifier = Modifier.padding(end = 30.dp)
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "")
                    }
                }

            }

        },
        sheetContent = {
            BottomDrawer(content = { CreateClientComposable(BottomSheetStateCurrent = sheetState) })

        }) {
        Box(
            modifier = Modifier
                .padding(horizontal = 20.dp)
        ) {
            Column(modifier = Modifier) {
                
                DualLazyWindow(
                    navController = navController,
                    leftButtonText = "Unpinned",
                    rightButtonText = "Pinned",
                    leftLazyItems = customers,
                    rightLazyItems = customers,
                    rightIcons = Icons.Outlined.PushPin,
                    leftDestination = Tasks.route,
                    rightDestination = Tasks.route,
                    itemType = ItemType.CLIENT
                )
            }
        }
    }
}
