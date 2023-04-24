package ru.gortea.petter.chat.presentation

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.chat.di.ChatComponent
import ru.gortea.petter.chat.navigation.ChatNavTarget
import ru.gortea.petter.chat.presentation.actors.ChatCreateRoomActor
import ru.gortea.petter.chat.presentation.actors.ChatInitCreateRoomActor
import ru.gortea.petter.chat.presentation.actors.ChatInitMessagesActor
import ru.gortea.petter.chat.presentation.actors.ChatLoadPageActor
import ru.gortea.petter.chat.presentation.actors.ChatSendMessageActor
import ru.gortea.petter.navigation.PetterRouter

internal typealias ChatStore = MviStore<ChatState, ChatEvent, Nothing>

internal fun ChatCreateRoomStore(
    userId: String,
    router: PetterRouter<ChatNavTarget>,
    component: ChatComponent
): ChatStore {
    val repository = component.createRoomRepository
    return TeaStore(
        initialState = ChatState.RoomCreationChatState(),
        reducer = ChatReducer(router),
        actors = listOf(
            ChatInitCreateRoomActor(repository),
            ChatCreateRoomActor(repository)
        ),
        initialEvents = listOf(
            ChatEvent.InitApi,
            ChatRoomCreationEvent.CreateChatRoom(userId)
        )
    )
}

internal fun ChatStore(
    room: ChatRoomModel,
    router: PetterRouter<ChatNavTarget>,
    component: ChatComponent
): ChatStore {
    val pageSize = 2
    val chatRepository = component.chatRepositoryFactory.create(
        conversationId = room.id,
        senderId = room.currentUser.id,
        recipientId = room.companion.id,
        pageSize = pageSize,
        encryptionKey = room.encryptionKey
    )

    return TeaStore(
        initialState = ChatState.ContentChatState(
            room.id,
            room.currentUser,
            room.companion,
            pageSize
        ),
        reducer = ChatReducer(router),
        actors = listOf(
            ChatInitMessagesActor(chatRepository),
            ChatLoadPageActor(chatRepository),
            ChatSendMessageActor(chatRepository)
        ),
        initialEvents = listOf(
            ChatEvent.InitApi,
            ChatUiEvent.LoadPage
        )
    )
}
