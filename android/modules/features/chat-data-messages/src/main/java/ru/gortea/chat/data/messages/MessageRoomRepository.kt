package ru.gortea.chat.data.messages

import kotlinx.coroutines.flow.SharedFlow
import ru.gortea.chat.data.messages.model.MessageModel

interface MessageRoomRepository {
    fun messageReceived(message: MessageModel)
    fun roomWithUserOpened(userId: String)
    fun roomWithUserClosed(userId: String)

    fun roomCompanionId(): String?
    fun receivedMessage(): SharedFlow<MessageModel>
}
