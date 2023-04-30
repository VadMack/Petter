package ru.gortea.petter.chat.list.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.list.data.ChatsListRepository
import ru.gortea.petter.chat.list.presentation.ChatListCommand
import ru.gortea.petter.chat.list.presentation.ChatListEvent

internal class ChatListInitLoadActor(
    private val repository: ChatsListRepository
) : Actor<ChatListCommand, ChatListEvent> {

    override fun process(commands: Flow<ChatListCommand>): Flow<ChatListEvent> {
        return commands.filterIsInstance<ChatListCommand.InitLoadChatList>()
            .flatMapLatest { repository.get() }
            .mapLatest(ChatListEvent::ChatListLoadingStatus)
    }
}
