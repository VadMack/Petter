package ru.gortea.petter.chat.list.presentation.handler

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.chat.list.analytics.ChatListAnalyticsController
import ru.gortea.petter.chat.list.presentation.ChatListEvent
import ru.gortea.petter.chat.list.presentation.ChatListState

internal class ChatListAnalyticsHandler(
    private val controller: ChatListAnalyticsController
) : AnalyticsHandler<ChatListState, ChatListEvent> {

    override fun logEvent(state: ChatListState, event: ChatListEvent) {
        when(event) {
            is ChatListEvent.InitApi -> controller.opened()
            is ChatListEvent.LastMessageUpdate -> controller.messageReceived()
            else -> Unit
        }
    }
}
