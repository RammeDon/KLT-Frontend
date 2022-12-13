package com.klt.ui.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun TitledDivider(
    modifier: Modifier = Modifier,
    title: String,
    fontSize: TextUnit = 14.sp

) {

    Row(
        modifier = Modifier
            .padding(vertical = 10.dp)
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .then(modifier)
    ) {
        Divider(
            Modifier
                .weight(14f) // needs to be dynamic
                .padding(top = 8.dp, end = 10.dp),
            color = Color(0xFFD10000),
            thickness = 2.dp
        )
        Spacer(modifier = Modifier.weight(1f))
        Text(text = title, fontSize = fontSize, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.weight(1f))
        Divider(
            Modifier
                .weight(14f) // needs to be dynamic
                .padding(top = 8.dp, start = 10.dp),
            color = Color(0xFFD10000),
            thickness = 2.dp
        )
    }

}