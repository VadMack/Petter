package ru.gortea.petter.ui_kit

import android.content.Context
import androidx.annotation.StringRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource

sealed interface TextModel {

    class StringText(private val text: String) : TextModel {
        @Composable
        override fun getText(): String = text
        override fun getText(context: Context): String = text
        override fun getStringText(): String = text
    }

    class ResText(@StringRes private val textRes: Int) : TextModel {
        @Composable
        override fun getText(): String = stringResource(textRes)
        override fun getText(context: Context): String = context.getString(textRes)
        override fun getStringText(): String = " "
    }

    @Composable
    fun getText(): String
    fun getText(context: Context): String
    fun getStringText(): String

    companion object {
        operator fun invoke(text: String): TextModel = StringText(text)
        operator fun invoke(@StringRes textRes: Int): TextModel = ResText(textRes)
    }
}
