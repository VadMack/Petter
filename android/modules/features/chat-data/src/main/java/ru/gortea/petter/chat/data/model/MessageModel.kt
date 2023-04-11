package ru.gortea.petter.chat.data.model

data class MessageModel(
    val id: String,
    val senderId: String,
    val recipientId: String,
    val content: String,
    val time: String,
    val state: MessageModelState
)

enum class MessageModelState {
    LOADING, SUCCESS, FAIL
}
