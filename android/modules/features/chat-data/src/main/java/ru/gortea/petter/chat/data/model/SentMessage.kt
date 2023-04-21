package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
internal class SentMessage(
    val chatRoomId: String,
    val content: String,
    val senderId: String,
    val recipientId: String,
    @Contextual val time: LocalDateTime = LocalDateTime.now()
)
