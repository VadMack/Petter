package ru.gortea.petter.chat.data.messages_list

import ru.gortea.petter.chat.data.api.ChatsApi
import ru.gortea.petter.chat.data.messages.model.ServerMessage
import ru.gortea.petter.chat.data.messages_list.model.MessagesListPageState
import ru.gortea.petter.data.paging.SourceOffsetPagingRepository

internal class MessagesListRepository(
    conversationId: String,
    pageSize: Int,
    private val api: ChatsApi
) : SourceOffsetPagingRepository<MessagesListPageState, ServerMessage>(
    initialState = MessagesListPageState(conversationId, pageSize = pageSize),
    invalidatePageMapper = { it.copy(offset = 0) },
    offsetUpdater = { offset, state -> state.copy(offset = offset) },
    source = { state ->
        api.getMessages(state.conversationId, state.offset, state.pageSize)
    }
)
