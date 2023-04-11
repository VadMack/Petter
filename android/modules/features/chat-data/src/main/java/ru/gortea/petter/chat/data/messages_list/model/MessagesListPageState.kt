package ru.gortea.petter.chat.data.messages_list.model

import ru.gortea.petter.data.paging.model.PageState

internal data class MessagesListPageState(
    val conversationId: String,
    override val page: Int = 0,
    override val pageSize: Int = PAGE_SIZE
) : PageState(page, pageSize) {

    private companion object {
        private const val PAGE_SIZE = 30
    }
}
