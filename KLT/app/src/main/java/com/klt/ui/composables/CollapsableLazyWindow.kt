package com.klt.ui.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.sharp.KeyboardArrowDown
import androidx.compose.material.icons.sharp.KeyboardArrowRight
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
    defaultCollapsed: Boolean
) {

    var collapsed by remember { mutableStateOf(defaultCollapsed) }

    Column(
        modifier = Modifier
            .then(modifier)
    ) {
        Column(modifier = Modifier) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { collapsed = !collapsed }
                    .padding(horizontal = 20.dp)
                    .height(30.dp)

            ) {
                Text(
                    text = windowTitle,
                    modifier = Modifier
                        .weight(3f),
                    fontWeight = FontWeight.Bold
                )

                Icon(
                    imageVector = if (collapsed) Icons.Sharp.KeyboardArrowRight else Icons.Sharp.KeyboardArrowDown,
                    contentDescription = "keyboard up/down icon"
                )
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        LazyWindow(
            navController = navController,
            destination = Login.route, // change it so that it takes the destination parameter as value
            items = items,
            repeats = repeats,
            color = color,
            icon = icon,
            collapsed = collapsed,
            modifier = Modifier.weight(1f)
        )
    }


}