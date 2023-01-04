package com.klt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.*
import com.klt.ui.navigation.Clients
import com.klt.ui.navigation.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun CreateUserScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {

        Column(modifier = Modifier.fillMaxSize()) {

            ScreenSubTitle(
                navController = navController,
                onBackNavigation = Tasks.route,
                bigText = "Create User",
                smallText = "Here you create accounts for the employees"
            )

            CreateUserForm(
                modifier = Modifier
                    .padding(horizontal = 35.dp)
                    .padding(top = 20.dp)
                    .fillMaxHeight()
            )
        }
    }
}
