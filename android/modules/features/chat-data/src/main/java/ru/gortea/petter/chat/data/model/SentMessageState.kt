package ru.gortea.petter.chat.data.model

import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.data.messages.model.MessageModelState
import java.util.*

internal sealed class SentMessageState(val message: SentMessage) {
    class Loading(message: SentMessage) : SentMessageState(message)
    class Success(message: SentMessage) : SentMessageState(message)
    class Fail(message: SentMessage, val reason: Throwable) : SentMessageState(message)
}

internal fun SentMessageState.toMessageModel(): MessageModel {
    return MessageModel(
        id = UUID.randomUUID().toString(),
        roomId = message.chatRoomId,
        senderId = message.senderId,
        recipientId = message.recipientId,
        content = message.decodedContent,
        dateTime = message.time,
        state = messageModelState()
    )
}

private fun SentMessageState.messageModelState(): MessageModelState {
    return when(this) {
        is SentMessageState.Loading -> MessageModelState.LOADING
        is SentMessageState.Fail -> MessageModelState.FAIL
        is SentMessageState.Success -> MessageModelState.SUCCESS
    }
}
