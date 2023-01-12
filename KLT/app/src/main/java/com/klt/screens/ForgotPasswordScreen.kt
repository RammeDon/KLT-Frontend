package com.klt.screens

import android.content.ContentValues.TAG
import android.content.Context
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
import kotlinx.coroutines.AbstractCoroutine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.wait
import org.json.JSONObject


@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var success by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()


    LaunchedEffect(success) {
        if (success) navController.navigate(ConfirmToken.route)
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        NormalTextField(
            labelText = "example@klt.se", title = "Email", modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            updateState = { email = it }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {
                coroutine.launch(Dispatchers.IO) {
                    ApiConnector.userExists(email, onRespond = {
                        val data: JSONObject = it.data()
                        val msg: String = data.get("msg") as String
                        when (it.status()) {
                            HttpStatus.SUCCESS -> {
                                LocalStorage.saveTokenEmail(context, email)
                                success = true
                                createMailToken(email, context)
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
            Text("Reset Password")
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}


private fun createMailToken(email: String, context: Context) {

    ApiConnector.createMailToken(email, onRespond = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                Looper.prepare()
                Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                Looper.loop()
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

