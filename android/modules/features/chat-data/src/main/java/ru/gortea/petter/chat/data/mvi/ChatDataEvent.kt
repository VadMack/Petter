package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessage
import ru.gortea.petter.chat.data.model.ServerMessageState
import ru.gortea.petter.data.paging.model.PagingDataState
import ua.naiksoftware.stomp.dto.LifecycleEvent

internal sealed interface ChatDataEvent {
    sealed interface Internal : ChatDataEvent {
        data class ChatLifecycle(val event: LifecycleEvent) : Internal
        data class MessageReceived(val state: ServerMessageState) : Internal
        class MessageSent(val state: SentMessageState) : Internal

        data class MessagesLoadStatus(val state: PagingDataState<ServerMessage>) : Internal

        object ConnectionClosed : Internal
        object InitApi : Internal
    }

    sealed interface User : ChatDataEvent {
        data class SendMessage(val message: String) : User

        object LoadPage : User
        object CloseConnection : User
    }
}
