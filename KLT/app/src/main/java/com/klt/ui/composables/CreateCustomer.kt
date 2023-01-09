package com.klt.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R
import com.klt.util.ApiConnector
import com.klt.util.ApiResult
import com.klt.util.HttpStatus
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateCustomer(
    BottomSheetStateCurrent: BottomSheetState,
    onSubmit: (String) -> Unit = {}
) {
    var clientName by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()

    var alertState by remember { mutableStateOf(FormAlertMsgState.NOT_ACTIVE) }
    var alertMsg by remember { mutableStateOf("") }

    fun updateAlert(msg: String, state: FormAlertMsgState) {
        alertState = state
        alertMsg = msg
    }

    val onCreateCustomer: (ApiResult) -> Unit = {
        val data: JSONObject = it.data()
        val msg: String = data.get("msg") as String

        when (it.status()) {
            HttpStatus.SUCCESS -> {
                updateAlert(msg, FormAlertMsgState.GOOD)
                onSubmit(clientName)
            }
            HttpStatus.UNAUTHORIZED -> updateAlert(msg, FormAlertMsgState.BAD)
            HttpStatus.FAILED -> updateAlert(msg, FormAlertMsgState.BAD)
        }
    }

    Text(text = "Customer name")
    Spacer(modifier = Modifier.height(5.dp))
    OutlinedTextField(
        value = clientName,
        onValueChange = { clientName = it },
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = "Customer Name...",
                color = colorResource(id = R.color.KLT_DarkGray1)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
            unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
        ),
        singleLine = true
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                coroutine.launch(Dispatchers.IO) {
                    ApiConnector.createCustomer(
                        name = clientName,
                        onRespond = onCreateCustomer
                    )
                    BottomSheetStateCurrent.collapse()
                    clientName = ""
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.KLT_Red)),
        ) {
            Text(text = "Create New Customer", color = Color.White)
        }
    }
}
