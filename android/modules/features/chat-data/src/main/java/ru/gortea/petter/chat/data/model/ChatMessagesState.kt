package ru.gortea.petter.chat.data.model

import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.data.paging.model.PagingDataState

data class ChatMessagesState(
    val messages: PagingDataState<MessageModel>,
    val connectionClosed: Boolean
)
