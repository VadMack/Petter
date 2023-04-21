package ru.gortea.petter.chat.data.mapper

import ru.gortea.chat.data.messages.model.MessageModel
import ru.gortea.chat.data.messages.model.MessageModelState
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.chat.data.model.ChatMessagesState
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.toMessageModel
import ru.gortea.petter.chat.data.mvi.ChatDataState
import ru.gortea.petter.data.paging.model.mapContentList
import java.util.*

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

    private fun SentMessageState.toMessageModel(): MessageModel {
        return MessageModel(
            id = UUID.randomUUID().toString(),
            senderId = message.senderId,
            recipientId = message.recipientId,
            content = message.content,
            dateTime = message.time,
            state = messageModelState()
        )
    }

    private fun SentMessageState.messageModelState(): MessageModelState {
        return when(this) {
            is SentMessageState.Loading -> MessageModelState.LOADING
            is SentMessageState.Fail -> MessageModelState.FAIL
            is SentMessageState.Success -> MessageModelState.SUCCESS
        }
    }
}
