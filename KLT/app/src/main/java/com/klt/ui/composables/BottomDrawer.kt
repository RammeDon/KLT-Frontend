package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R

@Composable
fun BottomDrawer(modifier: Modifier = Modifier) {
    val height = LocalConfiguration.current.screenHeightDp / 1.6
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(height.dp) // Remove this
            .background(
                shape = RoundedCornerShape(topEnd = 5.dp, topStart = 5.dp),
                color = colorResource(R.color.KLT_DarkGray1)
            )

    ) {
        Column(modifier = Modifier) {
            Spacer(modifier = Modifier.height(10.dp))
            Divider(
                thickness = 5.dp,
                modifier = Modifier
                    .padding(horizontal = 100.dp)
                    .background(shape = RoundedCornerShape(5.dp), color = Color.White)
            )
        }

    }

}