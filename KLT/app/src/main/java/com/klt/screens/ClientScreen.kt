package com.klt.screens

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.outlined.PushPin
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
import com.klt.ui.composables.CreateClientComposable
import com.klt.ui.composables.DualLazyWindow
import com.klt.ui.composables.KLTDivider
import com.klt.ui.navigation.Tasks
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

/* Item used to populate the customer list */
private class CustomerItem: ICustomer {
    override var id: String = "-1"
    override val hasIcon: Boolean = false
    override var name: String = "NAME"
    override var pinned: Boolean = false
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ClientScreen(
    navController: NavController,
    context: Context = LocalContext.current,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {


    val coroutine = rememberCoroutineScope()
    var haveFetchCustomers by remember { mutableStateOf(false) }
    val allCustomers = remember { mutableStateListOf<IKLTItem>() }
    val pinnedCustomers = remember { mutableStateListOf<IKLTItem>() }

    val onFetchTasks: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                val itemsArray = data.getJSONArray("customers")
                for (i in 0 until itemsArray.length()) {
                    val item = itemsArray.getJSONObject(i)
                    val c = CustomerItem()
                    c.id = item.getString("_id")
                    c.name = item.getString("name")
                    c.pinned = false // TODO: Get from backend
                    allCustomers.add(c)
                    if (c.pinned) pinnedCustomers.add(c)
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
                    onRespond = onFetchTasks
                )
            }
        }


    }

    val sheetState = rememberBottomSheetState(initialValue = BottomSheetValue.Collapsed)
    val scaffoldState = rememberBottomSheetScaffoldState(bottomSheetState = sheetState)
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
                            coroutine.launch {
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
                    leftLazyItems = allCustomers,
                    rightLazyItems = pinnedCustomers,
                    rightIcons = Icons.Outlined.PushPin,
                    leftDestination = Tasks.route,
                    rightDestination = Tasks.route
                )
            }

        }
    }


}
