package ru.gortea.petter.chat.presentation

import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.paging.model.PagingDataState

internal sealed interface ChatEvent {
    class MessagesStatus(val state: PagingDataState<MessageModel>) : ChatEvent

    object InitApi : ChatEvent
}

internal sealed interface ChatRoomCreationEvent : ChatEvent {
    class ChatRoomCreationStatus(val state: DataState<ChatRoomModel>) : ChatRoomCreationEvent

    class CreateChatRoom(val userId: String) : ChatRoomCreationEvent
}

internal sealed interface ChatUiEvent : ChatEvent {
    class MessageFieldChanged(val text: String) : ChatUiEvent

    object SendMessage : ChatUiEvent
    object LoadPage : ChatUiEvent
    object Back : ChatUiEvent
    object OpenCompanionProfile : ChatUiEvent
}
