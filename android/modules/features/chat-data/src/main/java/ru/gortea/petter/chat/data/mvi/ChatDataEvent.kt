package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessage
import ru.gortea.petter.chat.data.model.ServerMessageState
import ru.gortea.petter.data.paging.model.PagingDataState

internal sealed interface ChatDataEvent {
    sealed interface Internal : ChatDataEvent {
        class MessageReceived(val state: ServerMessageState) : Internal
        class MessageSent(val state: SentMessageState) : Internal

        class MessagesLoadStatus(val state: PagingDataState<ServerMessage>) : Internal

        object ConnectionClosed : Internal
        object InitApi : Internal
    }

    sealed interface User : ChatDataEvent {
        class SendMessage(val message: String) : User

        object LoadPage : User
        object CloseConnection : User
    }
}
