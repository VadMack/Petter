package ru.gortea.petter.chat.ui.model

import androidx.compose.ui.graphics.Color

internal data class MessageUiModel(
    val id: String,
    val gravity: MessageGravity,
    val content: String,
    val dateTime: String,
    val iconRes: Int?,
    val iconTint: Color?
)
