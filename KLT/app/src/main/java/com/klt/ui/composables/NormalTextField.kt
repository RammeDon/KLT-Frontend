package com.klt.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun NormalTextField(labelText: String, horizontalPadding: Dp = 15.dp, title: String = "") {
    Column {
        if (title != "")
            TextFieldTitle(title)

        var stateValue: String by remember {
            mutableStateOf("")
        }
        TextField(
            stateValue,
            label = { Text(labelText) },
            onValueChange = { if (it != " ") stateValue = it },
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 10.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
        )
    }
}
