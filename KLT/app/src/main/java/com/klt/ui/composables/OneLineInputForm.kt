package com.klt.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R

@Composable
fun OneLineInputForm(
    modifier: Modifier = Modifier,
    text: String,
    placeholder: String,
    onConfirm: (String) -> Unit
) {

    var data: String by remember { mutableStateOf("") }

    Column(horizontalAlignment = Alignment.CenterHorizontally, modifier = Modifier.then(modifier)) {

        Text(text = text)

        TextField(
            value = data,
            onValueChange = {data = it},
            singleLine = true,
            placeholder = { Text(text = placeholder) },
            modifier = Modifier
                .padding(vertical = 10.dp)
                .padding(top = 20.dp)
                .fillMaxWidth()
        )

        Button(
            enabled = (data.isNotEmpty()),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = colorResource(id = R.color.KLT_Red)
            ),
            modifier = Modifier
                .padding(vertical = 10.dp)
                .fillMaxWidth(),
            onClick = { onConfirm(data) })
        {
            Text(
                modifier = Modifier.padding(10.dp),
                color = Color.White,
                text = "Confirm"
            )
        }
    }
}
