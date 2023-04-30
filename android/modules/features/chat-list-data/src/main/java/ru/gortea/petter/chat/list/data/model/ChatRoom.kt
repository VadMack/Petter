package ru.gortea.petter.chat.list.data.model

import kotlinx.serialization.Serializable
import ru.gortea.petter.chat.data.messages.model.ServerMessage

@Serializable
data class ChatRoom(
    val id: String,
    val user1: String,
    val user2: String,
    val lastMessage: ServerMessage?,
    val publicKey: String
)
