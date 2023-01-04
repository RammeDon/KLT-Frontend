package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.klt.R

enum class FormAlertMsgState {
    GOOD,
    BAD,
    NOT_ACTIVE
}

@Composable
fun FormAlertMsg(
    msg: String,
    state: FormAlertMsgState
) {
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(10.dp))
            .background(
                when (state) {
                    FormAlertMsgState.GOOD -> colorResource(id = R.color.green)
                    FormAlertMsgState.BAD -> colorResource(id = R.color.KLT_Red)
                    FormAlertMsgState.NOT_ACTIVE -> Color.Transparent
                }
            )
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(
                vertical = if (state == FormAlertMsgState.NOT_ACTIVE) 0.dp else 20.dp,
                horizontal = 10.dp
            )
    ) {
        Text(
            text = msg,
            color = if (state == FormAlertMsgState.NOT_ACTIVE) Color.Transparent else Color.White,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
        )
    }
}