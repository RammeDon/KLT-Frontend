package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.ui.composables.EntryCard
import com.klt.ui.composables.TitledDivider
import com.klt.ui.navigation.Tasks


interface KLTItem {
    val name: String
    val id: String
}

object KLTItem1 : KLTItem {
    override val name = "KLT Internal"
    override val id = "BABAGO"

}

object KLTItem2 : KLTItem {
    override val name = "The boys"
    override val id = "WDSADF"
}

object KLTItem3 : Clients {
    override val name = "Business"
    override val id = "DEWDFSDA"
}

interface Clients : KLTItem

val clients = listOf(KLTItem1, KLTItem2, KLTItem3)

@Composable
fun ClientScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp),
    ) {
        item {
            Text(text = "Clients", fontSize = 26.sp, fontWeight = FontWeight.Bold)
            Text(text = "Click on a client to show its tasks", fontSize = 14.sp)
            Spacer(Modifier.padding(vertical = 8.dp))

            TitledDivider(title = "Pinned Clients")

            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                LazyColumn(
                    modifier = Modifier
                        .height(
                            max(
                                200.dp,
                                LocalConfiguration.current.screenHeightDp.dp / 3
                            )
                        )
                ) {

                    items(items = clients, key = { client -> client.id }) { item ->

                        repeat(5) {
                            EntryCard(
                                text = item.name,
                                navController = navController,
                                destination = Tasks.route,
                                id = item.id,
                                icon = Icons.Outlined.PushPin
                            )
                            Spacer(modifier = Modifier.height(7.dp))
                        }

                    }
                }

                TitledDivider(title = "All Clients")
            }
            LazyColumn(
                modifier = Modifier.height(
                    max(
                        200.dp,
                        LocalConfiguration.current.screenHeightDp.dp / 3
                    )
                )
            ) {

                items(items = clients, key = { client -> client.id }) { item ->

                    repeat(5) {
                        EntryCard(
                            text = item.name,
                            navController = navController,
                            destination = Tasks.route,
                            id = item.id,
                            icon = Icons.Outlined.PushPin
                        )
                        Spacer(modifier = Modifier.height(7.dp))
                    }

                }
            }
        }
    }
}
