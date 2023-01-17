package com.klt.ui.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
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
import com.klt.ui.navigation.Customers
import com.klt.ui.navigation.navigateSingleTopTo


@Composable
fun ScreenSubTitle(
    modifier: Modifier = Modifier,
    navController: NavController,
    onBackNavigation: String,
    bigText: String,
    smallText: String,
    fetchState: Boolean? = null,
    job: (Boolean) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.height(50.dp)
        ) {
            IconButton(onClick = {
                if (fetchState != null) job(!fetchState)
                navController.popBackStack()
                navController.navigateSingleTopTo(
                    navController.previousBackStackEntry?.destination?.route ?: Customers.route
                )
            }) {
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
