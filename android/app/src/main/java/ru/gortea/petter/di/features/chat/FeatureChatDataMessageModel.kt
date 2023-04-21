package ru.gortea.petter.di.features.chat

import dagger.Module
import dagger.Provides
import ru.gortea.chat.data.messages.InMemoryMessageRoomRepository
import ru.gortea.chat.data.messages.MessageRoomRepository
import javax.inject.Singleton

@Module
class FeatureChatDataMessageModel {

    @Singleton
    @Provides
    fun provideLastMessageRepository(): MessageRoomRepository {
        return InMemoryMessageRoomRepository()
    }
}
