package com.klt.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.composables.ScreenSubTitle
import com.klt.util.*
import com.klt.util.ApiConnector.getUserData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.suspendCoroutine
import kotlin.math.max

@Suppress("SuspiciousVarProperty")
private class UserItem : IUser {
    override var id: String = ""
    override var lastName: String = ""
    override var firstName: String = ""
    override var name: String = ""
        get() = "$firstName $lastName"
    override var email: String = ""
    override var hasIcon: Boolean = false
}

@Suppress("LocalVariableName", "UnnecessaryVariable")
@OptIn(InternalComposeApi::class)
@Composable
fun UserControlScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        ScreenSubTitle(
            navController = navController,
            onBackNavigation = navController.previousBackStackEntry?.destination?.route!!,
            bigText = "Manage Existing Users",
            smallText = "Here you delete redundant accounts"
        )
        Spacer(
            modifier = Modifier.height(
                max(LocalConfiguration.current.screenHeightDp / 23, 37).dp
            )
        )
        val context = LocalContext.current
        val coroutine = rememberCoroutineScope()
        var haveFetchUsers by remember { mutableStateOf(false) }
        val allUsers = remember { mutableStateListOf<IUser>() }
        val scrollState = rememberLazyListState()
        val onFetchUsers: (ApiResult) -> Unit = {
            when (it.status()) {
                HttpStatus.SUCCESS -> {
                    val itemsArray = it.dataArray()
                    val ls = LocalStorage.getLocalStorageData(context)
                    var currUser = ""
                    getUserData(LocalStorage.getToken(context), onRespond = { user ->
                        val userObj = user.data()
                        currUser = userObj.get("_id") as String
                    })
                    Log.d(TAG, currUser)
                    for (i in 0 until itemsArray.length()) {
                        val item = itemsArray.getJSONObject(i)
                        val c = UserItem()
                        c.id = item.getString("_id")
                        if (currUser != c.id) {
                            c.firstName = item.getString("firstName")
                            c.lastName = item.getString("lastName")
                            c.email = item.getString("email")
                            allUsers.add(c)
                        }
                    }
                    haveFetchUsers = true
                }
                HttpStatus.UNAUTHORIZED -> {
                    Looper.prepare()
                    Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }
                HttpStatus.FAILED -> {
                    Looper.prepare()
                    Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show()
                    Looper.loop()
                }
            }
        }

        LaunchedEffect(allUsers) {
            if (!haveFetchUsers) {
                coroutine.launch(Dispatchers.IO) {
                    ApiConnector.getAllUserData(
                        token = LocalStorage.getToken(context),
                        onRespond = onFetchUsers
                    )
                }
            }
        }

        LazyColumn(state = scrollState) {
            items(items = allUsers, key = { user -> user.id }) {
                var isDeleted by remember {
                    mutableStateOf(true)
                }
                val KLT_Red = colorResource(id = R.color.KLT_Red)

                Row {
                    Button(
                        modifier = Modifier
                            .padding(horizontal = 15.dp)
                            .width((LocalConfiguration.current.screenWidthDp / 1.5).dp)
                            .fillMaxHeight(),
                        onClick = { /* Disabled */ },
                        colors = ButtonDefaults.buttonColors(
                            backgroundColor = Color.LightGray
                        ),
                        shape = RoundedCornerShape(5.dp),
                        elevation = null,
                        enabled = false,
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
                                            start = Offset(-(50f), (size.height * 1.5).toFloat()),
                                            end = Offset(-(50f), -(size.height * 1.5).toFloat()),
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
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(end = 20.dp)
                    ) {
                        var confirmDelete by remember {
                            mutableStateOf(false)
                        }

                        IconButton(onClick = {
                            if (!confirmDelete) confirmDelete = true
                            else coroutine.launch(Dispatchers.IO) {
                                isDeleted = true
                                allUsers.remove(it)
                                deleteUser(it, context)
                            }
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Delete,
                                contentDescription = "Delete Customer",
                                tint = if (confirmDelete) Color.Green else Color.Black,
                                modifier = Modifier.scale(if (confirmDelete) 1.2f else 1f)
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        IconButton(onClick = {
                            confirmDelete = false
                        }) {
                            Icon(
                                imageVector = Icons.Rounded.Add,
                                contentDescription = "Cancel Delete",
                                tint = if (confirmDelete) Color.Red else Color.DarkGray,
                                modifier = Modifier
                                    .scale(if (confirmDelete) 1.2f else 1f)
                                    .rotate(45f),
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
            }
        }
    }
}

suspend fun deleteUser(user: IUser, context: Context) {
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

        ApiConnector.deleteUser(
            token = LocalStorage.getToken(context),
            userId = user.id,
            onRespond = onUserEditRespond
        )
    }
}
