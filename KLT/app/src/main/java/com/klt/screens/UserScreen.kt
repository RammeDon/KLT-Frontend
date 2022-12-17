package com.klt.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.composables.EditableCards
import com.klt.ui.composables.PasswordTextField
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
    var buttonPressed by remember { mutableStateOf(false) }

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
                    text = "emilhentriksen@klt.com",
                    icon = Icons.Outlined.Email,
                    editOn = editState
                )
                if (editState) { // instead of textfield it should be passwordtextfield
                    Box(modifier = Modifier.padding(horizontal = 30.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(start = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "name-Icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            PasswordTextField(
                                labelText = "New Password",
                                checkPasswordStrength = true
                            )
                        }
                    }
                    Spacer(modifier = Modifier.height(5.dp))
                    Box(modifier = Modifier.padding(horizontal = 30.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(start = 15.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "name-Icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            PasswordTextField(
                                labelText = "confirm Password",
                                performMatchCheck = true
                            )
                        }
                    }
                } else {
                    EditableCards(
                        text = "**************",
                        icon = Icons.Filled.Lock,
                        editOn = editState
                    )
                }

            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Button(modifier = Modifier
                    .width(180.dp)
                    .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.KLT_Red)),
                    onClick = {
                        editState = !editState
                        buttonPressed = !buttonPressed
                    }) {
                    Text(
                        text = if (!buttonPressed) "Edit Profile" else "Save Changes",
                        color = Color.White
                    )
                }
            }

        }

    }
}
