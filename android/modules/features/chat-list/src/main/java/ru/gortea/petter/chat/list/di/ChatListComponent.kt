package ru.gortea.petter.chat.list.di

import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.list.analytics.ChatListAnalyticsController
import ru.gortea.petter.chat.list.data.ChatsListRepository
import ru.gortea.petter.chat.list.navigation.ChatListExternalNodesProvider

interface ChatListComponent {
    val messageRoomRepository: MessageRoomRepository
    val chatsListRepository: ChatsListRepository

    val chatListAnalyticsController: ChatListAnalyticsController
    val chatListNodesProvider: ChatListExternalNodesProvider
}
