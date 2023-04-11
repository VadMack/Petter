package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@Serializable
internal class SentMessage(
    val content: String,
    val senderId: String,
    val recipientId: String,
    val time: String = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_TIME)
)
