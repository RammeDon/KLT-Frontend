package com.klt.ui.composables

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.navigation.NavController
import com.klt.screens.KLTItem

@Composable
fun LazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    destination: String,
    items: List<KLTItem>,
    repeats: Int = 1
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
        items(items = items, key = { client -> client.id }) { item ->
            repeat(repeats) {
                EntryCard(
                    text = item.name,
                    navController = navController,
                    destination = destination,
                    id = item.id,
                    icon = Icons.Outlined.PushPin
                )
                Spacer(modifier = Modifier.height(7.dp))
            }
        }
    }
}
