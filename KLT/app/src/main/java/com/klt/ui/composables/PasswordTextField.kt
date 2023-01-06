package com.klt.ui.composables

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.klt.R
import com.klt.util.HashUtils
import kotlinx.coroutines.launch

private fun strengthChecker(password: String): StrengthPasswordTypes =
    when {
        REGEX_STRONG_PASSWORD.toRegex().containsMatchIn(password) -> StrengthPasswordTypes.STRONG
        else -> StrengthPasswordTypes.WEAK
    }

enum class StrengthPasswordTypes {
    STRONG,
    WEAK
}

private const val REGEX_STRONG_PASSWORD =
    "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~\$^+=<>]).{8,20}\$"

var pwContainer: String = ""
var isMatching: Boolean = false

@Composable
fun PasswordTextField(
    labelText: String = "Password..",
    semanticContentDescription: String = "",
    hasError: Boolean = false,
    title: String = "",
    checkPasswordStrength: Boolean = false,
    performMatchCheck: Boolean = false,
    singleLine: Boolean = true,
    onUpdate: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current
    val showPassword = remember { mutableStateOf(false) }
    var pwStateValue: String by remember {
        mutableStateOf("")
    }
    var hashedPW: String by remember {
        mutableStateOf(HashUtils.sha256(pwStateValue))
    }
    var isMatch by remember {
        mutableStateOf(false)
    }

    LaunchedEffect(pwStateValue) {
        launch {
            hashedPW = HashUtils.sha256(pwStateValue)
            if (performMatchCheck) {
                isMatch = (pwContainer == hashedPW && pwStateValue != "")
                isMatching = isMatch // update global var
            } else pwContainer = hashedPW
        }
    }
    Column(modifier = Modifier) {
        if (title != "")
            TextFieldTitle(title)
        TextField(
            modifier = Modifier
                .fillMaxWidth()
                .semantics { contentDescription = semanticContentDescription },
            value = pwStateValue,
            label = { Text(labelText) },
            onValueChange = {
                if (it != " ") pwStateValue = it

                onUpdate(it)
            },
            keyboardOptions = KeyboardOptions.Default.copy(
                autoCorrect = true,
                keyboardType = KeyboardType.Text,
                imeAction = ImeAction.Done
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            singleLine = singleLine,
            isError = hasError,
            visualTransformation = if (showPassword.value) VisualTransformation.None else PasswordVisualTransformation(),
        )
        if (checkPasswordStrength) CheckPasswordStrength(text = pwStateValue)
        if (performMatchCheck) {
            Text(
                text = buildAnnotatedString {
                    withStyle(
                        style = SpanStyle(
                            color = Color.Red,
                            fontSize = 14.sp,
                        )
                    ) {
                        append("Passwords do not match")
                    }
                },
                color = Color.Red,
                modifier = Modifier
                    .alpha(if (isMatch || pwStateValue == "") 0f else 100f)
                    .padding(top = 3.dp)
            )
        }
    }
}


@Composable
private fun CheckPasswordStrength(
    validateStrengthPassword: Boolean = true,
    onHasStrongPassword: (isStrong: Boolean) -> Unit = {},
    text: String
) {
    if (validateStrengthPassword) {
        val strengthPasswordType = strengthChecker(text)
        if (strengthPasswordType == StrengthPasswordTypes.STRONG) {
            onHasStrongPassword(true)
        } else {
            onHasStrongPassword(false)
        }
        Text(
            modifier = Modifier
                .padding(top = 3.dp)
                .semantics {
                    contentDescription = "StrengthPasswordMessage"
                }
                .alpha(if (text == "") 0f else 100f),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.DarkGray,
                        fontSize = 14.sp,
                    )
                ) {
                    append(stringResource(id = R.string.warning_password_level))
                    withStyle(
                        style = SpanStyle(
                            color = if (strengthPasswordType == StrengthPasswordTypes.STRONG)
                                Color(0xFF52c202)
                            else Color(0xFFD10000)
                        )
                    ) {
                        when (strengthPasswordType) {
                            StrengthPasswordTypes.STRONG ->
                                append(
                                    " ${
                                        stringResource(id = R.string.warning_password_level_strong)
                                    }"
                                )
                            StrengthPasswordTypes.WEAK ->
                                append(
                                    " ${
                                        stringResource(id = R.string.warning_password_level_weak)
                                    }"
                                )
                        }
                    }
                }
            }
        )
    }
    Spacer(modifier = Modifier.height(24.dp))
}
