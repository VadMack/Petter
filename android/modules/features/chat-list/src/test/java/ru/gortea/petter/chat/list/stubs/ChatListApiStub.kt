package ru.gortea.petter.chat.list.stubs

import ru.gortea.petter.chat.list.data.api.ChatListApi
import ru.gortea.petter.chat.list.data.model.ChatRoom

internal object ChatListApiStub : ChatListApi {

    override suspend fun getChats(userId: String): List<ChatRoom> = listOf(
        ChatRoom(
            id = "id",
            user1 = "user1",
            user2 = "user2",
            lastMessage = null,
            publicKey = ""
        )
    )
}
