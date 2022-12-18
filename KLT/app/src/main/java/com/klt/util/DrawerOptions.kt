package com.klt.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import com.klt.ui.navigation.*

enum class SideBarUserOptions(val title: String, val icon: ImageVector?, val route: String) {
    PROFILE("Profile", Icons.Rounded.Person, User.route),
    SETTINGS("Settings", Icons.Rounded.Settings, Settings.route),
    EXPORT("Export", null, Login.route) /* TODO -- ROUTE STILL NEEDED */
}


enum class SideBarAdminOptions(val title: String, val icon: ImageVector?, val route: String) {
    USER_CONTROL("User control", Icons.Rounded.AdminPanelSettings, route = CreateUser.route),
    CLIENT_CONTROL("Client control", Icons.Rounded.AddBusiness, route = Clients.route),
    TASK_TEMPLATES("Task templates", Icons.Rounded.Description, route = Tasks.route),
    PERFORMANCE("Performance", Icons.Rounded.Analytics, route = Login.route)
}

enum class Measurements(val M: String) {
    ST("ST"),
    KG("KG"),
    Gr("Gr"),
    L("L")
}