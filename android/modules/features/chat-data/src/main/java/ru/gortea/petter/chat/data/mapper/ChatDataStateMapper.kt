package ru.gortea.petter.chat.data.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.chat.data.model.ChatMessagesState
import ru.gortea.petter.chat.data.model.MessageModel
import ru.gortea.petter.chat.data.model.MessageModelState
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessage
import ru.gortea.petter.chat.data.mvi.ChatDataState
import ru.gortea.petter.data.paging.model.mapContentList

internal class ChatDataStateMapper : UiStateMapper<ChatDataState, ChatMessagesState> {

    override fun map(state: ChatDataState): ChatMessagesState {
        return ChatMessagesState(
            messages = state.storedMessages.mapContentList { serverMsg ->
                val storedMessages = serverMsg.map { it.toMessageModel() }
                val addedMessages = state.sentMessages.map { it.toMessageModel() }
                val received = state.receivedMessages.map { it.toMessageModel() }

                (received + addedMessages).sortedBy { it.time } + storedMessages
            },
            connectionClosed = state.connectionClosed
        )
    }

    private fun ServerMessage.toMessageModel(): MessageModel {
        return MessageModel(
            id = id,
            senderId = senderId,
            recipientId = recipientId,
            content = content,
            time = time,
            state = MessageModelState.SUCCESS
        )
    }

    private fun SentMessageState.toMessageModel(): MessageModel {
        return MessageModel(
            id = "",
            senderId = message.senderId,
            recipientId = message.recipientId,
            content = message.content,
            time = message.time,
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
