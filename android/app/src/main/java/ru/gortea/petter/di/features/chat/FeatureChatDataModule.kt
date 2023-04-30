package ru.gortea.petter.di.features.chat

import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import ru.gortea.petter.chat.data.ChatCreateRoomRepository
import ru.gortea.petter.chat.data.ChatRepositoryFactory
import ru.gortea.petter.chat.data.api.ChatsApi
import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.JwtToken

@Module
class FeatureChatDataModule {

    @Provides
    fun provideChatsApi(retrofit: Retrofit): ChatsApi {
        return retrofit.create()
    }

    @Provides
    fun provideMessagesListRepositoryFactory(api: ChatsApi): MessagesListRepositoryFactory {
        return MessagesListRepositoryFactory(api)
    }

    @Provides
    fun provideWebSocketChatRepositoryFactory(
        client: OkHttpClient,
        @JwtToken jwtRepository: TokenRepository
    ): WebSocketChatRepositoryFactory {
        return WebSocketChatRepositoryFactory(client, jwtRepository)
    }

    @Provides
    fun provideChatRepositoryFactory(
        messageRoomRepository: MessageRoomRepository,
        messagesListRepositoryFactory: MessagesListRepositoryFactory,
        webSocketChatRepositoryFactory: WebSocketChatRepositoryFactory
    ): ChatRepositoryFactory {
        return ChatRepositoryFactory(
            messageRoomRepository,
            messagesListRepositoryFactory,
            webSocketChatRepositoryFactory
        )
    }

    @Provides
    fun provideChatCreateRoomRepository(
        chatsApi: ChatsApi,
        profileApi: ProfileApi,
        userRepository: CurrentUserRepository
    ): ChatCreateRoomRepository {
        return ChatCreateRoomRepository(chatsApi, profileApi, userRepository)
    }
}
