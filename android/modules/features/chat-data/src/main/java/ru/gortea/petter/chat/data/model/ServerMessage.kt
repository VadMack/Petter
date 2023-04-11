package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Serializable

@Serializable
data class ServerMessage(
    val id: String,
    val content: String,
    val senderId: String,
    val recipientId: String,
    val time: String
)
