package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.composables.LazyWindow
import com.klt.ui.composables.TitledDivider
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
    override val hasIcon = false
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

/*
    The most generic parent can be used as a type when passing arguments (e.g., Any)
    This means List<KLTItem> accepts both descendants from the Client and Task interface, but
    List<Client> only accepts objects inherited from Client, and List<Task> only accepts objects
    inherited from Task
 */


val listOfClients = listOf(Customer1, Customer2)
val listOfTasks = listOf(Task1, Task2)

@Composable
fun ClientScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
        ) {
            Text(text = "Clients", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(text = "Click on a client to show its tasks", fontSize = 14.sp)
            Spacer(Modifier.padding(vertical = 8.dp))
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                TitledDivider(title = "Pinned Clients")
                Box(
                    modifier = Modifier.heightIn(0.dp, 232.dp)
                ) {
                    LazyWindow(
                        navController = navController,
                        destination = Tasks.route,
                        items = listOfClients,
                        repeats = 8,
                        color = Color.LightGray,
                        icon = Icons.Outlined.PushPin
                    )
                }

                Spacer(modifier = Modifier.height(15.dp))
                TitledDivider(title = "All Clients")
                Box(
                    modifier = Modifier
                ) {
                    LazyWindow(
                        navController = navController,
                        destination = Tasks.route, // we don't yet have an ActiveTask screen
                        items = listOfTasks,
                        repeats = 10,
                        color = Color.Red
                    )
                }

            }
        }
    }
}
