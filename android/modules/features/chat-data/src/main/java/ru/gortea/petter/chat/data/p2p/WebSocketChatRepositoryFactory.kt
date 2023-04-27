package ru.gortea.petter.chat.data.p2p

import okhttp3.OkHttpClient
import ru.gortea.petter.chat.data.encryption.MessageEncryptor
import ru.gortea.petter.token.storage.TokenRepository

class WebSocketChatRepositoryFactory(
    private val okHttpClient: OkHttpClient,
    private val jwtRepository: TokenRepository
) {

    internal fun create(
        conversationId: String,
        encryptor: MessageEncryptor,
        senderId: String,
        recipientId: String
    ): WebSocketChatRepository {
        return WebSocketChatRepository(
            conversationId = conversationId,
            encryptor = encryptor,
            recipientId = recipientId,
            senderId = senderId,
            okHttpClient = okHttpClient,
            jwtRepository = jwtRepository
        )
    }
}
