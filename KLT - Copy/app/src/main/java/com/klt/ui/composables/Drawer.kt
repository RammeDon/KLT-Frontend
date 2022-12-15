package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.navigation.User

enum class DrawerOptions(text: String, route: String?) {
    Profile("Profile", User.route),
    Settings("Settings", com.klt.ui.navigation.Settings.route),
    Export("Export", null)
}

@Composable
fun Drawer(
    modifier: Modifier = Modifier,
    gesturesEnabled: Boolean = true,
    navController: NavController,
    state: DrawerState,
    updateState: (DrawerValue) -> Unit,
) {
    Column {
        Column(
            modifier = Modifier.weight(7f)
        ) {
            Row {
                IconButton(
                    onClick = {
                        updateState(DrawerValue.Closed)
                        iconDisplayed = if (menuOpened) 1 else 0
                    }, modifier = Modifier.padding(end = 15.dp, top = 10.dp)
                ) {
                    if (iconDisplayed == 1) {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_menu_open_24),
                            contentDescription = "Hamburger-menu-closed",
                            modifier = Modifier.scale(scale)
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.ic_baseline_menu_closed_24),
                            contentDescription = "Hamburger-menu-closed",
                            modifier = Modifier.scale(scale)
                        )
                    }
                }
                /* TODO -- SPACER */
                /* TODO -- MENU */
            }
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .background(Color(0x00424242))
        ) {
            Spacer(modifier = Modifier.weight(1f))
            Text(
                "Kjellssons Logistik & Transport AB",
                color = Color.LightGray,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }
    }
}
