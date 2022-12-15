package com.klt.ui.composables

import androidx.compose.material.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.klt.ui.navigation.User

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
}


@Composable
fun BottomDrawerContent(
    modifier: Modifier = Modifier,
    state: DrawerState
) {
    /* TODO -- BODY */
}
