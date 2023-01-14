package com.klt.screens

import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.composables.isMatching
import com.klt.ui.composables.pwContainer
import com.klt.ui.navigation.Login
import com.klt.ui.navigation.User
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    /*TODO -- add screen contents here */
    var newPassword by remember { mutableStateOf("") }
    var updated by remember { mutableStateOf(false) }
    val coroutine = rememberCoroutineScope()
    val context = LocalContext.current
    val authToken = LocalStorage.getToken(context)

    LaunchedEffect(updated) {
        if (updated && authToken == "") {
            navController.navigate(Login.route)
        }
        else if (updated) {
            navController.navigate(User.route)
        }
    }

    val onResetPassword: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String
        when (it.status()) {
            HttpStatus.SUCCESS -> {
                updated = true
                LocalStorage.saveTokenEmail(context, "")
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
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 12.dp)
            .padding(vertical = 70.dp)
        ,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "name-Icon"
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                PasswordTextField(
                    labelText = "New Password",
                    checkPasswordStrength = true,
                    onUpdate = {newPassword = it}
                )
            }
        }

        Spacer(modifier = Modifier.height(15.dp))
        Box(modifier = Modifier.padding(horizontal = 20.dp)) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(80.dp)
                    .background(
                        color = Color.LightGray,
                        shape = RoundedCornerShape(5.dp)
                    )
                    .padding(start = 15.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(top = 15.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.Lock,
                        contentDescription = "name-Icon"
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                PasswordTextField(
                    labelText = "Confirm new password",
                    performMatchCheck = true,
                    onUpdate = {}
                )
            }
        }


        /**val newPW = PasswordTextField(labelText = "New Password", checkPasswordStrength = true, onUpdate = {newPassword = it})
        val confirmPW =
            PasswordTextField(labelText = "Confirm new password", performMatchCheck = true, onUpdate = {})*/
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onClick = {

                coroutine.launch(Dispatchers.IO) {
                    if (isMatching && pwContainer != HashUtils.sha256("")) {
                        if (authToken  == "" ) {
                            ApiConnector.forgotPassword(newPassword,
                                LocalStorage.getETokenEmail(context),
                                onRespond = onResetPassword)
                        } else {
                            ApiConnector.changePassword(authToken, newPassword, onResetPassword)
                        }

                    }
                }

            }
        ) {
            Text("Change Password")
        }
        Spacer(modifier = Modifier.weight(5f))

    }

}
