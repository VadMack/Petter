package ru.gortea.petter.arch.android.util

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes

fun Context.toast(@StringRes textRes: Int, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, textRes, length).show()
}
