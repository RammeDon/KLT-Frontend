package com.klt.screens

import android.content.Context
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Check
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.klt.ui.composables.NormalTextField
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject


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
        val allCustomers = remember { mutableStateListOf<IKLTItem>() }
        val coroutine = rememberCoroutineScope()
        var haveFetchCustomers by remember { mutableStateOf(false) }
        val pinnedCustomers = remember { mutableStateListOf<IKLTItem>() }
        val scrollState = rememberLazyListState()
        var editOn: Boolean by remember {
            mutableStateOf(false)
        }
        var editingList = remember { mutableListOf<String>() }

        val onFetchTasks: (ApiResult) -> Unit = {
            val data: JSONObject = it.data()
            val msg: String = data.get("msg") as String
            when (it.status()) {
                HttpStatus.SUCCESS -> {
                    val itemsArray = data.getJSONArray("customers")
                    val ls = LocalStorage.getLocalStorageData(context)
                    for (i in 0 until itemsArray.length()) {
                        val item = itemsArray.getJSONObject(i)
                        val c = CustomerItem()
                        c.id = item.getString("_id")
                        c.name = item.getString("name")
                        c.pinned = ls.pinnedCustomers.contains(c.id)
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

        LazyColumn(state = scrollState) {
            items(items = allCustomers, key = { customer -> customer.id }) {
                var nameVal by remember {
                    mutableStateOf("")
                }
                var isEditing by remember {
                    mutableStateOf(false)
                }
                Row {
                    if (!isEditing) {
                        Button(
                            modifier = Modifier
                                .padding(horizontal = 15.dp)
                                .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                                .fillMaxHeight()
                                .then(modifier),
                            onClick = { /*No.*/ },
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.LightGray
                            ),
                            shape = RoundedCornerShape(5.dp),
                            elevation = null,
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .then(modifier)
                                    .background(Color.Transparent)
                            ) {
                                Column(
                                    modifier = Modifier.fillMaxHeight(),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 20.dp)
                    ) {
                        var clicked by remember {
                            mutableStateOf(false)
                        }
                        IconButton(onClick = {
                            coroutine.launch {
                                if (clicked && nameVal != "") {
                                    it.name = nameVal
                                    updateCustomer(it, context)
                                }
                                clicked = !clicked
                            }
                        }) {
                            Icon(
                                imageVector = if (!clicked)
                                    Icons.Rounded.Edit else Icons.Rounded.Check,
                                contentDescription = "Edit Customer",
                                tint = if (!clicked) Color.Black else Color.Green
                            )
                        }
                        IconButton(onClick = { /*TODO*/ }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete Customer"
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
    }
}

fun updateCustomer(customer: IKLTItem, context: Context) {
    val onCustomerEditRespond: (ApiResult) -> Unit = { apiResult ->
        when (apiResult.status()) {
            HttpStatus.SUCCESS -> {
                Toast.makeText(context, "Edit is successful!", Toast.LENGTH_SHORT).show()
            }
            HttpStatus.UNAUTHORIZED -> {
                Toast.makeText(context, "Error: Unauthorized", Toast.LENGTH_SHORT).show()
            }
            HttpStatus.FAILED -> {
                Toast.makeText(context, "Error: Operation failed!", Toast.LENGTH_LONG).show()
            }
        }
    }

    ApiConnector.updateCustomer(
        token = LocalStorage.getToken(context),
        customerId = customer.id,
        jsonData = Gson().toJson(customer),
        onRespond = onCustomerEditRespond
    )
}
