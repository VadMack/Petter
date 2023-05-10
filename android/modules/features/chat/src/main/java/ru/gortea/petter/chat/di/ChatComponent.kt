package ru.gortea.petter.chat.di

import ru.gortea.petter.chat.analytics.ChatAnalyticsController
import ru.gortea.petter.chat.data.ChatCreateRoomRepository
import ru.gortea.petter.chat.data.ChatRepositoryFactory
import ru.gortea.petter.chat.navigation.ChatExternalNodesProvider

interface ChatComponent {
    val createRoomRepository: ChatCreateRoomRepository
    val chatRepositoryFactory: ChatRepositoryFactory

    val chatAnalyticsController: ChatAnalyticsController
    val chatNodesProvider: ChatExternalNodesProvider
}
