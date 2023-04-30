package ru.gortea.petter.chat.list.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.chat.list.di.ChatListComponent
import ru.gortea.petter.chat.list.navigation.ChatListNavTarget
import ru.gortea.petter.chat.list.presentation.actors.ChatListInitLoadActor
import ru.gortea.petter.chat.list.presentation.actors.ChatListLastMessageActor
import ru.gortea.petter.chat.list.presentation.actors.ChatListLoadActor
import ru.gortea.petter.navigation.PetterRouter

internal typealias ChatListStore = MviStore<ChatListState, ChatListEvent>

internal fun createChatListStore(
    router: PetterRouter<ChatListNavTarget>,
    component: ChatListComponent
): ChatListStore {
    val chatsListRepository = component.chatsListRepository
    val messageRoomRepository = component.messageRoomRepository

    return TeaStore(
        initialState = ChatListState(),
        reducer = ChatListReducer(router),
        actors = listOf(
            ChatListInitLoadActor(chatsListRepository),
            ChatListLoadActor(chatsListRepository),
            ChatListLastMessageActor(messageRoomRepository)
        ),
        initialEvents = listOf(
            ChatListEvent.InitApi,
            ChatListEvent.ObserveLastMessage,
            ChatListUiEvent.LoadChats
        )
    )
}
