package ru.gortea.petter.chat.data.mvi.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.messages_list.MessagesListRepository
import ru.gortea.petter.chat.data.mvi.ChatDataCommand
import ru.gortea.petter.chat.data.mvi.ChatDataEvent

internal class ChatDataInitMessagesActor(
    private val repository: MessagesListRepository
) : Actor<ChatDataCommand, ChatDataEvent> {

    override fun process(commands: Flow<ChatDataCommand>): Flow<ChatDataEvent> {
        return commands.filterIsInstance<ChatDataCommand.InitMessagesLoad>()
            .flatMapLatest { repository.get() }
            .map { ChatDataEvent.Internal.MessagesLoadStatus(it) }
    }
}
