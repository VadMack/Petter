package ru.gortea.petter.chat.list.presentation

import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.list.data.model.ChatCompanionRoom
import ru.gortea.petter.data.model.DataState

internal sealed interface ChatListEvent {
    data class ChatListLoadingStatus(val state: DataState<List<ChatCompanionRoom>>) : ChatListEvent
    class LastMessageUpdate(val lastMassage: MessageModel) : ChatListEvent

    object ObserveLastMessage : ChatListEvent
    object InitApi : ChatListEvent
}

internal sealed interface ChatListUiEvent : ChatListEvent {
    class OpenChat(val userId: String) : ChatListUiEvent

    object LoadChats : ChatListUiEvent
}
