package ru.gortea.petter.chat.data.model

import ru.gortea.petter.profile.data.remote.model.UserModel

data class ChatRoomModel(
    val id: String,
    val currentUser: UserModel,
    val companion: UserModel
)
