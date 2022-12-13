package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController

@Composable
fun EntryCard(
    text: String,
    navController: NavController,
    destination: String,
    id: String,

    modifier: Modifier = Modifier,
    color: Color = Color.LightGray,
    icon: ImageVector? = null,


    ) {
    val padding = 15.dp

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(50.dp) // make dynamic
            .background(Color.LightGray, shape = RoundedCornerShape(5.dp))
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(padding)) {
            Text(text = text)
            Spacer(modifier = Modifier.weight(1f))
            if (icon != null) Icon(imageVector = icon, contentDescription = "card-icon")
        }
        Button(modifier = Modifier
            .fillMaxSize()
            .alpha(0f), onClick = {
            navController.navigate(destination)
        }) {
            /* intentionally left blank */
        }

    }

}
