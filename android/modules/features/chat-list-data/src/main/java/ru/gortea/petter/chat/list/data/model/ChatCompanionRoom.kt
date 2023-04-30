package ru.gortea.petter.chat.list.data.model

import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.profile.data.remote.model.UserModel

data class ChatCompanionRoom(
    val id: String,
    val companion: UserModel,
    val currentUser: UserModel,
    val lastMessage: MessageModel?,
    val publicKey: String
)
