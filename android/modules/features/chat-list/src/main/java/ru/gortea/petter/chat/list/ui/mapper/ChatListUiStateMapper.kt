package ru.gortea.petter.chat.list.ui.mapper

import androidx.compose.ui.graphics.Color
import ru.gortea.petter.arch.UiStateMapper
import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.data.messages.model.MessageModelState
import ru.gortea.petter.chat.data.messages.util.format
import ru.gortea.petter.chat.list.R
import ru.gortea.petter.chat.list.data.model.ChatCompanionRoom
import ru.gortea.petter.chat.list.presentation.ChatListState
import ru.gortea.petter.chat.list.ui.state.ChatListUiState
import ru.gortea.petter.chat.list.ui.state.model.MessageUiModel
import ru.gortea.petter.chat.list.ui.state.model.RoomUiModel
import ru.gortea.petter.data.model.mapContentSync
import ru.gortea.petter.theme.Alert600
import ru.gortea.petter.theme.Primary500
import ru.gortea.petter.ui_kit.R as UiKitR

internal class ChatListUiStateMapper : UiStateMapper<ChatListState, ChatListUiState> {

    override fun map(state: ChatListState): ChatListUiState {
        return ChatListUiState(
            chats = state.chats.mapContentSync { list ->
                list.map { it.toRoomUiModel() }
            }
        )
    }

    private fun ChatCompanionRoom.toRoomUiModel(): RoomUiModel {
        return RoomUiModel(
            id = id,
            companionId = companion.id,
            companionName = requireNotNull(companion.displayName),
            avatarPath = companion.avatarPath,
            message = lastMessage?.toMessageUiModel(currentUser.id) ?: MessageUiModel()
        )
    }

    private fun MessageModel.toMessageUiModel(currentUserId: String): MessageUiModel {
        return MessageUiModel(
            id = id,
            content = content,
            dateTime = dateTime.format(),
            authorNameRes = if (senderId == currentUserId) R.string.current_user_message else null,
            iconRes = state.icon(),
            iconTint = state.iconTint()
        )
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
