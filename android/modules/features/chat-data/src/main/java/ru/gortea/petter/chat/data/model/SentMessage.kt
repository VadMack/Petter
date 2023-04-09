package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class SentMessage(
    val content: String,
    val senderId: String,
    val recipientId: String
)
