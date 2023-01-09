package com.klt.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Analytics
import androidx.compose.ui.graphics.vector.ImageVector
import com.klt.ui.navigation.*

enum class SideBarUserOptions(val title: String, val icon: ImageVector?, val route: String) {
    PROFILE("Profile", User.icon, User.route),
    SETTINGS("Settings", Settings.icon, Settings.route),
    LOGOUT("Logout", null, Logout.route)
}


enum class SideBarAdminOptions(val title: String, val icon: ImageVector?, val route: String) {
    CREATE_USER("Create User", CreateUser.icon, route = CreateUser.route),
    USER_CONTROL("User Control", UserControl.icon, route = UserControl.route),
    CUSTOMER_CONTROL("Customer control", CustomerControl.icon, route = CustomerControl.route),

    // Needs to be routed to Alrik's screen
    PERFORMANCE("Performance", Icons.Rounded.Analytics, route = Login.route)
}
