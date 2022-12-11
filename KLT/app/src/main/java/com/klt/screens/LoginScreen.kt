package com.klt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.navigation.Home

val backgroundColor = Color.White


@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    var username: String by remember {
        mutableStateOf("")
    }
    Box(modifier = modifier.then(Modifier.fillMaxSize()), contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier
                .background(
                    Color(0xFFE9E9E9)
                )
                .fillMaxWidth()
        ) {
            Spacer(Modifier.padding(vertical = 8.dp))
            Text(text = "Username", modifier = Modifier.padding(horizontal = 160.dp))
            TextField(
                username,
                label = { Text("Username") },
                onValueChange = { if (it != " ") username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(Modifier.padding(vertical = 8.dp))

            Text(text = "Password", modifier = Modifier.padding(horizontal = 160.dp))

            var pw: String by remember {
                mutableStateOf("")
            }

            PasswordTextField(
                text = pw,
                hasError = false,
                onTextChanged = { newVal: String -> pw = newVal }
            )

            Button(
                onClick = { navController.navigate(Home.route) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 15.dp)
            ) {
                Text(text = "Login")
            }
            var state by remember {
                mutableStateOf(false)
            }

            val text = "Forgot Password state: $state"
            ClickableText(
                text = AnnotatedString(text),
                onClick = { state = !state },
                modifier = Modifier
                    .padding(vertical = 18.dp)
                    .align(Alignment.CenterHorizontally)
            )

        }
    }
}


@Composable
fun PasswordTextField(
    text: String,
    semanticContentDescription: String = "",
    hasError: Boolean = false,
    onTextChanged: (text: String) -> Unit,
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .semantics { contentDescription = semanticContentDescription }
            .padding(horizontal = 12.dp),
        value = text,
        label = { Text("Password") },
        onValueChange = onTextChanged,
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
