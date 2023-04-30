package ru.gortea.petter.chat.data.messages

import kotlinx.coroutines.flow.Flow
import ru.gortea.petter.chat.data.messages.model.MessageModel

interface MessageRoomRepository {
    fun messageReceived(message: MessageModel)
    fun roomWithUserOpened(userId: String)
    fun roomWithUserClosed(userId: String)

    fun roomCompanionId(): String?
    fun receivedMessage(): Flow<MessageModel>
}
