package com.klt.screens

import android.content.res.Configuration
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.composables.NormalTextField
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.navigation.Customers
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
    val context = LocalContext.current
    Column(
        modifier = modifier
            .then(Modifier.fillMaxSize())
            .scale(
                if (context.resources.configuration.orientation == Configuration
                        .ORIENTATION_PORTRAIT
                ) 1f else 0.9f
            ),
        verticalArrangement = Arrangement.Center
    ) {
        val initialHorizontalPadding = 20.dp
        val coroutine = rememberCoroutineScope()
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        val horizontalScale = 0.8f
        Spacer(Modifier.weight(1f))
        Box(
            modifier = Modifier
                .padding(horizontal = LocalConfiguration.current.screenWidthDp.dp / 14)
                .shadow(10.dp, shape = RoundedCornerShape(10.dp))
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFE9E9E9), shape = RoundedCornerShape(5.dp))
                    .padding(
                        vertical = if (context.resources.configuration.orientation == Configuration
                                .ORIENTATION_PORTRAIT
                        ) 20.dp else 0.dp
                    )
                    .padding(
                        horizontal = initialHorizontalPadding * 2
                    )
            ) {
                Spacer(
                    Modifier
                        .padding(vertical = 8.dp)
                        .scale(
                            if (context.resources.configuration.orientation == Configuration
                                    .ORIENTATION_PORTRAIT
                            ) 1f else horizontalScale
                        )
                )
                NormalTextField(
                    labelText = "Email..",
                    title = "Email",
                    forUsername = true,
                    updateState = { email = it },
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(
                            if (context.resources.configuration.orientation == Configuration
                                    .ORIENTATION_PORTRAIT
                            ) 1f else horizontalScale
                        )
                )
                Spacer(
                    Modifier
                        .padding(
                            if (context.resources.configuration.orientation == Configuration
                                    .ORIENTATION_PORTRAIT
                            ) 8.dp else 0.dp
                        )
                )
                PasswordTextField(
                    title = "Password",
                    onUpdate = { password = it },
                    modifier = Modifier.scale(
                        if (context.resources.configuration.orientation == Configuration
                                .ORIENTATION_PORTRAIT
                        ) 1f else horizontalScale

                    )
                )

                // Auth state
                var auth by remember { mutableStateOf(Pair(false, "")) }
                var isAdmin by remember { mutableStateOf(false) }

                // Called on response from login call
                val onLoginRespond: (ApiResult) -> Unit = {
                    val data: JSONObject = it.data()
                    val msg: String = data.get("msg") as String
                    when (it.status()) {
                        SUCCESS -> {
                            val token: String = data.get("token") as String
                            val admin: Boolean = data.get("isAdmin") as Boolean
                            auth = Pair(true, token)
                            isAdmin = admin
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
                            LocalStorage.saveIsAdmin(context, "$isAdmin")
                            navController.navigate(Customers.route)
                        }
                    }
                }

                Button(
                    onClick = {
                        coroutine.launch(Dispatchers.IO) {
                            ApiConnector.login(
                                email,
                                password,
                                onRespond = onLoginRespond
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally)
                        .padding(
                            if (context.resources.configuration.orientation == Configuration
                                    .ORIENTATION_PORTRAIT
                            ) 30.dp else 5.dp
                        )
                        .scale(
                            if (context.resources.configuration.orientation == Configuration
                                    .ORIENTATION_PORTRAIT
                            ) 1f else 0.5f
                        ),
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = colorResource(id = R.color.KLT_Red)
                    )
                ) {
                    Text(
                        color = Color.White,
                        text = "Login",
                        modifier = Modifier
                            .padding(
                                horizontal = 22.dp,
                                vertical = 8.dp
                            ),
                        fontSize = if (context.resources.configuration.orientation == Configuration
                                .ORIENTATION_PORTRAIT
                        ) 15.sp else (15 * 2).sp
                    )
                }

                val text = "Forgot Password"
                ClickableText(
                    text = AnnotatedString(text),
                    onClick = { navController.navigate(ForgotPassword.route) },
                    modifier = Modifier
//                        .padding(bottom = 5.dp)
                        .align(Alignment.CenterHorizontally)
                        .scale(
                            if (context.resources.configuration.orientation == Configuration
                                    .ORIENTATION_PORTRAIT
                            ) 1f else 0.8f
                        ),
                )
            }
        }
        Spacer(Modifier.weight(1f))
        Spacer(
            Modifier.height(
                if (context.resources.configuration.orientation == Configuration
                        .ORIENTATION_PORTRAIT
                ) 30.dp else 0.dp
            )
        )
    }
}
