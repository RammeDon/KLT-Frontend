package com.klt.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import kotlinx.coroutines.launch

@Composable
fun TopBar(modifier: Modifier = Modifier, navController: NavController, state: ScaffoldState) {
    val scale = 2f
    Row(modifier = modifier.then(Modifier.fillMaxWidth())) {
        Icon(
            painter = painterResource(id = R.drawable.klt_icon_logo),
            contentDescription = "KLT Logo",
            tint = Color.Unspecified,
            modifier = Modifier
                .scale(scale)
                .padding(start = 25.dp, top = 15.dp)
        )
        Spacer(modifier = Modifier.weight(3f))

        val coroutine = rememberCoroutineScope()
        var menuOpened by remember {
            mutableStateOf(false)
        }

        var iconDisplayed by remember {
            mutableStateOf(-1)
        }

        IconButton(
            onClick = {
                coroutine.launch {
                    if (state.drawerState.isClosed) state.drawerState.open()
                    else state.drawerState.close()
                }
                menuOpened = !menuOpened
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
    }
}
