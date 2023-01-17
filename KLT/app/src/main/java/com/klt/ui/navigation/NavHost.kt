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
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.composable
import com.klt.screens.*

/**
 * Docs: https://google.github.io/accompanist/navigation-animation/
 */
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimatedAppNavHost(
    modifier: Modifier = Modifier, navController: NavHostController,
    startDestination: String
) {
    // Calls the navigate function to control movement between views/screens in the app
    val defaultTween = 450

    AnimatedNavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier
    ) {


        // LOGIN
        composable(
            route = Login.route,
            // animation played ON ENTER when screen is not at top of back stack)
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            LoginScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Login.route) }
            )
        }

        // CLIENTS
        composable(
            route = Customers.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            CustomerScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Customers.route) }
            )
        }

        // SETTINGS
        composable(
            route = Settings.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            SettingsScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Settings.route) }
            )
        }

        // TASKS
        composable(
            route = Tasks.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            TaskScreen(
                navController = navController
            ) { navController.navigateSingleTopTo(Tasks.route) }
        }

        // CREATE USER
        composable(
            route = CreateUser.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            CreateUserScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(CreateUser.route) }
            )
        }

        // ADMIN
        composable(
            route = Admin.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            AdminScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Admin.route) }
            )
        }

        // USER
        composable(
            route = User.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            UserScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(User.route) }
            )
        }

        // RESET PASSWORD
        composable(
            route = ResetPassword.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            ResetPasswordScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(ResetPassword.route) }
            )
        }


        // FORGOT PASSWORD
        composable(
            route = ForgotPassword.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            ForgotPasswordScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(ForgotPassword.route) }
            )
        }

        // ACTIVE TASK
        composable(
            route = ActiveTask.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            ActiveTaskScreen(
                navController = navController
            ) { navController.navigateSingleTopTo(ActiveTask.route) }
        }

        // LOGOUT
        composable(
            route = Logout.route,
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            LogoutScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(Logout.route) }
            )
        }

        // USER CONTROL
        composable(
            route = UserControl.route,
            // animation played ON ENTER when screen is not at top of back stack)
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            UserControlScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(UserControl.route) }
            )
        }

        // USER CONTROL
        composable(
            route = CustomerControl.route,
            // animation played ON ENTER when screen is not at top of back stack)
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            CustomerControlScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(UserControl.route) }
            )
        }

        // CONFIRM TOKEN
        composable(
            route = ConfirmToken.route,
            // animation played ON ENTER when screen is not at top of back stack)
            enterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON ENTER ON BACK-KEY (requires route to be at top of back stack)
            popEnterTransition = {
                fadeIn(tween(defaultTween))
            },
            // animation played ON EXIT
            exitTransition = {
                fadeOut(tween(defaultTween))
            },
            // animation played ON EXIT ON BACK-KEY
            popExitTransition = {
                fadeOut(tween(defaultTween))
            }
        ) {
            ConfirmTokenScreen(
                navController = navController,
                OnSelfClick = { navController.navigateSingleTopTo(UserControl.route) }
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

/**
 * Ensures that there is no duplicate entries within the NavGraph backstack on clicking a NavPath
 * ImageVector (Icon) asset, if any.
 */
fun NavController.navigateSingleTopTo(route: String) = this.navigate(route) {
    // Pop up to the start destination of the graph to
    // avoid building up a large stack of destinations
    // on the back stack as users select items
    this@navigateSingleTopTo.graph.findNode(route)?.let {
        popUpTo(
            it.id
        ) {
            saveState = true
        }
    }
    // Avoid multiple copies of the same destination when re-selecting the same item
    launchSingleTop = true
    // Restore state when re-selecting a previously selected item
    restoreState = true
}
