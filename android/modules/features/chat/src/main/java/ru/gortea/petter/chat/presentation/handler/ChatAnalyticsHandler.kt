package ru.gortea.petter.chat.presentation.handler

import ru.gortea.petter.arch.analytics.AnalyticsHandler
import ru.gortea.petter.chat.analytics.ChatAnalyticsController
import ru.gortea.petter.chat.presentation.ChatEvent
import ru.gortea.petter.chat.presentation.ChatState
import ru.gortea.petter.chat.presentation.ChatState.ContentChatState
import ru.gortea.petter.chat.presentation.ChatUiEvent
import ru.gortea.petter.data.paging.model.PagingDataState

internal class ChatAnalyticsHandler(
    private val controller: ChatAnalyticsController
) : AnalyticsHandler<ChatState, ChatEvent> {
    private val sentMessages = ArrayDeque<Long>()

    override fun logEvent(state: ChatState, event: ChatEvent) {
        when (event) {
            is ChatEvent.InitApi -> controller.opened()
            is ChatUiEvent.SendMessage -> sendMessage()
            is ChatEvent.MessagesStatus -> {
                if (state is ContentChatState) messageReceived(event, state.currentUser.id)
            }
            else -> Unit
        }
    }

    private fun sendMessage() {
        sentMessages.addLast(System.currentTimeMillis())
    }

    private fun messageReceived(event: ChatEvent.MessagesStatus, currentUserId: String) {
        val dataState = event.state
        if (dataState is PagingDataState.Paged.Content) {
            when (dataState.content.lastOrNull()?.senderId) {
                null -> Unit
                currentUserId -> {
                    if (sentMessages.isNotEmpty()) {
                        val time = System.currentTimeMillis() - sentMessages.removeFirst()
                        controller.messageSent(time)
                    }
                }
                else -> controller.messageReceived()
            }
        }
    }
}
