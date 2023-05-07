package ru.gortea.petter.chat.list.di

import com.bumble.appyx.core.modality.BuildContext
import com.bumble.appyx.core.node.Node
import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.list.data.ChatsListRepository
import ru.gortea.petter.chat.list.navigation.ChatListExternalNodesProvider
import ru.gortea.petter.chat.list.stubs.ChatEmptyListApiStub
import ru.gortea.petter.chat.list.stubs.ChatListApiStub
import ru.gortea.petter.chat.list.stubs.MessageRoomRepositoryStub
import ru.gortea.petter.chat.list.stubs.ProfileApiStub
import ru.gortea.petter.chat.list.stubs.UserDaoStub
import ru.gortea.petter.profile.data.local.CurrentUserRepository

internal class ChatListTestComponent(
    private val isEmpty: Boolean = false
) : ChatListComponent {
    override val messageRoomRepository: MessageRoomRepository = MessageRoomRepositoryStub()

    override val chatsListRepository: ChatsListRepository
        get() = ChatsListRepository(
            if (isEmpty) ChatEmptyListApiStub else ChatListApiStub,
            ProfileApiStub,
            CurrentUserRepository(UserDaoStub())
        )
    override val chatListNodesProvider: ChatListExternalNodesProvider
        get() = object : ChatListExternalNodesProvider {

            override fun chat(buildContext: BuildContext, userId: String): Node {
                throw Exception()
            }
        }
}
