package ru.gortea.petter.chat.data.messages_list

import ru.gortea.petter.chat.data.api.ChatsApi
import ru.gortea.petter.chat.data.messages_list.model.MessagesListPageState
import ru.gortea.petter.chat.data.model.ServerMessage
import ru.gortea.petter.data.paging.SourcePagingRepository

internal class MessagesListRepository(
    conversationId: String,
    pageSize: Int,
    private val api: ChatsApi
) : SourcePagingRepository<MessagesListPageState, ServerMessage>(
    initialState = MessagesListPageState(conversationId, pageSize = pageSize),
    invalidatePageMapper = { it.copy(page = 0) },
    nextPageMapper = { it.copy(page = it.page + 1) },
    source = { state ->
        api.getMessages(state.conversationId, state.page, state.pageSize)
    }
)
