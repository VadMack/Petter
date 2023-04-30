package ru.gortea.petter.chat.list.data

import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.gortea.petter.chat.data.messages.model.toMessageModel
import ru.gortea.petter.chat.list.data.api.ChatListApi
import ru.gortea.petter.chat.list.data.model.ChatCompanionRoom
import ru.gortea.petter.chat.list.data.model.ChatRoom
import ru.gortea.petter.data.MapSourceRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi

class ChatsListRepository(
    private val api: ChatListApi,
    private val userApi: ProfileApi,
    private val currentUserRepository: CurrentUserRepository
) : MapSourceRepository<List<ChatRoom>, List<ChatCompanionRoom>>(
    source = {
        api.getChats(currentUserRepository.getCurrentUser().id)
    },
    mapper = { list ->
        val currentUser = currentUserRepository.getCurrentUser()
        val users = coroutineScope {
            list.map { if (it.user1 == currentUser.id) it.user2 else it.user1 }
                .distinct()
                .map { userId -> async { userApi.getUserById(userId) } }
                .awaitAll()
        }

        list.map { room ->
            ChatCompanionRoom(
                id = room.id,
                companion = users.first { it.id == room.user1 || it.id == room.user2 },
                currentUser = currentUser,
                lastMessage = room.lastMessage?.toMessageModel(),
                publicKey = room.publicKey
            )
        }.sortedByDescending { it.lastMessage?.dateTime }
    }
)
