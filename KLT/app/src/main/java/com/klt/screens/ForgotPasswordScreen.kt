package com.klt.screens

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import androidx.navigation.NavController
import com.klt.ui.composables.NormalTextField
import com.klt.ui.navigation.ResetPassword

@Composable
fun ForgotPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.weight(1f))
        NormalTextField(
            labelText = "example@klt.se", title = "Email", modifier = Modifier
                .align(Alignment.CenterHorizontally)
                .fillMaxWidth(),
            updateState = { email = it }
        )
        Button(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            onClick = {
                sendToken(email, context)
                navController.navigate(ResetPassword.route)
                println(email)
            }
        ) {
            Text("Reset Password")
        }
        Spacer(modifier = Modifier.weight(1f))
    }

}


private fun sendToken(email : String, context: Context) {


    val allowedChars = ('A'..'Z') + ('a'..'z') + ('0'..'9')
    val token = (20..20).map { allowedChars.random() }.joinToString("")


    //emailIntent.data = Uri.parse("mailto:$email")
    //emailIntent.type = "text/plain"
    //emailIntent.putExtra(Intent.EXTRA_EMAIL, email)
    //emailIntent.putExtra(Intent.EXTRA_SUBJECT, "KLT email token")
    //emailIntent.putExtra(Intent.EXTRA_TEXT, token)


    //context.startActivity(Intent.createChooser(emailIntent, "Send mail..."))
    val emailIntent  = Intent(Intent.ACTION_SENDTO).apply {
        data = Uri.parse("mailto:$email")
        putExtra(Intent.EXTRA_EMAIL, arrayOf(email))
        putExtra(Intent.EXTRA_SUBJECT, "Email token")
        putExtra(Intent.EXTRA_TEXT, "Your mail token is: $token")

    }

    //context.startActivity(Intent.createChooser(emailIntent, "Send email"))
    context.sendBroadcast(emailIntent)


}
