package ru.gortea.petter.chat.ui.model

import android.net.Uri
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.ui_kit.text_field.TextFieldState

internal sealed interface ChatUiState {
    class NoRoomUiState(val room: DataState<ChatRoomModel>) : ChatUiState

    class ContentUiState(
        val companionName: String,
        val companionAvatar: Uri?,
        val offset: Int,
        val messages: PagingDataState<MessageUiModel>,
        val messageField: TextFieldState
    ) : ChatUiState
}
