package com.klt.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowUp
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.screens.KLTItem
import com.klt.ui.navigation.Login

@Composable
fun CollapsableLazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    destination: String,
    items: List<KLTItem>,
    repeats: Int = 1,
    color: Color,
    windowTitle: String,
    icon: ImageVector? = null,
) {

    var collapsed by remember { mutableStateOf(false) }

    Column(modifier = Modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp)
                .clickable { collapsed = !collapsed }
                .height(30.dp)
        ) {
            Text(
                text = windowTitle,
                modifier = Modifier
                    .weight(3f),
                fontWeight = FontWeight.Bold
            )

            Icon(
                imageVector = if (collapsed) Icons.Sharp.KeyboardArrowUp else Icons.Sharp.KeyboardArrowDown,
                contentDescription = "keyboard up/down icon"
            )
        }
    }
    LazyWindow(
        navController = navController,
        destination = Login.route,
        items = items,
        repeats = 5,
        color = color,
        icon = icon,
        collapsed = collapsed
    )
}