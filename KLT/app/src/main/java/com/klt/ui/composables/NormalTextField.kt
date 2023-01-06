package com.klt.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

var username: String? = null

@Composable
fun NormalTextField(
    modifier: Modifier = Modifier,
    labelText: String, horizontalPadding: Dp = 15.dp,
    title: String = "",
    singleLine: Boolean = true,
    forUsername: Boolean = false,
    updateState: (String) -> Unit = {}
) {
    Column {
        if (title != "")
            TextFieldTitle(title)
        var stateValue: String by remember {
            mutableStateOf("")
        }
        TextField(
            stateValue,
            label = { Text(labelText) },
            onValueChange = {
                stateValue = it
                updateState(it)
                if (forUsername) username = stateValue
            },
            modifier = Modifier
                .padding(bottom = 10.dp)
                .then(modifier),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            singleLine = singleLine
        )


    }
}
