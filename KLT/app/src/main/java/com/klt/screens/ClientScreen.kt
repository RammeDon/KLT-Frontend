package com.klt.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.composables.DualLazyWindow
import com.klt.ui.navigation.Tasks


interface KLTItem {
    val name: String
    val id: String
    val hasIcon: Boolean
}

interface Customer : KLTItem //inherits all abstract properties from KLTItem
interface Task : KLTItem { // inherits all abstracts properties from KLTItem + specify some new ones
    val timeTaken: Long
    val timePaused: Long
    override val hasIcon: Boolean // force default true
        get() = true
}

// Client -> Client Interface -> KLT Item interface
object Customer1 : Customer {
    // only needs to override values specified in super parent (KLTItem)
    override val name = "KLT Internal Client Example"
    override val id = "BABAGOCLIENT"
    override val hasIcon = true
}

object Customer2 : Customer {
    // only needs to override values specified in super parent (KLTItem)
    override val name = "Krinova Client Example"
    override val id = "BABAGOCLIENT2"
    override val hasIcon = true
}

// Task -> Task Interface -> KLT Item interface
object Task1 : Task {
    // needs to override values specified in super parent (KLTItem)
    override val name = "KLT Internal Task Example"
    override val id = "BABAGOTASK"

    // also needs to override values specified in direct parent (Task)
    override val timeTaken = 1L
    override val timePaused = 2L
}


object Task2 : Task {
    // needs to override values specified in super parent (KLTItem)
    override val name = "Krinova Task Example"
    override val id = "BABAGOTASK2"

    // also needs to override values specified in direct parent (Task)
    override val timeTaken = 1L
    override val timePaused = 2L
}

val listOfClients = listOf(Customer1, Customer2)
val listOfTasks = listOf(Task1, Task2)

@Composable
fun ClientScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
    ) {
        DualLazyWindow(
            navController = navController,
            leftButtonText = "Unpinned",
            rightButtonText = "Pinned",
            leftLazyItems = listOfClients,
            rightLazyItems = listOfClients,
            rightIcons = Icons.Outlined.PushPin,
            leftDestination = Tasks.route,
            rightDestination = Tasks.route
        )
    }
}
