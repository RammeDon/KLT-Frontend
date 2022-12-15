package com.klt

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.klt.drawers.SideDrawer
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
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RunApp() {
    val navController = rememberAnimatedNavController()
    val state = rememberScaffoldState(
        rememberDrawerState(initialValue = DrawerValue.Closed)
    )

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            scaffoldState = state,
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    TopBar(navController = navController, state = state)
                }
            },
            content = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    Box(
                        modifier = Modifier
                            .padding(it) // only applied when there is a bottomBar added.
                            // set height of padding equal to 1/x of device screen current height
                            .padding(top = (LocalConfiguration.current.screenHeightDp / 23).dp)
                    ) {
                        AnimatedAppNavHost(navController = navController)
                    }
                }
            },
            drawerShape = RoundedCornerShape(topEnd = 10.dp, bottomEnd = 10.dp),
            drawerContent = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr)
                {
                    val sideDrawer: SideDrawer = SideDrawer(
                        drawerState = state.drawerState,
                        navController = navController
                    )
                    sideDrawer.drawScaffold()
                }
            },
            drawerGesturesEnabled = state.drawerState.isOpen,
            bottomBar = { }

        )
    }
}
