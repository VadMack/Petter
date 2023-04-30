package ru.gortea.petter.chat.list.ui.state.model

internal data class RoomUiModel(
    val id: String,
    val companionId: String,
    val companionName: String,
    val avatarPath: String?,
    val message: MessageUiModel
)
