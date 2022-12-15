package com.klt.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerState
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.klt.R
import com.klt.ui.navigation.User
import kotlinx.coroutines.launch

enum class DrawerOptions(text: String, route: String?) {
    // include all drawer options in here, with their routes (if any)
    Profile("Profile", User.route),
    Settings("Settings", com.klt.ui.navigation.Settings.route),
    Export("Export", null)
}

@Composable
fun SideDrawerContent(
    modifier: Modifier = Modifier,
    state: DrawerState,
) {
    val coroutine = rememberCoroutineScope()
    Row(modifier = Modifier.padding(horizontal = 28.dp)) {
        Text("Menu")
        Spacer(modifier = Modifier.weight(1f))
        IconButton(
            onClick = {
                coroutine.launch {
                    if (state.isClosed) state.open()
                    else state.close()
                }
            }, modifier = Modifier.padding(end = 15.dp, top = 10.dp)
        ) {
            if (state.isOpen) {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_menu_open_24),
                    contentDescription = "Hamburger-menu-closed",
                    modifier = modifier
                )
            } else {
                Icon(
                    painter = painterResource(R.drawable.ic_baseline_menu_closed_24),
                    contentDescription = "Hamburger-menu-closed",
                    modifier = modifier
                )
            }
        }
    }


}


@Composable
fun BottomDrawerContent(
    modifier: Modifier = Modifier,
    state: DrawerState
) {
    /* TODO -- BODY */
}
