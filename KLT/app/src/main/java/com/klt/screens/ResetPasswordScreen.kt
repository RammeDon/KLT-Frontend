package com.klt.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.klt.R

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


@Composable
fun ResetPasswordScreen(
    navController: NavController,
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    /*TODO -- add screen contents here */
}

@Composable
private fun CheckPasswordStrength(
    validateStrengthPassword: Boolean = false,
    onHasStrongPassword: (isStrong: Boolean) -> Unit = {},
    text: String
) {
    Spacer(modifier = Modifier.height(8.dp))
    if (validateStrengthPassword) {
        val strengthPasswordType = strengthChecker(text)
        if (strengthPasswordType == StrengthPasswordTypes.STRONG) {
            onHasStrongPassword(true)
        } else {
            onHasStrongPassword(false)
        }
        Text(
            modifier = Modifier
                .padding(start = 12.dp, top = 10.dp)
                .semantics {
                    contentDescription = "StrengthPasswordMessage"
                }
                .alpha(if (text == "") 0f else 100f),
            text = buildAnnotatedString {
                withStyle(
                    style = SpanStyle(
                        color = Color.DarkGray,
                        fontSize = 10.sp,
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
}
