package ru.gortea.petter.chat.list.ui.state.model

import androidx.compose.ui.graphics.Color

internal data class MessageUiModel(
    val id: String = "",
    val content: String = "",
    val dateTime: String = "",
    val authorNameRes: Int? = null,
    val iconRes: Int? = null,
    val iconTint: Color? = null,
)
