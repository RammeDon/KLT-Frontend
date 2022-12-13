package com.klt.ui.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

@Composable
fun PasswordTextField(
    labelText: String = "Password",
    semanticContentDescription: String = "",
    hasError: Boolean = false,
    title: String = ""
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    var pwStateValue: String by remember {
        mutableStateOf("")
    }
    if (title != "")
        TextFieldTitle(title)

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = semanticContentDescription }
            .padding(horizontal = 12.dp),
        value = pwStateValue,
        label = { Text(labelText) },
        onValueChange = { if (it != " ") pwStateValue = it },
        keyboardOptions = KeyboardOptions.Default.copy(
            autoCorrect = true,
            keyboardType = KeyboardType.Text,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(
            onDone = {
                focusManager.clearFocus()
            }
        ),
        singleLine = true,
        isError = hasError,
        visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
    )
}
