package ru.gortea.petter.chat.list.ui.state

import ru.gortea.petter.chat.list.ui.state.model.RoomUiModel
import ru.gortea.petter.data.model.DataState

internal data class ChatListUiState(
    val chats: DataState<List<RoomUiModel>>
)
