package com.klt.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.klt.ui.navigation.Login
import com.klt.ui.navigation.Settings
import com.klt.ui.navigation.User

enum class SideBarUserOptions(val title: String, val icon: ImageVector?, val route: String) {
    PROFILE("Profile", Icons.Rounded.Person, User.route),
    SETTINGS("Settings", Icons.Rounded.Settings, Settings.route),
    EXPORT("Export", null, Login.route) /* TODO -- ROUTE STILL NEEDED */
}


enum class SideBarAdminOptions(val title: String, val icon: ImageVector?, val route: String) {
    USER_CONTROL("User control", Icons.Rounded.AdminPanelSettings, route = Login.route),
    CLIENT_CONTROL("Client control", Icons.Rounded.AddBusiness, route = Login.route),
    TASK_TEMPLATES("Task templates", Icons.Rounded.Description, route = Login.route),
    PERFORMANCE("Performance", Icons.Rounded.Analytics, route = Login.route)
}
