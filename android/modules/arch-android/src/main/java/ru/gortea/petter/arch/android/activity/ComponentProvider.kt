package ru.gortea.petter.arch.android.activity

import android.app.Activity
import android.app.Service
import ru.gortea.petter.arch.android.ComponentProvider

fun<T: Any> Activity.getComponent(): T {
    return (application as ComponentProvider).getComponent()
}

fun<T: Any> Service.getComponent(): T {
    return (application as ComponentProvider).getComponent()
}
