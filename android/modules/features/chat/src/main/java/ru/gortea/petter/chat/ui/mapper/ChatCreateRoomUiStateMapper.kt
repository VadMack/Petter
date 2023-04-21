package ru.gortea.petter.chat.ui.mapper

import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.chat.presentation.ChatState
import ru.gortea.petter.chat.ui.model.ChatUiState

internal class ChatCreateRoomUiStateMapper : UiStateMapper<ChatState, ChatUiState.NoRoomUiState> {

    override fun map(state: ChatState): ChatUiState.NoRoomUiState {
        return when (state) {
            is ChatState.RoomCreationChatState -> roomCreationChatState(state)
            is ChatState.ContentChatState -> error("Illegal state $state")
        }
    }

    private fun roomCreationChatState(state: ChatState.RoomCreationChatState): ChatUiState.NoRoomUiState {
        return ChatUiState.NoRoomUiState(state.room)
    }
}
