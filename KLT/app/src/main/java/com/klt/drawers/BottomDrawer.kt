package com.klt.drawers

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.klt.R

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun BottomDrawer(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit = {}
) {
    Column(
        modifier = Modifier
            .padding(horizontal = 10.dp)
            .padding(bottom = 10.dp)
    ) {
        Spacer(modifier = Modifier.height(10.dp))
        Divider(
            thickness = 7.dp,
            modifier = Modifier
                .padding(horizontal = 130.dp)
                .background(
                    shape = RoundedCornerShape(5.dp),
                    color = colorResource(R.color.KLT_DarkGray2)
                )
        )
        Spacer(
            modifier = Modifier
                .height(30.dp)
        )
        content()
    }
}
