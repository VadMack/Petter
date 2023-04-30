package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.arch.store.MviStore
import ru.gortea.petter.arch.store.factory.TeaStore
import ru.gortea.petter.chat.data.encryption.MessageEncryptor
import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.mvi.actors.ChatDataDisconnectSocketActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataInitMessagesActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataLoadPageActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSendMessageActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSubscribeLifecycleActor
import ru.gortea.petter.chat.data.mvi.actors.ChatDataSubscribeMessagesActor
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory

internal typealias ChatDataStore = MviStore<ChatDataState, ChatDataEvent>

internal fun ChatDataStore(
    conversationId: String,
    encryptionKey: String,
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
        encryptor = MessageEncryptor(encryptionKey),
        senderId = senderId,
        recipientId = recipientId
    )

    return TeaStore(
        initialState = ChatDataState(senderId = senderId),
        reducer = ChatDataReducer(),
        actors = listOf(
            ChatDataSendMessageActor(messageRoomRepository, webSocketChatRepository),
            ChatDataSubscribeMessagesActor(recipientId, messageRoomRepository, webSocketChatRepository),
            ChatDataSubscribeLifecycleActor(recipientId, messageRoomRepository, webSocketChatRepository),
            ChatDataDisconnectSocketActor(recipientId, messageRoomRepository, webSocketChatRepository),
            ChatDataInitMessagesActor(messagesListRepository),
            ChatDataLoadPageActor(messagesListRepository)
        ),
        initialEvents = listOf(ChatDataEvent.Internal.InitApi)
    )
}
