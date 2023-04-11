package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.mvi.actors.ChatDataDisconnectSocketActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataInitMessagesActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataLoadPageActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSendMessageActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSubscribeMessagesActor
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory

internal typealias ChatDataStore = MviStore<ChatDataState, ChatDataEvent, Nothing>

internal fun ChatDataStore(
    conversationId: String,
    messagesListRepositoryFactory: MessagesListRepositoryFactory,
    webSocketChatRepositoryFactory: WebSocketChatRepositoryFactory
): ChatDataStore {
    val messagesListRepository = messagesListRepositoryFactory.create(conversationId)
    val webSocketChatRepository = webSocketChatRepositoryFactory.create(conversationId)

    return TeaStore(
        initialState = ChatDataState(),
        reducer = ChatDataReducer(),
        actors = listOf(
            ChatDataSendMessageActor(webSocketChatRepository),
            ChatDataSubscribeMessagesActor(webSocketChatRepository),
            ChatDataDisconnectSocketActor(webSocketChatRepository),
            ChatDataInitMessagesActor(messagesListRepository),
            ChatDataLoadPageActor(messagesListRepository)
        ),
        initialEvents = listOf(
            ChatDataEvent.Internal.InitApi,
            ChatDataEvent.User.LoadPage
        )
    )
}
