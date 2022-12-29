package com.klt.screens

import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.NormalTextField
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.navigation.Clients
import com.klt.ui.navigation.ForgotPassword
import com.klt.util.ApiConnector
import com.klt.util.ApiResult
import com.klt.util.HttpStatus.*
import com.klt.util.LocalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(modifier = modifier.then(Modifier.fillMaxSize()), contentAlignment = Alignment.Center) {
        val initialHorizontalPadding = 20.dp
        val context = LocalContext.current
        val coroutine = rememberCoroutineScope()
        Box(
            modifier = Modifier
                .padding(horizontal = initialHorizontalPadding)
                .padding(bottom = 56.dp) // push up to make up for TopBar
                .shadow(10.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFE9E9E9), shape = RoundedCornerShape(5.dp))
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .padding(horizontal = initialHorizontalPadding * 2)
            ) {
                Spacer(Modifier.padding(vertical = 8.dp))
                NormalTextField(labelText = "Username", title = "Username", forUsername = true)
                Spacer(Modifier.padding(vertical = 8.dp))
                PasswordTextField(title = "Password")

                // Auth state
                var auth by remember { mutableStateOf(Pair(false, "")) }

                // Called on response from login call
                val onLoginRespond: (ApiResult) -> Unit = {
                    val data: JSONObject = it.data()
                    val msg: String = data.get("msg") as String
                    when (it.status()) {
                        SUCCESS -> {
                            val token: String = data.get("token") as String
                            auth = Pair(true, token)
                        }
                        UNAUTHORIZED -> {
                            Looper.prepare()
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            Looper.loop()
                        }
                        FAILED -> {
                            Looper.prepare()
                            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                            Looper.loop()
                        }
                    }
                }

                LaunchedEffect(auth) {
                    launch {
                        if (auth.first) {
                            LocalStorage.saveToken(context, auth.second)
                            navController.navigate(Clients.route)
                        }
                    }
                }

                Button(
                    onClick = {
                        coroutine.launch(Dispatchers.IO) {
                            ApiConnector.login(
                                "Test@Test.com",
                                "TestTest#0",
                                onRespond = onLoginRespond
                            )
                        }
                    },
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .padding(top = 40.dp)
                        .scale(1.2f),
                ) {
                    Text(text = "Login", modifier = Modifier.padding(horizontal = 22.dp))
                }

                val text = "Forgot Password"
                ClickableText(
                    text = AnnotatedString(text),
                    onClick = { navController.navigate(ForgotPassword.route) },
                    modifier = Modifier
                        .padding(vertical = 18.dp)
                        .align(Alignment.CenterHorizontally)
                )
            }
        }
    }
}
