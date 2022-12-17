package com.klt.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController


@Composable
fun ScreenSubTitle(
    navController: NavController,
    onBackNavigation: String,
    bigText: String,
    smallText: String
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .height(50.dp)
                .padding(top = 20.dp)

        ) {
            IconButton(onClick = { navController.navigate(onBackNavigation) }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "card-icon",
                    tint = Color.Black
                )
            }
            Text(
                text = bigText,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        }
        Text(
            text = smallText,
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier.padding(start = 18.dp)
        )
    }
}
