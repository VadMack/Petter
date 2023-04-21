package ru.gortea.petter.chat.presentation

import ru.gortea.chat.data.messages.model.MessageModel
import ru.gortea.petter.arch.android.util.FieldState
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.paging.model.PagingDataState
import ru.gortea.petter.profile.data.remote.model.UserModel

internal sealed interface ChatState {

    data class ContentChatState(
        val roomId: String,
        val currentUser: UserModel,
        val companion: UserModel,
        val pageSize: Int,
        val messages: PagingDataState<MessageModel> = PagingDataState.Initial.Empty,
        val messageField: FieldState = FieldState()
    ) : ChatState

    data class RoomCreationChatState(
        val room: DataState<ChatRoomModel> = DataState.Empty
    ) : ChatState
}
