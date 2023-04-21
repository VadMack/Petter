package ru.gortea.petter.chat.data.p2p

import okhttp3.OkHttpClient
import ru.gortea.petter.token.storage.TokenRepository

class WebSocketChatRepositoryFactory(
    private val okHttpClient: OkHttpClient,
    private val jwtRepository: TokenRepository
) {

    internal fun create(
        conversationId: String,
        senderId: String,
        recipientId: String
    ): WebSocketChatRepository {
        return WebSocketChatRepository(
            conversationId = conversationId,
            recipientId = recipientId,
            senderId = senderId,
            okHttpClient = okHttpClient,
            jwtRepository = jwtRepository
        )
    }
}
