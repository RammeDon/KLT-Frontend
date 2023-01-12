package com.klt.screens

import android.content.ContentValues
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.NormalTextField
import com.klt.ui.navigation.ConfirmToken
import com.klt.ui.navigation.ResetPassword
import com.klt.util.ApiConnector
import com.klt.util.HttpStatus
import com.klt.util.LocalStorage
import com.klt.util.LocalStorageData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun ConfirmTokenScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    var inputToken by remember { mutableStateOf("") }
    var tokenExists by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current

    LaunchedEffect(tokenExists) {
        if (tokenExists) navController.navigate(ResetPassword.route)
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        NormalTextField(
            labelText = "Enter token here", title = "Token", modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            updateState = { inputToken = it }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {

                coroutine.launch(Dispatchers.IO) {
                    ApiConnector.mailTokenExists(inputToken, onRespond = {
                        val data: JSONObject = it.data()
                        val msg: String = data.get("msg") as String
                        when (it.status()) {
                            HttpStatus.SUCCESS -> {
                                tokenExists = true
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
                    })
                }


            }
        ) {
            Text("Confirm Token")
        }
        Spacer(modifier = Modifier.weight(1f))
    }
}