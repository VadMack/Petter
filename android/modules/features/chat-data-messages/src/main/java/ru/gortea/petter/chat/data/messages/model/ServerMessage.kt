package ru.gortea.petter.chat.data.messages.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime

@Serializable
data class ServerMessage(
    val id: String,
    val chatRoomId: String,
    val content: String,
    val senderId: String,
    val recipientId: String,
    @Contextual val sentTime: LocalDateTime
)

fun ServerMessage.toMessageModel(): MessageModel {
    return MessageModel(
        id = id,
        roomId = chatRoomId,
        senderId = senderId,
        recipientId = recipientId,
        content = content,
        dateTime = sentTime,
        state = MessageModelState.SUCCESS
    )
}
