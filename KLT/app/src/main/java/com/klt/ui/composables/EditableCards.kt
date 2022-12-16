package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun EditableCards(
    modifier: Modifier = Modifier,
    editOn: Boolean = false,
    icon: ImageVector? = null,

    text: String,
    editPassword: Boolean = false

) {
    var nameEdit by remember {
        mutableStateOf("")
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
    )
    {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp)
                .background(color = Color.LightGray, shape = RoundedCornerShape(5.dp))
                .padding(start = 15.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = "name-Icon")
            }
            Spacer(modifier = Modifier.width(20.dp))

            if (!editOn) {
                Text(text = text)
            }
            if (editOn && editPassword) {
                PasswordTextField()
            }
            if (editOn && !editPassword) {
                TextField(
                    value = nameEdit,
                    onValueChange = { nameEdit = it },
                    placeholder = {
                        Text(
                            text = text
                        )
                    },
                    modifier = Modifier
                        .background(color = Color.LightGray)
                        .weight(1f)
                )
            }

        }
    }

}