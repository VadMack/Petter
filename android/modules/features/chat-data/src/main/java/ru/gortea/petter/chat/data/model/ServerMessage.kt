package ru.gortea.petter.chat.data.model

import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import ru.gortea.chat.data.messages.model.MessageModel
import ru.gortea.chat.data.messages.model.MessageModelState
import java.time.LocalDateTime

@Serializable
data class ServerMessage(
    val id: String,
    val content: String,
    val senderId: String,
    val recipientId: String,
    @Contextual val sentTime: LocalDateTime
)

fun ServerMessage.toMessageModel(): MessageModel {
    return MessageModel(
        id = id,
        senderId = senderId,
        recipientId = recipientId,
        content = content,
        dateTime = sentTime,
        state = MessageModelState.SUCCESS
    )
}
