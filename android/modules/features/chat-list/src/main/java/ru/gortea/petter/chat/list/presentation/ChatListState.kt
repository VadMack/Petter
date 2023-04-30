package ru.gortea.petter.chat.list.presentation

import ru.gortea.petter.chat.list.data.model.ChatCompanionRoom
import ru.gortea.petter.data.model.DataState

internal data class ChatListState(
    val chats: DataState<List<ChatCompanionRoom>> = DataState.Empty
)
