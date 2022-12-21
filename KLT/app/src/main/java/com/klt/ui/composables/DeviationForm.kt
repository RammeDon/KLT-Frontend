package com.klt.ui.composables

import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R

enum class Deviations(val text: String, val icon: ImageVector) {
    BREAK("Break", Icons.Rounded.Coffee),
    TOILET("Toilet break", Icons.Rounded.Wc),
    PHONE("Phone call", Icons.Rounded.Phone),
    GUIDE_DRIVER("Guide driver", Icons.Rounded.Help)
}

@Composable
fun DeviationForm(onConfirm: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 40.dp)
            .verticalScroll(rememberScrollState())
            .fillMaxSize()
    ) {

        OneLineInputForm(
            text = "What is the reason for this pause?",
            onConfirm = { onConfirm(it) },
            placeholder = "Deviation.."
        )

        Text(
            text = "Or select an predefined deviation",
            modifier = Modifier.padding(vertical = 10.dp),
            color = Color.LightGray
        )

        Deviations.values().forEach {
            Button(
                onClick = { onConfirm(it.text) },
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = colorResource(id = R.color.KLT_Red)
                ),
                shape = RoundedCornerShape(5.dp),
                elevation = null,
                modifier = Modifier.padding(vertical = 5.dp)
            ){
                Row(modifier = Modifier.padding(5.dp)) {
                    Column(verticalArrangement = Arrangement.Center) {
                        Text(
                            text = it.text,
                            color = Color.White
                        )
                    }
                    Spacer(modifier = Modifier.weight(1f))
                    Icon(
                        imageVector = it.icon,
                        tint = Color.White,
                        contentDescription = "card-icon"
                    )
                }
            }
        }
    }
}