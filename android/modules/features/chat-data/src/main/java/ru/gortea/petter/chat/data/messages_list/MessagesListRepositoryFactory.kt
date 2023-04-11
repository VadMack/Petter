package ru.gortea.petter.chat.data.messages_list

import ru.gortea.petter.chat.data.api.ChatsApi

class MessagesListRepositoryFactory(
    private val api: ChatsApi
) {
    internal fun create(conversationId: String): MessagesListRepository {
        return MessagesListRepository(conversationId, api)
    }
}
