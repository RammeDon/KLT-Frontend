package com.klt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.klt.screens.LoginScreen
import com.klt.screens.backgroundColor
import com.klt.ui.theme.KLTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KLTTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    RunApp(name = "")

                }
            }
        }
    }
}

@Composable
fun RunApp(name: String) {
    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Open))
    Scaffold(scaffoldState = scaffoldState, topBar = {
        Icon(
            painter = painterResource(id = R.drawable.klt_icon_logo),
            contentDescription = "KLT Logo",
            tint = Color.Unspecified,
            modifier = Modifier
                .scale(2f)
                .padding(start = 25.dp, top = 15.dp)
        )
    })
    {
        LoginScreen(
            modifier = Modifier.background(color = backgroundColor),
        )
    }

}
