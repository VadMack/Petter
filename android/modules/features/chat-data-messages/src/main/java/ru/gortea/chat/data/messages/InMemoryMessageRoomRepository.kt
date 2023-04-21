package ru.gortea.chat.data.messages

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import ru.gortea.chat.data.messages.model.MessageModel

class InMemoryMessageRoomRepository : MessageRoomRepository {
    private var currentCompanionId: String? = null
    private val messageReceived = MutableSharedFlow<MessageModel>(
        replay = 3,
        extraBufferCapacity = 16
    )

    override fun messageReceived(message: MessageModel) {
        messageReceived.tryEmit(message)
    }

    override fun roomWithUserOpened(userId: String) {
        currentCompanionId = userId
    }

    override fun roomWithUserClosed(userId: String) {
        if (currentCompanionId == userId) {
            currentCompanionId = null
        }
    }

    override fun roomCompanionId(): String? = currentCompanionId

    override fun receivedMessage(): SharedFlow<MessageModel> = messageReceived.asSharedFlow()
}
