package com.klt.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.klt.ui.navigation.*

enum class SideBarUserOptions(val title: String, val icon: ImageVector?, val route: String) {
    PROFILE("Profile", User.icon, User.route),
    SETTINGS("Settings", Settings.icon, Settings.route),
    EXPORT("Export", null, Login.route), /* TODO -- ROUTE STILL NEEDED */
    LOGOUT("Logout", null, Logout.route)
}


enum class SideBarAdminOptions(val title: String, val icon: ImageVector?, val route: String) {
    USER_CONTROL("User control", Icons.Rounded.AdminPanelSettings, route = CreateUser.route),
    CLIENT_CONTROL("Client control", Icons.Rounded.AddBusiness, route = Clients.route),
    TASK_TEMPLATES("Task templates", Icons.Rounded.Description, route = Tasks.route),
    PERFORMANCE("Performance", Icons.Rounded.Analytics, route = Statistics.route)
}

enum class Measurements(val M: String) {
    ST("ST"),
    KG("KG"),
    Gr("Gr"),
    L("L")
}