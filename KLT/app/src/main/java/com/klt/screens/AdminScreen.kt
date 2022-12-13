package com.klt.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun AdminScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    /*TODO */
}


sealed interface User {
    val firstName: String
    val lastName: String
    val email: String
    val hashedPassword: String
    val isAdmin: Boolean

}

object User1 : User {
    override val firstName = "Ramin"
    override val lastName = "Darudi"
    override val email = "ramin@example.com"
    override val hashedPassword = "AAASDD56aSD77"
    override val isAdmin = true
}

object User2 : User {
    override val firstName = "Sam"
    override val lastName = "Hurenkamhp"
    override val email = "sammy@example.com"
    override val hashedPassword = "1234"
    override val isAdmin = true
}

object User3 : User {
    override val firstName = "Oscar"
    override val lastName = "Karlsson"
    override val email = "Oskar@example.com"
    override val hashedPassword = "backend"
    override val isAdmin = true
}

object User14 : User {
    override val firstName = "Alrik"
    override val lastName = "Darudi"
    override val email = "ramin@example.com"
    override val hashedPassword = "AAASDD56aSD77"
    override val isAdmin = true
}