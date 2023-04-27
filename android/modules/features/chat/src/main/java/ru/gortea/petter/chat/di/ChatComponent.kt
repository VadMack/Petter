package ru.gortea.petter.chat.di

import ru.gortea.petter.chat.data.ChatCreateRoomRepository
import ru.gortea.petter.chat.data.ChatRepositoryFactory
import ru.gortea.petter.chat.navigation.ChatExternalNodesProvider

interface ChatComponent {
    val createRoomRepository: ChatCreateRoomRepository
    val chatRepositoryFactory: ChatRepositoryFactory

    val chatNodesProvider: ChatExternalNodesProvider
}
