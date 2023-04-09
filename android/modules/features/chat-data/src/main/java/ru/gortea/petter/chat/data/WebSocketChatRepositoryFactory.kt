package ru.gortea.petter.chat.data

import okhttp3.OkHttpClient
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.token.storage.TokenRepository

class WebSocketChatRepositoryFactory(
    private val okHttpClient: OkHttpClient,
    private val jwtRepository: TokenRepository,
    private val userRepository: UserLocalRepository
) {

    fun create(userId: String): WebSocketChatRepository {
        return WebSocketChatRepository(userId, okHttpClient, jwtRepository, userRepository)
    }
}
