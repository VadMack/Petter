package ru.gortea.petter.chat.data.mvi

import ru.gortea.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.mvi.actors.ChatDataDisconnectSocketActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataInitMessagesActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataLoadPageActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSendMessageActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSubscribeLifecycleActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSubscribeMessagesActor
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory

internal typealias ChatDataStore = MviStore<ChatDataState, ChatDataEvent, Nothing>

internal fun ChatDataStore(
    conversationId: String,
    senderId: String,
    recipientId: String,
    pageSize: Int,
    messageRoomRepository: MessageRoomRepository,
    messagesListRepositoryFactory: MessagesListRepositoryFactory,
    webSocketChatRepositoryFactory: WebSocketChatRepositoryFactory
): ChatDataStore {
    val messagesListRepository = messagesListRepositoryFactory.create(conversationId, pageSize)
    val webSocketChatRepository = webSocketChatRepositoryFactory.create(
        conversationId = conversationId,
        senderId = senderId,
        recipientId = recipientId
    )

    return TeaStore(
        initialState = ChatDataState(senderId = senderId),
        reducer = ChatDataReducer(),
        actors = listOf(
            ChatDataSendMessageActor(webSocketChatRepository),
            ChatDataSubscribeMessagesActor(recipientId, messageRoomRepository, webSocketChatRepository),
            ChatDataSubscribeLifecycleActor(recipientId, messageRoomRepository, webSocketChatRepository),
            ChatDataDisconnectSocketActor(recipientId, messageRoomRepository, webSocketChatRepository),
            ChatDataInitMessagesActor(messagesListRepository),
            ChatDataLoadPageActor(messagesListRepository)
        ),
        initialEvents = listOf(ChatDataEvent.Internal.InitApi)
    )
}
