package com.klt.ui.composables


import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.klt.R
import com.klt.screens.Customer
import com.klt.screens.KLTItem
import com.klt.screens.Task
import org.json.JSONArray

@Composable
fun LazyWindow(
    modifier: Modifier = Modifier,
    navController: NavController,
    destination: String,
    items: JSONArray,
    repeats: Int = 1,
    color: Color,
    icon: ImageVector? = null,
    collapsed: Boolean = false,
) {
    LazyColumn(
        // TODO -- ADD VERTICAL ARRANGEMENT AND TAKE OUT REPEATS
        modifier = Modifier
            .fillMaxHeight()
            .then(modifier),
    ) {
        (0 until items.length()).forEach {
            val customer = items.getJSONObject(it)
            item{
                Spacer(modifier = Modifier.height(7.dp))
                EntryCard(
                    item = customer,
                    textColor = Color.Black,
                    navController = navController,
                    destination = destination,
                    backgroundColor = Color.LightGray
                )
            }
        }
    }
}
