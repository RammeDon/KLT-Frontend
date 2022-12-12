package com.klt.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/*
 *
 *  I was not sure whether or not this would qualify as a "screen" per sé, as I think it is
 * possible to simply have it as an overlay over the existing context. Can you let me know your
 * thoughts on this? If the "swipe up" ends up being too complex we can always simplify and
 * simply build a different view/screen for this feature.
 *
 */

@Composable
fun CreateTaskScreen(
    modifier: Modifier = Modifier,
    OnSelfClick: () -> Unit = {}
) {
    /*TODO */
}
