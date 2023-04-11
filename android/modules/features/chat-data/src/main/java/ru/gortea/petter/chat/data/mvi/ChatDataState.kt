package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessage
import ru.gortea.petter.data.paging.model.PagingDataState

internal data class ChatDataState(
    val receivedMessages: List<ServerMessage> = emptyList(),
    val storedMessages: PagingDataState<ServerMessage> = PagingDataState.Initial.Empty,
    val sentMessages: List<SentMessageState> = emptyList(),
    val connectionClosed: Boolean = false
)
