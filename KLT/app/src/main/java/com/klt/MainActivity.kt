package com.klt

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLayoutDirection
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.klt.drawers.SideDrawer
import com.klt.ui.composables.Loading
import com.klt.ui.composables.TopBar
import com.klt.ui.navigation.*
import com.klt.ui.theme.KLTTheme
import com.klt.util.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


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
@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RunApp() {
    val navController = rememberAnimatedNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val state = rememberScaffoldState(rememberDrawerState(initialValue = DrawerValue.Closed))
    val context = LocalContext.current

    var showHamburger by remember { mutableStateOf(false) }
    var isAuth by remember { mutableStateOf(false) }
    var loading by remember { mutableStateOf(true) }

    // Backend respond, if we have an valid token
    val onAuth: (ApiResult) -> Unit = {
        isAuth = (it.status() == HttpStatus.SUCCESS)
        loading = false
    }

    val coroutine = rememberCoroutineScope()
    LaunchedEffect(navBackStackEntry) {
        launch {

            // Call backend to check if we already have an valid token
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getUserData(
                    token = LocalStorage.getToken(context),
                    onRespond = { onAuth(it) }
                )
            }

            showHamburger = !listOf(
                Login.route,
                ForgotPassword.route,
                ResetPassword.route
            ).contains(navController.currentBackStackEntry?.destination?.route)
        }
    }

    CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Rtl) {
        Scaffold(
            scaffoldState = state,
            topBar = {
                CompositionLocalProvider(LocalLayoutDirection provides LayoutDirection.Ltr) {
                    TopBar(
                        navController = navController,
                        state = state,
                        showHamburger = showHamburger
                    )
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

                        if (!loading) {
                            AnimatedAppNavHost(
                                navController = navController,
                                startDestination = if (isAuth) Customers.route else Login.route
                            )
                        } else {
                            Loading()
                        }
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
        )
    }
}
