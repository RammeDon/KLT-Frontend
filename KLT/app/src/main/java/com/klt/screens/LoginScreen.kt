package com.klt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.NormalTextField
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.navigation.Clients
import com.klt.ui.navigation.ForgotPassword


@Composable
fun LoginScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(modifier = modifier.then(Modifier.fillMaxSize()), contentAlignment = Alignment.Center) {
        val initialHorizontalPadding = 20.dp
        Box(
            modifier = Modifier
                .padding(horizontal = initialHorizontalPadding)
                .padding(bottom = 56.dp) // push up to make up for TopBar
                .shadow(10.dp, shape = RoundedCornerShape(30.dp))
        ) {
            Column(
                modifier = Modifier
                    .background(Color(0xFFE9E9E9), shape = RoundedCornerShape(15.dp))
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .padding(horizontal = initialHorizontalPadding * 2)
            ) {
                Spacer(Modifier.padding(vertical = 8.dp))
                NormalTextField(labelText = "Username", title = "Username")
                Spacer(Modifier.padding(vertical = 8.dp))
                PasswordTextField(title = "Password")

                /*
                TODO - LOGIC CHECK `pwContainer` against database entry for username & add logic to button
                 */

                Button(
                    onClick = { navController.navigate(Clients.route) },
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
}
