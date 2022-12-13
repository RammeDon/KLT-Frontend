package com.klt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.navigation.ForgotPassword
import com.klt.ui.navigation.Home


@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(modifier = modifier.then(Modifier.fillMaxSize()), contentAlignment = Alignment.Center) {
        val textGenerator: @Composable (String) -> Unit = {
            Text(
                text = it,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 5.dp)
            )
        }

        Column(
            modifier = Modifier
                .background(
                    Color(0xFFE9E9E9)
                )
                .fillMaxWidth()
        ) {
            Spacer(Modifier.padding(vertical = 8.dp))

            textGenerator("Username")

            var username: String by remember {
                mutableStateOf("")
            }
            TextField(
                username,
                label = { Text("Username") },
                onValueChange = { if (it != " ") username = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 15.dp)
                    .padding(bottom = 10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text)
            )

            Spacer(Modifier.padding(vertical = 8.dp))

            textGenerator("Password")

            var pw: String by remember {
                mutableStateOf("")
            }

            PasswordTextField(
                initText = pw,
                hasError = false,
                onTextChanged = { newVal: String -> pw = newVal }
            )

            Button(
                onClick = { navController.navigate(Home.route) },
                modifier = Modifier
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 40.dp)
                    .scale(1.2f)
            ) {
                Text(text = "Login", modifier = Modifier.padding(horizontal = 22.dp))
            }


            val text = "Forgot Password"
            ClickableText(
                text = AnnotatedString(text),
                onClick = { navController.navigate(ForgotPassword.route) },
                modifier = Modifier
                    .padding(vertical = 18.dp)
                    .align(Alignment.CenterHorizontally)
            )
        }
    }
}
