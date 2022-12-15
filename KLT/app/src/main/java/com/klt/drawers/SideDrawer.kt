package com.klt.drawers

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.composables.EntryCard
import com.klt.ui.composables.TitledDivider
import com.klt.ui.navigation.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

class SideDrawer(
    private val drawerState: DrawerState,
    private val navController: NavController,
    private val modifier: Modifier = Modifier
) {
    @SuppressLint("ComposableNaming")
    @Composable
    fun drawScaffold() {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(colorResource(id = R.color.KLT_WhiteGray1)),
            topBar = { drawHead(modifier = modifier.scale(1.75f)) },
            content = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                ) {
                    drawBody(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(it)
                    )
                }
            },
            bottomBar = { drawFooter() }
        )
    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun drawHead(modifier: Modifier = Modifier) {
        val coroutine = rememberCoroutineScope()
        Row(
            modifier = Modifier
                .background(Color.White)
                .padding(bottom = 56.dp)
        ) {
            Spacer(modifier = Modifier.width(56.dp)) // cover scrim area
            /* TODO -- CODE HEAD */
            Row(
                modifier = Modifier
                    .padding(horizontal = 28.dp)
                    .padding(top = 14.dp)
                    .height(35.dp)
            ) {
                Text(
                    "Menu", modifier = modifier.then(
                        Modifier
                            .fillMaxHeight()
                            .padding(top = 7.dp)
                    ),
                    textAlign = TextAlign.Center,
                    color = colorResource(id = R.color.KLT_Red)
                )
                Spacer(modifier = Modifier.weight(1f))
                IconButton(
                    onClick = { coroutine.launch { drawerState.close() } },
                    modifier = Modifier.padding(top = 10.dp)
                ) {
                    Icon(
                        Icons.Rounded.ArrowForward,
                        contentDescription = "Close",
                        tint = colorResource(id = R.color.KLT_Red),
                        modifier = modifier
                    )
                }
            }
        }
    }


    @SuppressLint("ComposableNaming")
    @Composable
    private fun drawBody(modifier: Modifier = Modifier) {
        val coroutine: CoroutineScope = rememberCoroutineScope()
        Column(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            TitledDivider(title = "User Menu")

            EntryCard(
                item = "Profile",
                textColor = colorResource(id = R.color.KLT_DarkGray1),
                navController = this@SideDrawer.navController,
                destination = User.route,
                hasIcon = false,
                isInsideDrawer = true,
                backgroundColor = colorResource(id = R.color.KLT_WhiteGray2),
                job = { coroutine.launch { drawerState.close() } }
            )
            Spacer(modifier = Modifier.height(5.dp))
            EntryCard(
                item = "Profile",
                textColor = colorResource(id = R.color.KLT_DarkGray1),
                navController = this@SideDrawer.navController,
                destination = User.route,
                hasIcon = false,
                isInsideDrawer = true,
                job = { coroutine.launch { drawerState.close() } }
            )
            Spacer(modifier = Modifier.weight(1f))
            TitledDivider(title = "Admin Menu")
            Spacer(modifier = Modifier.weight(2f))


        }

    }

    @SuppressLint("ComposableNaming")
    @Composable
    private fun drawFooter(modifier: Modifier = Modifier) {
        Column(
            modifier = Modifier
                .background(colorResource(id = R.color.KLT_DarkGray1))
                .height(
                    LocalConfiguration.current.screenHeightDp.dp / 6
                )
        ) {
            Spacer(Modifier.weight(1f))
            Text(
                AnnotatedString(stringResource(id = R.string.company_name)), color = colorResource(
                    id = R.color.KLT_WhiteGray1
                ), modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 20.dp)
                    .alpha(0.5f),
                textAlign = TextAlign.Center, fontSize = 12.sp

            )

        }


    }
}
