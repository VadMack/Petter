package ru.gortea.petter.chat.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.onCompletion
import ru.gortea.chat.data.messages.MessageRoomRepository
import ru.gortea.chat.data.messages.model.MessageModel
import ru.gortea.petter.arch.collection.collect
import ru.gortea.petter.chat.data.mapper.ChatDataStateMapper
import ru.gortea.petter.chat.data.messages_list.MessagesListRepositoryFactory
import ru.gortea.petter.chat.data.model.ChatMessagesState
import ru.gortea.petter.chat.data.mvi.ChatDataEvent
import ru.gortea.petter.chat.data.mvi.ChatDataStore
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepositoryFactory
import ru.gortea.petter.chat.data.reason.ConnectionClosed
import ru.gortea.petter.data.paging.model.PagingDataState

class ChatRepository internal constructor(
    conversationId: String,
    encryptionKey: String,
    senderId: String,
    recipientId: String,
    pageSize: Int,
    messageRoomRepository: MessageRoomRepository,
    messagesListRepositoryFactory: MessagesListRepositoryFactory,
    webSocketChatRepositoryFactory: WebSocketChatRepositoryFactory,
    private val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Default)
) {

    private val store = ChatDataStore(
        conversationId = conversationId,
        encryptionKey = encryptionKey,
        senderId = senderId,
        recipientId = recipientId,
        pageSize = pageSize,
        messageRoomRepository = messageRoomRepository,
        messagesListRepositoryFactory = messagesListRepositoryFactory,
        webSocketChatRepositoryFactory = webSocketChatRepositoryFactory
    )

    private val dataFlow = MutableSharedFlow<PagingDataState<MessageModel>>(
        replay = 1,
        extraBufferCapacity = 3
    )

    init {
        store.attach(coroutineScope)
        store.collect(ChatDataStateMapper(), ::stateRenderer)
    }

    private fun stateRenderer(state: ChatMessagesState) {
        if (state.connectionClosed) {
            dataFlow.tryEmit(PagingDataState.Initial.Fail(ConnectionClosed()))
            coroutineScope.cancel()
        } else {
            dataFlow.tryEmit(state.messages)
        }
    }

    fun get(): Flow<PagingDataState<MessageModel>> = dataFlow.onCompletion {
        store.dispatch(ChatDataEvent.User.CloseConnection)
    }

    fun loadPage() {
        store.dispatch(ChatDataEvent.User.LoadPage)
    }

    fun sendMessage(message: String) {
        store.dispatch(ChatDataEvent.User.SendMessage(message))
    }
}
