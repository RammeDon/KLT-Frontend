package com.klt.ui.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R

@Composable
fun KLTDivider(modifier: Modifier = Modifier) {
    Divider(
        Modifier
            .padding(top = 8.dp)
            .padding(horizontal = 30.dp)
            .alpha(0.5f)
            .then(modifier),
        color = colorResource(id = R.color.KLT_Red),
        thickness = 2.dp
    )
}
