package ru.gortea.petter.notifications.di

import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.token.storage.TokenRepository
import ru.gortea.petter.token.storage.qualifier.DeviceToken

interface NotificationsServiceComponent {

    @get:DeviceToken
    val tokenRepository: TokenRepository

    val messageRoomRepository: MessageRoomRepository
}
