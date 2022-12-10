package com.klt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.klt.screens.LoginScreen
import com.klt.screens.backgroundColor
import com.klt.ui.composables.TopBar
import com.klt.ui.navigation.Home
import com.klt.ui.navigation.appTabRowScreens
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
    val scaffoldState = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Open))
    // Calls the navigate function to control movement between views/screens in the app
    val navController = rememberNavController()
    // Returns the current back stack (composable view/screen stack) as a state
    val currentBackStack by navController.currentBackStackEntryAsState()
    val currentDestination = currentBackStack?.destination
    val currentScreen =
        appTabRowScreens.find { it.route == currentDestination?.route } ?: Home

    Scaffold(scaffoldState = scaffoldState, topBar = { TopBar() })
    {
        LoginScreen(
            modifier = Modifier.background(color = backgroundColor),
        )
    }
}
