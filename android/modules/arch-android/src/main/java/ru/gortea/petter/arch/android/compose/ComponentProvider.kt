package ru.gortea.petter.arch.android.compose

import androidx.compose.runtime.Composable
import ru.gortea.petter.arch.android.ComponentProvider

@Composable
fun<T> getComponent(): T {
    return (LocalApplicationContext.current as ComponentProvider).getComponent()
}
