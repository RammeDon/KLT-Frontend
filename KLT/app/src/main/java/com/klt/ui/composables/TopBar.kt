package com.klt.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.ScaffoldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.navigation.Clients
import com.klt.ui.navigation.ForgotPassword
import com.klt.ui.navigation.Login
import com.klt.ui.navigation.ResetPassword
import kotlinx.coroutines.launch

@Composable
fun TopBar(modifier: Modifier = Modifier, navController: NavController, state: ScaffoldState) {
    val scale = 2f
    val interactionSource = remember { MutableInteractionSource() }
    Row(
        modifier = modifier.then(
            Modifier
                .fillMaxWidth()
                .padding(top = 15.dp)
                .padding(horizontal = 10.dp)
        )
    ) {
        Icon(
            painter = painterResource(id = R.drawable.klt_icon_logo),
            contentDescription = "KLT Logo",
            tint = Color.Unspecified,
            modifier = Modifier
                .scale(scale)
                .padding(start = 25.dp, top = 15.dp)
                .clickable(
                    onClick = {
                        val currScreen = navController.currentBackStackEntry?.destination?.route!!
                        if (currScreen != Login.route && currScreen != ForgotPassword.route &&
                            currScreen != ResetPassword.route
                        ) {
                            navController.navigate(Clients.route)
                        }

                    },
                    interactionSource = interactionSource, indication = null
                )
        )
        Spacer(modifier = Modifier.weight(3f))

        val coroutine = rememberCoroutineScope()

        IconButton(
            onClick = {
                coroutine.launch {
                    if (state.drawerState.isClosed) state.drawerState.open()
                    else state.drawerState.close()
                }
            },
            modifier = Modifier.padding(end = 15.dp, top = 10.dp)
        ) {
            Icon(
                Icons.Rounded.Menu,
                contentDescription = "Hamburger-menu-closed",
                modifier = Modifier.scale(scale)
            )
        }


    }
}
