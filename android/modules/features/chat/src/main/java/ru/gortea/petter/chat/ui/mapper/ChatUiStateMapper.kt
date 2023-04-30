package ru.gortea.petter.chat.ui.mapper

import android.net.Uri
import androidx.compose.ui.graphics.Color
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.arch.android.util.toTextFieldState
import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.data.messages.model.MessageModelState
import ru.gortea.petter.chat.data.messages.util.format
import ru.gortea.petter.chat.presentation.ChatState
import ru.gortea.petter.chat.ui.model.ChatUiState
import ru.gortea.petter.chat.ui.model.MessageGravity
import ru.gortea.petter.chat.ui.model.MessageUiModel
import ru.gortea.petter.data.paging.model.mapContent
import ru.gortea.petter.theme.Alert600
import ru.gortea.petter.theme.Primary500
import ru.gortea.petter.ui_kit.R as UiKitR

internal class ChatUiStateMapper : UiStateMapper<ChatState, ChatUiState.ContentUiState> {

    override fun map(state: ChatState): ChatUiState.ContentUiState {
        return when (state) {
            is ChatState.RoomCreationChatState -> error("Illegal state $state")
            is ChatState.ContentChatState -> contentChatState(state)
        }
    }

    private fun contentChatState(state: ChatState.ContentChatState): ChatUiState.ContentUiState {
        return ChatUiState.ContentUiState(
            companionAvatar = state.companion.avatarPath?.let(Uri::parse),
            companionName = state.companion.displayName ?: "",
            offset = state.pageSize * 2,
            messages = state.messages.mapContent {
                MessageUiModel(
                    id = it.id,
                    gravity = it.gravity(state.currentUser.id),
                    content = it.content,
                    dateTime = it.dateTime.format(),
                    iconRes = it.state.icon(),
                    iconTint = it.state.iconTint()
                )
            },
            messageField = state.messageField.toTextFieldState()
        )
    }

    private fun MessageModel.gravity(currentUserId: String): MessageGravity {
        return if (senderId == currentUserId) MessageGravity.END else MessageGravity.START
    }

    private fun MessageModelState.icon(): Int? {
        return when(this) {
            MessageModelState.LOADING -> UiKitR.drawable.ic_clock
            MessageModelState.FAIL -> UiKitR.drawable.ic_alert
            MessageModelState.SUCCESS -> null
        }
    }

    private fun MessageModelState.iconTint(): Color? {
        return when(this) {
            MessageModelState.LOADING -> Primary500
            MessageModelState.FAIL -> Alert600
            MessageModelState.SUCCESS -> null
        }
    }
}
