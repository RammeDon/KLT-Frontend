package com.klt.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.composables.EditableCards
import com.klt.ui.composables.ScreenSubTitle

/*  ------  TODO: When time for adding functionality the this kotlin file will be turned into a
*            class which will have EditableCards as well. This is for more manageable handling of
*              input data and CRUD calls to input or collect data from the database.  */

@Composable
fun UserScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {

    var editState by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenSubTitle(
                navController = navController,
                onBackNavigation = navController.previousBackStackEntry?.destination?.route.toString(),
                bigText = "Return to " + navController.previousBackStackEntry?.destination?.route.toString(),
                smallText = ""
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            Text(
                text = "Profile",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
            Divider(
                Modifier
                    .padding(top = 8.dp)
                    .padding(horizontal = 30.dp)
                    .alpha(0.5f),
                color = colorResource(id = R.color.KLT_Red),
                thickness = 2.dp
            )
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                EditableCards(
                    text = "Emil Henriksen",
                    icon = Icons.Outlined.Person,
                    editOn = editState
                )
                EditableCards(
                    text = "+46 0734587234",
                    icon = Icons.Outlined.Phone,
                    editOn = editState
                )
                EditableCards(
                    text = "emilhentriksen@example.com",
                    icon = Icons.Outlined.Email,
                    editOn = editState
                )
                EditableCards(
                    text = "**************",
                    icon = Icons.Filled.Lock,
                    editOn = editState
                )

            }
            Box(modifier = Modifier) {
                Spacer(modifier = Modifier)
                Button(onClick = { /*TODO*/ },) {

                }
            }

        }

    }
}
