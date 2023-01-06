package com.klt.ui.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klt.util.ITask

@Composable
fun GoalInput(
    modifier: Modifier = Modifier,
    goal: ITask.IGoal,
    onUpdate: (Any) -> Unit
) {



    /** Get correct keyboard from goal type */
    var data: String by remember {
        if (goal.type == ITask.GoalDataTypes.Boolean){
            onUpdate("false")
            mutableStateOf("false")
        } else mutableStateOf("")
    }
    fun getKeyBoardType(): KeyboardType {
        return when (goal.type) {
            ITask.GoalDataTypes.Number -> KeyboardType.Number
            ITask.GoalDataTypes.Text -> KeyboardType.Text
            ITask.GoalDataTypes.Boolean -> {
                return KeyboardType.Text
            }
        }
    }


    LaunchedEffect(key1 = data) {
        onUpdate(data)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.padding(vertical = 5.dp).then(modifier)) {

        Text(
            text = goal.name,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Left,
            color = Color.Black,
            fontSize = 12.sp
        )

        if (goal.type == ITask.GoalDataTypes.Boolean) {
            Box(
                contentAlignment = Alignment.CenterStart,
                modifier = Modifier.fillMaxWidth()
            ){
                Checkbox(
                    checked = (data == "true"),
                    onCheckedChange = { data = if (data == "true") "false" else "true" },
                )
            }
        } else {
            TextField(
                value = data,
                onValueChange = {data = it},
                singleLine = true,
                keyboardOptions = KeyboardOptions(
                    keyboardType = getKeyBoardType()
                ),
                placeholder = { Text(text = "Value..") },
                modifier = Modifier
                    .fillMaxWidth()
            )
        }
    }
}