package ru.gortea.petter.chat.data.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.chat.data.messages.model.toMessageModel
import ru.gortea.petter.chat.data.model.ChatMessagesState
import ru.gortea.petter.chat.data.model.toMessageModel
import ru.gortea.petter.chat.data.mvi.ChatDataState
import ru.gortea.petter.data.paging.model.mapContentList

internal class ChatDataStateMapper : UiStateMapper<ChatDataState, ChatMessagesState> {

    override fun map(state: ChatDataState): ChatMessagesState {
        return ChatMessagesState(
            messages = state.storedMessages.mapContentList { serverMsg ->
                val storedMessages = serverMsg.map { it.toMessageModel() }
                val addedMessages = state.sentMessages.map { it.toMessageModel() }
                val received = state.receivedMessages.map { it.toMessageModel() }

                addedMessages + received + storedMessages
            },
            connectionClosed = state.connectionClosed
        )
    }
}
