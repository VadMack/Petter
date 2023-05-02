package ru.gortea.petter.arch.android.compose

import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.platform.LocalContext
import ru.gortea.petter.arch.android.util.toast

@Composable
fun ToastIfTrue(value: Boolean, @StringRes textRes: Int) {
    val context = LocalContext.current

    value.LaunchedEffect {
        if (value) {
            context.toast(textRes)
        }
    }
}

@Composable
fun Boolean.LaunchedEffect(action: () -> Unit) {
    val value = rememberSaveable(this) { this }

    LaunchedEffect(key1 = value) {
        action()
    }
}
