package ru.gortea.petter.chat.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory

class ChatRepositoryFactory(
    private val messagesListRepositoryFactory: MessagesListRepositoryFactory,
    private val webSocketChatRepositoryFactory: WebSocketChatRepositoryFactory
) {

    fun create(
        conversationId: String,
        coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
    ): ChatRepository {
        return ChatRepository(
            conversationId,
            messagesListRepositoryFactory,
            webSocketChatRepositoryFactory,
            coroutineScope
        )
    }
}
