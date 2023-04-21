package ru.gortea.petter.chat.di

import ru.gortea.petter.chat.data.ChatCreateRoomRepository
import ru.gortea.petter.chat.data.ChatRepositoryFactory

interface ChatComponent {
    val createRoomRepository: ChatCreateRoomRepository
    val chatRepositoryFactory: ChatRepositoryFactory
}
