package ru.gortea.petter.chat.data.messages_list.model

import ru.gortea.petter.data.paging.model.OffsetState

internal data class MessagesListPageState(
    val conversationId: String,
    override val offset: Int = 0,
    override val pageSize: Int = PAGE_SIZE
) : OffsetState(offset, pageSize) {

    private companion object {
        private const val PAGE_SIZE = 30
    }
}
