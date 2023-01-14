package com.klt.ui.composables

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R
import com.klt.util.ApiConnector
import com.klt.util.ApiResult
import com.klt.util.HttpStatus
import com.klt.util.LocalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun CreateUserForm(
    modifier: Modifier = Modifier,
    context: Context = LocalContext.current
) {
    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
            .then(modifier)
    ) {


        val coroutineScope = rememberCoroutineScope()
        var email by remember { mutableStateOf("") }
        var firstName by remember { mutableStateOf("") }
        var lastName by remember { mutableStateOf("") }
        var phoneNumber by remember { mutableStateOf("") }
        var alertState by remember { mutableStateOf(FormAlertMsgState.NOT_ACTIVE) }
        var alertMsg by remember { mutableStateOf("") }


        fun updateAlert(msg: String, state: FormAlertMsgState) {
            alertState = state
            alertMsg = msg
        }


        val onCreateAccount: (ApiResult) -> Unit = {

            val data: JSONObject = it.data()

            val msg: String = data.get("msg") as String

            when (it.status()) {
                HttpStatus.SUCCESS -> {
                    email = ""
                    firstName = ""
                    lastName = ""
                    phoneNumber = ""
                    updateAlert(msg, FormAlertMsgState.GOOD)
                }
                HttpStatus.UNAUTHORIZED -> updateAlert(msg, FormAlertMsgState.BAD)
                HttpStatus.FAILED -> updateAlert(msg, FormAlertMsgState.BAD)
            }
        }


        // Date container
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                FormAlertMsg(msg = alertMsg, state = alertState)
            }

            TextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            TextField(
                value = phoneNumber,
                onValueChange = { phoneNumber = it },
                label = { Text("phone number..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            TextField(
                value = firstName,
                onValueChange = { firstName = it },
                label = { Text("First Name..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            TextField(
                value = lastName,
                onValueChange = { lastName = it },
                label = { Text("Last Name..") },
                singleLine = true,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            Button(
                onClick = {
                    coroutineScope.launch(Dispatchers.IO) {
                        ApiConnector.createAccount(
                            LocalStorage.getToken(context),     // TODO: Add token
                            email,
                            firstName,
                            lastName,
                            phoneNumber,
                            onCreateAccount
                        )
                    }

                },
                modifier = Modifier
                    .fillMaxWidth()
                    .align(alignment = Alignment.CenterHorizontally)
                    .padding(vertical = 10.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.KLT_Red)
                )
            ) {
                Text(
                    color = Color.White,
                    text = "Create Account",
                    modifier = Modifier.padding(
                        vertical = 10.dp,
                        horizontal = 22.dp
                    )
                )
            }
        }
    }
}
