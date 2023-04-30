package ru.gortea.petter.chat.list.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.list.presentation.ChatListCommand
import ru.gortea.petter.chat.list.presentation.ChatListEvent

internal class ChatListLastMessageActor(
    private val repository: MessageRoomRepository
) : Actor<ChatListCommand, ChatListEvent> {

    override fun process(commands: Flow<ChatListCommand>): Flow<ChatListEvent> {
        return commands.filterIsInstance<ChatListCommand.ObserveLastMessage>()
            .flatMapLatest { repository.receivedMessage() }
            .map { ChatListEvent.LastMessageUpdate(it) }
    }
}
