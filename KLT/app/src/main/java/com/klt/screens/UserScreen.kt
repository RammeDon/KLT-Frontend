package com.klt.screens

import android.content.ContentValues.TAG
import android.content.Context
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Phone
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.google.gson.Gson
import com.klt.R
import com.klt.ui.composables.EditableCards
import com.klt.ui.composables.KLTDivider
import com.klt.ui.composables.PasswordTextField
import com.klt.ui.composables.ScreenSubTitle
import com.klt.ui.navigation.Login
import com.klt.ui.navigation.ResetPassword
import com.klt.util.ApiConnector
import com.klt.util.ApiResult
import com.klt.util.HttpStatus
import com.klt.util.LocalStorage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.serialization.json.JsonObject
import kotlinx.serialization.json.JsonObjectBuilder
import org.json.JSONObject

/*  ------  TODO: When time for adding functionality the this kotlin file will be turned into a
*            class which will have EditableCards as well. This is for more manageable handling of
*              input data and CRUD calls to input or collect data from the database.  */



@Composable
fun UserScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {

    val currUserID = ""
    val context = LocalContext.current
    val coroutine = rememberCoroutineScope()
    var userObj = JSONObject()
    var firstName by remember { mutableStateOf("") }
    var lastName by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var userFetched by remember { mutableStateOf(false) }

    LaunchedEffect(currUserID) {
        if (!userFetched) {
            coroutine.launch(Dispatchers.IO) {
                ApiConnector.getUserData(LocalStorage.getToken(context), onRespond = { user ->
                    userObj = user.data()
                    Log.d(TAG, userObj.toString())
                    //currUserID = userObj.get("_id") as String
                    firstName = userObj.get("firstName") as String
                    lastName = userObj.get("lastName") as String
                    email = userObj.get("email") as String
                })
            }
        }
        userFetched = true

    }

    var editState by remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .then(modifier)
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            ScreenSubTitle(
                navController = navController,
                onBackNavigation = navController.previousBackStackEntry?.destination?.route.toString(),
                bigText = "Return to " + navController.previousBackStackEntry?.destination?.route.toString(),
                smallText = ""
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(30.dp)
            )
            Text(
                text = "Profile",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                fontSize = 35.sp
            )
            KLTDivider()
            Spacer(modifier = Modifier.height(20.dp))
            Column(
                modifier = Modifier,
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                EditableCards(
                    text = firstName,
                    icon = Icons.Outlined.Person,
                    editOn = editState,
                    editValue = {firstName = it}
                )
                EditableCards(
                    text = lastName,
                    icon = Icons.Outlined.Person,
                    editOn = editState,
                    editValue = {lastName = it}
                )/**
                EditableCards(
                    text = "+46 0734587234",
                    icon = Icons.Outlined.Phone,
                    editOn = editState,
                    editValue = {firstName = it}
                )*/
                EditableCards(
                    text = email,
                    icon = Icons.Outlined.Email,
                    editOn = editState,
                    editValue = {email = it}
                )/**
                if (editState) { // instead of textfield it should be passwordtextfield
                    Box(modifier = Modifier.padding(horizontal = 30.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(start = 15.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {

                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "name-Icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            PasswordTextField(
                                labelText = "New Password",
                                checkPasswordStrength = true,
                                onUpdate = {}
                            )
                        }
                    }
                    Box(modifier = Modifier.padding(horizontal = 30.dp)) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(80.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(5.dp)
                                )
                                .padding(start = 15.dp),
                            verticalAlignment = Alignment.Top
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .padding(top = 15.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Filled.Lock,
                                    contentDescription = "name-Icon"
                                )
                            }
                            Spacer(modifier = Modifier.width(20.dp))
                            PasswordTextField(
                                labelText = "confirm Password",
                                performMatchCheck = true,
                                onUpdate = {}
                            )
                        }
                    }
                } else {
                    EditableCards(
                        text = "**************",
                        icon = Icons.Filled.Lock,
                        editOn = editState
                    )
                }
*/
            }
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 20.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )

                if (!editState) {
                    Button(modifier = Modifier
                        .width(180.dp)
                        .height(40.dp),
                        colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.KLT_Red)),
                        onClick = {
                            navController.navigate(ResetPassword.route)

                        }) {
                        Text(
                            text = "Change Password",
                            color = Color.White
                        )
                    }
                }

                Spacer(modifier = Modifier.height(10.dp))
                Button(modifier = Modifier
                    .width(180.dp)
                    .height(40.dp),
                    colors = ButtonDefaults.buttonColors(backgroundColor = colorResource(id = R.color.KLT_Red)),
                    onClick = {
                        if (editState) {
                            coroutine.launch(Dispatchers.IO) {
                                updateUser(firstName, lastName, email, userObj, context)
                            }
                        }
                        editState = !editState
                    }) {
                    Text(
                        text = if (!editState) "Edit Profile" else "Save Changes",
                        color = Color.White
                    )
                }
            }

        }

    }
}

private fun updateUser(
    firstname: String,
    lastname: String,
    email: String,
    userObj: JSONObject,
    context: Context
) {
    userObj.put("firstName", firstname)
    userObj.put("lastName", lastname)
    userObj.put("email", email)

    val onUserEditRespond: (ApiResult) -> Unit = { apiResult ->
        when (apiResult.status()) {
            HttpStatus.SUCCESS -> {
                Looper.prepare()
                Toast.makeText(context, "Edit is successful!", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
            HttpStatus.UNAUTHORIZED -> {
                Looper.prepare()
                Toast.makeText(context, "Error: Unauthorized", Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
            HttpStatus.FAILED -> {
                Looper.prepare()
                Toast.makeText(context, "Error: Operation failed!", Toast.LENGTH_LONG).show()
                Looper.loop()
            }
        }
    }
    ApiConnector.editUser(
        token = LocalStorage.getToken(context),
        jsonData = userObj,
        onRespond = onUserEditRespond
    )

}
