package ru.gortea.petter.chat.data

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
import ru.gortea.petter.chat.data.api.ChatsApi
import ru.gortea.petter.chat.data.model.ChatRoomIdModel
import ru.gortea.petter.chat.data.model.ChatRoomModel
import ru.gortea.petter.chat.data.model.InterlocutorsModel
import ru.gortea.petter.data.SourceRepository
import ru.gortea.petter.profile.data.local.CurrentUserRepository
import ru.gortea.petter.profile.data.remote.api.ProfileApi
import ru.gortea.petter.profile.data.remote.model.GetUserModel
import ru.gortea.petter.profile.data.remote.model.UserModel

class ChatCreateRoomRepository(
    private val api: ChatsApi,
    private val profileApi: ProfileApi,
    private val currentUserRepository: CurrentUserRepository
) : SourceRepository<ChatRoomModel>(
    source = {
        val model = it as GetUserModel
        val currentUser = currentUserRepository.getCurrentUser()
        val argument = InterlocutorsModel(currentUser.id, model.id)

        val createChatRoom: Deferred<ChatRoomIdModel>
        val getProfile: Deferred<UserModel>
        val data = coroutineScope {
            createChatRoom = async { api.createRoom(argument) }
            getProfile = async { profileApi.getUserById(model.id) }

            listOf(createChatRoom, getProfile).awaitAll()
        }

        val user = data.filterIsInstance<UserModel>()[0]
        val chatRoom = data.filterIsInstance<ChatRoomIdModel>()[0]
        ChatRoomModel(id = chatRoom.id, currentUser = currentUser, companion = user)
    }
)
