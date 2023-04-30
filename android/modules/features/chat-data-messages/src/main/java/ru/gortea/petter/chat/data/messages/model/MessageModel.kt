package ru.gortea.petter.chat.data.messages.model

import java.time.LocalDateTime

data class MessageModel(
    val id: String,
    val roomId: String,
    val senderId: String,
    val recipientId: String,
    val content: String,
    val dateTime: LocalDateTime,
    val state: MessageModelState
)

enum class MessageModelState {
    LOADING, SUCCESS, FAIL
}
