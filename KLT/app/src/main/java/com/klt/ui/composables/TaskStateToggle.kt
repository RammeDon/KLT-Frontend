package com.klt.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun TaskStateToggle(
    modifier: Modifier = Modifier
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        Button(
            modifier = Modifier
                .fillMaxSize()
                .weight(1f),
            onClick = {

            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF808080))
        )
        {
            Text(text = "Completed")

        }
        Button(modifier = Modifier
            .fillMaxSize()
            .weight(1f),
            onClick = {
            }
        ) {
            Text(text = "Uncompleted")
        }
    }
}