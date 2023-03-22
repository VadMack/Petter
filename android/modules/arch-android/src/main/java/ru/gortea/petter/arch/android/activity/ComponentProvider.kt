package ru.gortea.petter.arch.android.activity

import android.app.Activity
import ru.gortea.petter.arch.android.ComponentProvider

fun<T: Any> Activity.getComponent(): T {
    return (application as ComponentProvider).getComponent()
}
