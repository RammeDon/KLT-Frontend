package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.CreateUserForm
import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.navigation.Customers

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
                onBackNavigation = navController.previousBackStackEntry?.destination?.route
                    ?: Customers.route,
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
