package com.klt.screens

import android.content.Context
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R
import com.klt.ui.navigation.Customers
import com.klt.ui.navigation.Login
import com.klt.util.LocalStorage

@Composable
fun LogoutScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {

    val context: Context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {


        Column(modifier = Modifier.fillMaxSize()) {

            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .fillMaxSize()
            ) {

                Spacer(modifier = Modifier.height((LocalConfiguration.current.screenHeightDp / 3).dp))

                Column(modifier = Modifier.wrapContentSize()) {
                    Text(
                        modifier = Modifier.fillMaxSize(),
                        textAlign = TextAlign.Center,
                        color = Color.Black,
                        fontSize = 15.sp,
                        text = "Are tou sure you want to logout?"
                    )

                    Row(
                        horizontalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Button(
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = colorResource(id = R.color.KLT_Red)
                            ),
                            modifier = Modifier.padding(10.dp),
                            onClick = {
                                LocalStorage.saveToken(context, "")
                                navController.navigate(Login.route)
                                navController.graph.clear()
                            })
                        {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                color = Color.White,
                                text = "Logout"
                            )
                        }
                        Button(
                            modifier = Modifier.padding(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                backgroundColor = Color.Transparent
                            ),
                            elevation = null,
                            onClick = {
                                navController.navigate(
                                    navController.previousBackStackEntry?.destination?.route
                                        ?: Customers.route
                                )
                            })
                        {
                            Text(
                                modifier = Modifier.padding(10.dp),
                                text = "Cancel"
                            )
                        }
                    }
                }
            }
        }
    }
}
