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
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CreateClientComposable(
    BottomSheetStateCurrent: BottomSheetState
) {
    var clientName by remember { mutableStateOf("") }
    val coroutine = rememberCoroutineScope()


    Text(text = "Client name")
    Spacer(modifier = Modifier.height(5.dp))
    OutlinedTextField(
        value = clientName,
        onValueChange = { clientName = it },
        modifier = Modifier
            .height(50.dp)
            .fillMaxWidth(),
        placeholder = {
            Text(
                text = "Client Name...",
                color = colorResource(id = R.color.KLT_DarkGray1)
            )
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = colorResource(id = R.color.KLT_DarkGray1),
            unfocusedBorderColor = colorResource(id = R.color.KLT_DarkGray2)
        )
    )
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = {
                coroutine.launch {
                    BottomSheetStateCurrent.collapse()
                }
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.KLT_Red)),
        ) {
            Text(text = "Create New Client", color = Color.White)

        }
    }
}