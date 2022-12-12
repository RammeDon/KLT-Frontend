package com.klt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import com.klt.ui.composables.TopBar
import com.klt.ui.navigation.AnimatedAppNavHost
import com.klt.ui.theme.KLTTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Wrapper theme that acts as a container for the upcoming surface composable
            KLTTheme {
                // This surface is the top-level Box containing the core styling of the activity
                // ... Here, the modifier has largely been left empty.
                Surface(modifier = Modifier.fillMaxSize()) {
                    RunApp()
                }
            }
        }
    }
}

/**
 *  Initializes the NavController and sets the NavHost on startup
 */
@Composable
fun RunApp() {
    val scaffoldState = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Open)
    )

    Scaffold(
        scaffoldState = scaffoldState,
        topBar = { TopBar() },
        content = {
            Box(
                modifier = Modifier
                    .padding(it) // only applied when there is a bottomBar added.
                    // set height of padding equal to 1/x of device screen current height
                    .padding(top = (LocalConfiguration.current.screenHeightDp / 23).dp)
            ) {
                AnimatedAppNavHost() // Instantiates NavHost and loads in the default route
            }
        })
}
