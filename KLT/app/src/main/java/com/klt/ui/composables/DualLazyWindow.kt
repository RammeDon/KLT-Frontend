package com.klt.ui.composables

import android.annotation.SuppressLint
import android.view.WindowInsets.Side
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import com.klt.screens.KLTItem
import com.klt.util.ItemType
import com.klt.util.Sides
import org.json.JSONArray
import org.json.JSONObject

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun DualLazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    leftButtonText: String,
    rightButtonText: String,
    leftLazyItems: JSONArray,
    rightLazyItems: JSONArray,
    leftIcons: ImageVector? = null,
    rightIcons: ImageVector? = null,
    leftDestination: String,
    rightDestination: String,
    itemSelected: (JSONObject) -> Unit = {},
    itemType: ItemType
) {
    val buttonSelectedColor = Color.LightGray
    val buttonUnSelectedColor = colorResource(id = R.color.KLT_WhiteGray2)
    var leftButtonSelected by remember { mutableStateOf(false) }
    var leftButtonColor by remember { mutableStateOf(buttonUnSelectedColor) }
    var rightButtonColor by remember { mutableStateOf(buttonSelectedColor) }

    Box(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .then(modifier)
    ) {
        Scaffold(
            topBar = {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(55.dp)
                        .padding(horizontal = 10.dp)
                ) {
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .alpha(if (leftButtonSelected) 1f else 0.7f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 10.dp,
                                    topEnd = 0.dp,
                                    bottomStart = 10.dp,
                                    bottomEnd = 0.dp,
                                )
                            ),
                        onClick = {
                            leftButtonSelected = true
                            leftButtonColor = buttonSelectedColor
                            rightButtonColor = buttonUnSelectedColor
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = leftButtonColor)
                    ) {
                        Column {
                            Text(
                                text = leftButtonText,
                                color = if (leftButtonSelected) Color.Black else Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Int",
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(0.5f)
                                    .scale(0.75f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                    Button(
                        shape = RectangleShape,
                        modifier = Modifier
                            .fillMaxSize()
                            .weight(1f)
                            .alpha(if (!leftButtonSelected) 1f else 0.7f)
                            .clip(
                                RoundedCornerShape(
                                    topStart = 0.dp,
                                    topEnd = 10.dp,
                                    bottomStart = 0.dp,
                                    bottomEnd = 10.dp,
                                )
                            ),
                        onClick = {
                            leftButtonSelected = false
                            rightButtonColor = buttonSelectedColor
                            leftButtonColor = buttonUnSelectedColor
                        },
                        colors = ButtonDefaults.buttonColors(backgroundColor = rightButtonColor)
                    ) {
                        Column {
                            Text(
                                text = rightButtonText,
                                color = Color.Black,
                                modifier = Modifier.fillMaxWidth(),
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = "Int",
                                color = Color.Black,
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .alpha(0.5f)
                                    .scale(0.75f),
                                textAlign = TextAlign.Center
                            )
                        }
                    }
                }
//
            }, content = {
                Box(modifier = Modifier.padding(top = 15.dp)) {

                    LazyColumn(
                        // TODO -- ADD VERTICAL ARRANGEMENT AND TAKE OUT REPEATS
                        modifier = Modifier
                            .fillMaxHeight()
                            .then(modifier),
                    ) {

                    if (leftButtonSelected) {

                        (0 until leftLazyItems.length()).forEach {
                            val customer = leftLazyItems.getJSONObject(it)
                            println("---------- this customer ${customer.get("name")}")
                            item{
                                Spacer(modifier = Modifier.height(7.dp))
                                EntryCard(
                                    item = customer,
                                    textColor = Color.Black,
                                    navController = navController,
                                    destination = leftDestination,
                                    itemType = itemType,
                                    side = Sides.LEFT
                                )
                            }
                        }
                    } else {
                        (0 until rightLazyItems.length()).forEach {
                            val customer = rightLazyItems.getJSONObject(it)
                            item{
                                Spacer(modifier = Modifier.height(7.dp))
                                EntryCard(
                                    item = customer,
                                    textColor = Color.Black,
                                    navController = navController,
                                    destination = rightDestination,
                                    itemType = itemType,
                                    side = Sides.RIGHT
                                )
                            }
                        }
                        }
                    }
                }
            }
        )
    }
}
