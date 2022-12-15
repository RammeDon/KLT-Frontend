package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.ui.navigation.Login

@OptIn(ExperimentalComposeUiApi::class)
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
    val cardColor = remember { mutableStateOf(color) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp)
            .height(50.dp) // make dynamic
            .background(cardColor.value, shape = RoundedCornerShape(5.dp))
            .clickable { } // Make box background color change
            .then(modifier),
        contentAlignment = Alignment.Center
    ) {
        Row(modifier = Modifier.padding(padding)) {
            Text(text = text)
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
            if (icon != null) IconButton(onClick = { navController.navigate(Login.route) }) {
                Icon(imageVector = icon, contentDescription = "card-icon")
            }

        }

    }

}
