package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.composables.isMatching
import com.klt.ui.composables.pwContainer
import com.klt.ui.navigation.Login
import com.klt.util.HashUtils

@Composable
fun ResetPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    /*TODO -- add screen contents here */


    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        PasswordTextField(labelText = "Token from email", onUpdate = {})
        val newPW = PasswordTextField(labelText = "New Password", checkPasswordStrength = true, onUpdate = {})
        val confirmPW =
            PasswordTextField(labelText = "Confirm new password", performMatchCheck = true, onUpdate = {})
        Spacer(modifier = Modifier.weight(1f))
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 10.dp),
            onClick = {
                if (isMatching && pwContainer != HashUtils.sha256("")) navController.navigate(Login.route)
            }
        ) {
            Text("Change Password")
        }
        Spacer(modifier = Modifier.weight(5f))

    }

}
