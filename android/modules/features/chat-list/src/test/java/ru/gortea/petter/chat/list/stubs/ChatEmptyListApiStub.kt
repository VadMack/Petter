package ru.gortea.petter.chat.list.stubs

import ru.gortea.petter.chat.data.messages.model.ServerMessage
import ru.gortea.petter.chat.list.data.api.ChatListApi
import ru.gortea.petter.chat.list.data.model.ChatRoom
import java.time.LocalDateTime

internal object ChatEmptyListApiStub : ChatListApi {

    private var isFirstCall = true

    override suspend fun getChats(userId: String): List<ChatRoom> {
        return if (isFirstCall) {
            isFirstCall = false
            emptyList()
        } else {
            listOf(
                ChatRoom(
                    id = "id",
                    user1 = "user1",
                    user2 = "user2",
                    lastMessage = ServerMessage(
                        id = "id",
                        chatRoomId = "id",
                        senderId = "user1",
                        recipientId = "user2",
                        content = "Content",
                        sentTime = LocalDateTime.of(2023, 1, 1, 0, 0)
                    ),
                    publicKey = ""
                )
            )
        }
    }
}
