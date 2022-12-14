package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.screens.Customer
import com.klt.screens.KLTItem
import com.klt.screens.Task
import com.klt.ui.navigation.Login

@Composable
fun EntryCard(
    item: KLTItem,
    textColor: Color,
    navController: NavController,
    destination: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    hasIcon: Boolean = true
) {
    val padding = 15.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(50.dp) // make dynamic
            .background(backgroundColor, shape = RoundedCornerShape(5.dp))
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(padding)) {
            Text(text = item.name, color = textColor)
            Spacer(modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Button(modifier = Modifier
                .alpha(0f)
                .weight(1f), onClick = {
                navController.navigate(destination)
            }) {
                /* intentionally left blank */
            }
            if (hasIcon) {
                IconButton(onClick = { navController.navigate(Login.route) }) {
                    Icon(
                        imageVector = when (item) {
                            is Customer -> Icons.Outlined.PushPin
                            is Task -> Icons.Rounded.ArrowForward
                            else -> Icons.Default.BrokenImage // in case of error
                        }, contentDescription = "card-icon", tint = textColor
                    )
                }
            }
        }
    }
}
