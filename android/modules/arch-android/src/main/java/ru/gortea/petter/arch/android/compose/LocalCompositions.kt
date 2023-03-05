package ru.gortea.petter.arch.android.compose

import android.content.Context
import androidx.compose.runtime.staticCompositionLocalOf

val LocalApplicationContext = staticCompositionLocalOf<Context> {
    error("Composition local of ApplicationContext not present")
}
