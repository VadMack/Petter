package ru.gortea.petter.chat.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.gortea.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory

class ChatRepositoryFactory(
    private val messageRoomRepository: MessageRoomRepository,
    private val messagesListRepositoryFactory: MessagesListRepositoryFactory,
    private val webSocketChatRepositoryFactory: WebSocketChatRepositoryFactory
) {

    fun create(
        conversationId: String,
        encryptionKey: String,
        senderId: String,
        recipientId: String,
        pageSize: Int,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ): ChatRepository {
        return ChatRepository(
            conversationId = conversationId,
            encryptionKey = encryptionKey,
            senderId = senderId,
            recipientId = recipientId,
            pageSize = pageSize,
            messageRoomRepository = messageRoomRepository,
            messagesListRepositoryFactory = messagesListRepositoryFactory,
            webSocketChatRepositoryFactory = webSocketChatRepositoryFactory,
            coroutineScope = coroutineScope
        )
    }
}
