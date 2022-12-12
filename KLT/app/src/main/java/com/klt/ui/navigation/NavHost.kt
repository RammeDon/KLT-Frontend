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

//import androidx.navigation.compose.composable
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.klt.screens.*


@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AppNavHost(modifier: Modifier = Modifier) {
    // Calls the navigate function to control movement between views/screens in the app
    val navController = rememberAnimatedNavController()

    AnimatedNavHost(
        navController = navController,
        startDestination = Login.route,
        modifier = modifier
    ) {

        // LOGIN
        composable(route = Login.route) {
            LoginScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Login.route) }
            )
        }

        // HOME
        composable(route = Home.route) {
            HomeScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Home.route) }
            )
        }

        // CLIENTS
        composable(route = Clients.route) {
            ClientScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Clients.route) }
            )
        }

        // SETTINGS
        composable(route = Settings.route) {
            SettingsScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Settings.route) }
            )
        }

        // TASKS
        composable(route = Tasks.route) {
            TaskScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Tasks.route) }
            )
        }

        // CREATE USER
        composable(route = CreateUser.route) {
            CreateUserScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(CreateUser.route) }
            )
        }

        // ADMIN
        composable(route = Admin.route) {
            AdminScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Admin.route) }
            )
        }

        // USER
        composable(route = User.route) {
            UserScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(User.route) }
            )
        }

        // RESET PASSWORD
        composable(route = ResetPassword.route) {
            ResetPasswordScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(ResetPassword.route) }
            )
        }


        // FORGOT PASSWORD
        composable(route = ForgotPassword.route) {
            ForgotPasswordScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(ForgotPassword.route) }
            )
        }
    }
}

/**
 * Ensures that there is no duplicate entries within the NavGraph backstack on clicking a NavPath
 * ImageVector (Icon) asset, if any.
 */
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
