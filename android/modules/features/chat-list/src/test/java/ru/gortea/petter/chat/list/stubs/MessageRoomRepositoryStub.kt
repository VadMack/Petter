package ru.gortea.petter.chat.list.stubs

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.data.messages.model.MessageModel

internal class MessageRoomRepositoryStub : MessageRoomRepository {
    private val receivedMessage = MutableSharedFlow<MessageModel>(replay = 1)

    override fun messageReceived(message: MessageModel) {
        receivedMessage.tryEmit(message)
    }

    override fun receivedMessage(): Flow<MessageModel> = receivedMessage

    override fun roomWithUserOpened(userId: String) = Unit
    override fun roomWithUserClosed(userId: String) = Unit
    override fun roomCompanionId(): String? = null
}
