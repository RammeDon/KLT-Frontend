package com.klt.ui.navigation

/**
 *
 *  Adapted from source: android.developers.com
 */
/*
 * Copyright 2022 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.klt.screens.ClientScreen
import com.klt.screens.HomeScreen
import com.klt.screens.LoginScreen
import com.klt.screens.SettingsScreen

//import com.klt.screens.LoginScreen

@Composable
fun AppNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = Home.route,
        modifier = modifier
    ) {

        // LOGIN
        composable(route = Login.route) {
            LoginScreen(
                OnSelfClick = { // navigation for when an allocated icon is clicked
                    navController.navigateSingleTopTo(Login.route)
                }
            )
        }

        // HOME
        composable(route = Home.route) {
            HomeScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(Home.route)
                },
            )

        }

        // CLIENTS
        composable(route = Clients.route) {
            ClientScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(Home.route)
                }
            )
        }

        // SETTINGS
        composable(route = Settings.route) {
            SettingsScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(Settings.route)
                },
            )
        }

        // TASKS
        composable(route = Tasks.route) {
            SettingsScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(Tasks.route)
                },
            )
        }

        // TASKS
        composable(route = Tasks.route) {
            SettingsScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(Tasks.route)
                },
            )
        }

        // CREATE USER
        composable(route = CreateUser.route) {
            SettingsScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(CreateUser.route)
                },
            )
        }

        // ADMIN
        composable(route = Admin.route) {
            SettingsScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(Admin.route)
                },
            )
        }

        // User
        composable(route = User.route) {
            SettingsScreen(
                OnSelfClick = {
                    navController.navigateSingleTopTo(User.route)
                },
            )
        }
    }
}

fun NavHostController.navigateSingleTopTo(route: String) = this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    popUpTo(
        this@navigateSingleTopTo.graph.findStartDestination().id
    ) {
        saveState = true
    }
    // Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    // Restore state when re-selecting a previously selected item
    restoreState = true
}
