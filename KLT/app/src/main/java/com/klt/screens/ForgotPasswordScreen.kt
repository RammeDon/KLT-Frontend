package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.NormalTextField
import com.klt.ui.navigation.ResetPassword

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        NormalTextField(labelText = "example@klt.se", title = "Email")
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = { navController.navigate(ResetPassword.route) }
        ) {
            Text("Reset Password")
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}
