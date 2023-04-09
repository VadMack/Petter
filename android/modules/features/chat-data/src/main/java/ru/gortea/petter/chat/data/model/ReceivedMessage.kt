package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ReceivedMessage(
    val content: String,
    val time: String
)
