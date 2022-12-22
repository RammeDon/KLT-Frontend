package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.BrokenImage
import androidx.compose.material.icons.outlined.CheckBoxOutlineBlank
import androidx.compose.material.icons.outlined.PushPin
import androidx.compose.material.icons.rounded.ArrowForward
import androidx.compose.material.icons.rounded.Check
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.screens.Customer
import com.klt.screens.CustomerSelected
import com.klt.screens.Task
import com.klt.util.ItemType
import com.klt.util.Sides
import kotlinx.coroutines.launch
import org.json.JSONObject

@Composable
fun EntryCard(
    item: JSONObject = JSONObject("{}"),
    textColor: Color,
    navController: NavController,
    destination: String,
    modifier: Modifier = Modifier,
    backgroundColor: Color = Color.LightGray,
    hasIcon: Boolean = true,
    isInsideDrawer: Boolean = false,
    icon: ImageVector? = null,
    itemType: ItemType,
    side: Sides,
    itemSelected: (JSONObject) -> Unit = {},
    cardText: String = "",
    job: () -> Unit = { }
) {
    val coroutine = rememberCoroutineScope()
    val cardColor = remember { mutableStateOf(backgroundColor) }
    //var cardText = ""


    /**if (item is JSONObject) {// make dynamic
        CustomerSelected.name = item.get("name").toString()
        CustomerSelected.id = item.get("_id") as String
    }*/
    /**if (item is JSONObject) {
        cardText = if (itemType == ItemType.CLIENT || itemType == ItemType.TASK) {
            item.get("name") as String
        }else item.toString()
    }*/
    Button(
        modifier = Modifier
            .padding(horizontal = 15.dp)
            .height(50.dp),
        onClick = {
            println("------------ This is the customer name $")
            if (isInsideDrawer) coroutine.launch { job() }
            CustomerSelected.name = item.get("name") as String
            CustomerSelected.id = item.get("_id") as String
            navController.navigate(destination)
        },
        colors = ButtonDefaults.buttonColors(
            backgroundColor = cardColor.value
        ),
        shape = RoundedCornerShape(5.dp),
        elevation = null
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .then(modifier)
                .background(Color.Transparent)
        ) {
            Column(modifier = Modifier.fillMaxHeight(), verticalArrangement = Arrangement.Center) {
                Text(
                    text = if (cardText != "") cardText else item.get("name").toString(),
                    color = textColor,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (hasIcon) {
                IconButton(onClick = {
                    // TODO - add conditional logic below for job to run on show/hide pin icon for
                    //  cards in ClientScreen
                    if (isInsideDrawer) coroutine.launch { job() }
                    navController.navigate(destination)
                }) {
                    Icon(
                        imageVector = when (itemType) {
                            ItemType.CLIENT-> if (side == Sides.LEFT) Icons.Outlined.CheckBoxOutlineBlank else Icons.Outlined.PushPin
                            ItemType.TASK -> if (side == Sides.LEFT) Icons.Rounded.Check else Icons.Rounded.ArrowForward
                            else -> icon ?: Icons.Default.BrokenImage // in case of error
                        }, contentDescription = "card-icon", tint = textColor
                    )
                }
            }
        }
    }
}

