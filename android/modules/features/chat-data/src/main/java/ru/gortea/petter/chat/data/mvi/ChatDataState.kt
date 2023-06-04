package ru.gortea.petter.chat.data.mvi

import ru.gortea.petter.chat.data.messages.model.ServerMessage
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.data.paging.model.PagingDataState
import ua.naiksoftware.stomp.dto.LifecycleEvent

internal data class ChatDataState(
    val senderId: String,
    val receivedMessages: List<ServerMessage> = emptyList(),
    val storedMessages: PagingDataState<ServerMessage> = PagingDataState.Initial.Empty,
    val sentMessages: List<SentMessageState> = emptyList(),
    val lifecycleEvent: LifecycleEvent = LifecycleEvent(LifecycleEvent.Type.OPENED),
    val connectionClosed: Boolean = false
) {
    val messagesCount: Int
        get() = receivedMessages.size + storedMessages.content.size
}
