package com.klt.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.content.res.Configuration
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import androidx.navigation.NavController
import com.google.gson.Gson
import com.klt.R
import com.klt.ui.composables.CreateCustomer
import com.klt.ui.composables.NormalTextField
import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.navigation.Customers
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine
import kotlin.math.max


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CustomerControlScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {

        val context = LocalContext.current
        val allCustomers = remember { mutableStateListOf<ICustomer>() }
        val coroutine = rememberCoroutineScope()
        var haveFetchCustomers by remember { mutableStateOf(false) }
        val scrollState = rememberLazyListState()
        var editingList = remember { mutableListOf<String>() }

        val onFetchTasks: (ApiResult) -> Unit = {
            when (it.status()) {
                HttpStatus.SUCCESS -> {
                    val data = it.data()
                    val itemsArray = data.getJSONArray("customers")
                    for (i in 0 until itemsArray.length()) {
                        val item = itemsArray.getJSONObject(i)
                        val c = CustomerItem()
                        c.id = item.getString("_id")
                        c.name = item.getString("name")
                        allCustomers.add(c)
                    }
                    haveFetchCustomers = true
                }
                HttpStatus.UNAUTHORIZED -> {
                    Looper.prepare()
                    Toast.makeText(context, "UNAUTHORIZED", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }
                HttpStatus.FAILED -> {
                    Looper.prepare()
                    Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
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
                ScreenSubTitle(
                    navController = navController,
                    onBackNavigation = navController.previousBackStackEntry?.destination?.route
                        ?: Customers.route,
                    bigText = "Manage Existing Customers",
                    smallText = "Here you add, change or delete customers"
                )
                Spacer(
                    modifier = Modifier.height(
                        max(LocalConfiguration.current.screenHeightDp / 23, 37).dp
                    )
                )
            },
            content = {
                Row(
                    horizontalArrangement = Arrangement.SpaceEvenly
                ) {
                    IconButton(
                        onClick = {
                            coroutine.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                    ) {
                        Icon(Icons.Default.Add, contentDescription = "")
                    }
                    ClickableText(
                        text = AnnotatedString("Add Customers"),
                        modifier = Modifier.padding(top = 14.dp),
                        onClick = {
                            coroutine.launch {
                                scaffoldState.bottomSheetState.expand()
                            }
                        },
                    )
                    Spacer(modifier = Modifier.weight(1f))
                }

                LazyColumn(state = scrollState) {
                    items(items = allCustomers, key = { customer -> customer.id }) {
                        var nameVal by remember {
                            mutableStateOf("")
                        }
                        var isEditing by remember {
                            mutableStateOf(false)
                        }
                        var isDeleted by remember {
                            mutableStateOf(false)
                        }
                        val KLT_Red = colorResource(id = R.color.KLT_Red)
                        var deleteButtonClicked by remember {
                            mutableStateOf(false)
                        }

                        LaunchedEffect(isDeleted) {
                            if (isDeleted) {
                                allCustomers.remove(it)
                                launch(Dispatchers.IO) {
                                    deleteCustomer(it, context)
                                }
                            }
                        }

                        Row {
                            if (deleteButtonClicked) {
                                OpenDialogue(
                                    context = context,
                                    customer = it,
                                    OnResponse = { result ->
                                        isDeleted = result
                                        if (!result) {
                                            deleteButtonClicked = false
                                        }
                                    })
                            }
                            if (!isEditing) {
                                Button(
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                        .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                                        .fillMaxHeight()
                                        .then(modifier),
                                    onClick = { /* Disabled.*/ },
                                    colors = ButtonDefaults.buttonColors(
                                        backgroundColor = Color.LightGray
                                    ),
                                    shape = RoundedCornerShape(5.dp),
                                    elevation = null,
                                    enabled = false
                                ) {
                                    Row(
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .then(modifier)
                                            .background(Color.Transparent)
                                    ) {
                                        Column(
                                            modifier = Modifier
                                                .fillMaxHeight()
                                                .drawBehind {
                                                    val borderSize = 4.dp.toPx()
                                                    drawLine(
                                                        color = KLT_Red,
                                                        start = Offset(
                                                            -(50f),
                                                            (size.height * 1.5).toFloat()
                                                        ),
                                                        end = Offset(
                                                            -(50f),
                                                            -(size.height * 1.5).toFloat()
                                                        ),
                                                        strokeWidth = borderSize
                                                    )
                                                },
                                            verticalArrangement = Arrangement.Center
                                        ) {
                                            Text(
                                                text = it.name, color = Color.Black,
                                                fontWeight = FontWeight.Normal,
                                                fontSize = 14.sp
                                            )
                                        }
                                    }
                                }
                            } else {
                                NormalTextField(
                                    labelText = it.name,
                                    modifier = Modifier
                                        .padding(horizontal = 15.dp)
                                        .padding(top = 8.dp)
                                        .fillMaxHeight()
                                        .width((LocalConfiguration.current.screenWidthDp / 1.5).dp),
                                    updateState = { input -> nameVal = input }
                                )
                            }
                            var confirmDelete by remember {
                                mutableStateOf(deleteButtonClicked)
                            }
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(end = 20.dp)
                            ) {
                                var clicked by remember {
                                    mutableStateOf(false)
                                }
                                IconButton(onClick = {
                                    coroutine.launch(Dispatchers.IO) {
                                        if (clicked && nameVal != "") {
                                            it.name = nameVal
                                            isEditing = false
                                            clicked = false
                                            updateCustomer(it, context)
                                        } else {
                                            clicked = !clicked
                                            isEditing = !isEditing
                                        }
                                    }
                                }) {
                                    Icon(
                                        imageVector = if (!clicked)
                                            Icons.Rounded.Edit else Icons.Rounded.Check,
                                        contentDescription = "Edit Customer",
                                        tint = if (!clicked) Color.Black else Color.Green
                                    )
                                }
                                IconButton(onClick = {
                                    if (confirmDelete) {
                                        deleteButtonClicked = true
                                        confirmDelete = false
                                    } else confirmDelete = true
                                }) {
                                    Icon(
                                        imageVector = Icons.Rounded.Delete,
                                        contentDescription = "Delete Customer",
                                        tint = if (confirmDelete) Color.Red else Color.Black,
                                        modifier = Modifier.scale(if (confirmDelete) 1.2f else 1f),
                                    )
                                }
                                Spacer(modifier = Modifier.weight(1f))

                                if (clicked) {
                                    editingList.add(it.id)
                                } else {
                                    editingList.remove(it.id)
                                }
                                isEditing = clicked
                            }
                        }
                        Spacer(Modifier.height(5.dp))
                    }
                }
            },
            sheetContent = {
                com.klt.drawers.BottomDrawer(content = {
                    CreateCustomer(BottomSheetStateCurrent = sheetState, onSubmit = {
                        val buildCustomerByName: (ApiResult) -> Unit = { apiResult ->
                            when (apiResult.status()) {
                                HttpStatus.SUCCESS -> {
                                    Log.d(TAG, apiResult.toString())
                                    val data = apiResult.data()
                                    val item = data.getJSONObject("customer")
                                    val c = CustomerItem()
                                    c.id = item.getString("_id")
                                    c.name = item.getString("name")
                                    allCustomers.add(0, c)
                                }
                                HttpStatus.UNAUTHORIZED -> {
                                    Looper.prepare()
                                    Toast.makeText(context, "UNAUTHORIZED", Toast.LENGTH_SHORT)
                                        .show()
                                    Looper.loop()
                                }
                                HttpStatus.FAILED -> {
                                    Looper.prepare()
                                    Toast.makeText(context, "FAILED", Toast.LENGTH_SHORT).show()
                                    Looper.loop()
                                }
                            }
                        }
                        ApiConnector.getCustomerByName(
                            token = LocalStorage.getToken(context),
                            customerName = it,
                            onRespond = buildCustomerByName
                        )
                    })
                })
            })
    }
}


fun updateCustomer(customer: ICustomer, context: Context) {
    val onCustomerEditRespond: (ApiResult) -> Unit = { apiResult ->
        when (apiResult.status()) {
            HttpStatus.SUCCESS -> {
                Looper.prepare()
                Toast.makeText(context, "Edit is successful!", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
            HttpStatus.UNAUTHORIZED -> {
                Looper.prepare()
                Toast.makeText(context, "Error: Unauthorized", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
            HttpStatus.FAILED -> {
                Looper.prepare()
                Toast.makeText(context, "Error: Operation failed!", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }
    }

    ApiConnector.editCustomer(
        token = LocalStorage.getToken(context),
        jsonData = Gson().toJson(customer),
        onRespond = onCustomerEditRespond
    )
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun OpenDialogue(context: Context, customer: ICustomer, OnResponse: (Boolean) -> Unit) {
    val dialogueState = remember { mutableStateOf(true) }
    val onDismiss: (Boolean) -> Unit = {
        OnResponse(it)
        dialogueState.value = false  // button has been clicked
    }

    if (dialogueState.value) {
        Dialog(
            onDismissRequest = { onDismiss(false) }, properties = DialogProperties(
                usePlatformDefaultWidth = false
            )
        ) {
            Card(
                elevation = 5.dp,
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .border(
                        1.dp,
                        color = colorResource(id = R.color.KLT_DarkGray1),
                        shape = RoundedCornerShape(8.dp)
                    )
            ) {
                Column(modifier = Modifier.padding(12.dp)) {
                    Icon(
                        imageVector = Icons.Rounded.Warning,
                        contentDescription = "Warning!",
                        tint = Color.Red,
                        modifier = Modifier
                            .scale(3f)
                            .padding(top = 10.dp)
                            .align(Alignment.CenterHorizontally)
                    )
                    Spacer(Modifier.height(50.dp))
                    Text(
                        "Deleting a customer will delete all customer tasks and metric " +
                                "history.",
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                    Spacer(Modifier.height(25.dp))
                    Text("Are you sure you want to delete ${customer.name}?")
                    Spacer(modifier = Modifier.height(20.dp))
                    Divider()
                    Row(horizontalArrangement = Arrangement.Center) {
                        Button(
                            onClick = {
                                onDismiss(true)
                            },
                            colors = ButtonDefaults
                                .buttonColors(
                                    backgroundColor = colorResource(id = R.color.KLT_Red),
                                    contentColor = Color.White
                                ),
                            elevation = null
                        ) {
                            Text(text = "Confirm")
                        }
                        Spacer(modifier = Modifier.weight(5f))
                        ClickableText(
                            text = AnnotatedString("Cancel"),
                            onClick = { onDismiss(false) },
                            modifier = Modifier
                                .scale(
                                    if (context.resources.configuration.orientation == Configuration
                                            .ORIENTATION_PORTRAIT
                                    ) 1f else 0.8f
                                )
                                .align(Alignment.CenterVertically),
                        )
                        Spacer(modifier = Modifier.weight(0.5f))
                    }
                }
            }
        }
    }

}

suspend fun deleteCustomer(customer: ICustomer, context: Context) {
    return suspendCoroutine { _ ->
        val onUserEditRespond: (ApiResult) -> Unit = { apiResult ->
            Looper.prepare()
            when (apiResult.status()) {
                HttpStatus.SUCCESS -> {
                    Toast.makeText(context, "Delete successful!", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }
                HttpStatus.UNAUTHORIZED -> {
                    Toast.makeText(context, "Error: Unauthorized", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }
                HttpStatus.FAILED -> {
                    Toast.makeText(context, "Error: Failed", Toast.LENGTH_LONG).show()
                    Looper.loop()
                }
            }
        }

        ApiConnector.deleteCustomer(
            token = LocalStorage.getToken(context),
            customerId = customer.id,
            onRespond = onUserEditRespond
        )
    }
}
