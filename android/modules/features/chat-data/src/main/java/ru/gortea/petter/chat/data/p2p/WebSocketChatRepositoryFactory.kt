package ru.gortea.petter.chat.data.p2p

import okhttp3.OkHttpClient
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.token.storage.TokenRepository

class WebSocketChatRepositoryFactory(
    private val okHttpClient: OkHttpClient,
    private val jwtRepository: TokenRepository,
    private val userRepository: UserLocalRepository
) {

    internal fun create(conversationId: String): WebSocketChatRepository {
        return WebSocketChatRepository(conversationId, okHttpClient, jwtRepository, userRepository)
    }
}
