package ru.gortea.petter.chat.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.ChatRepository
import ru.gortea.petter.chat.presentation.ChatCommand
import ru.gortea.petter.chat.presentation.ChatEvent

internal class ChatInitMessagesActor(
    private val chatRepository: ChatRepository
) : Actor<ChatCommand, ChatEvent> {

    override fun process(commands: Flow<ChatCommand>): Flow<ChatEvent> {
        return commands.filterIsInstance<ChatCommand.InitMessages>()
            .flatMapLatest { chatRepository.get() }
            .map { ChatEvent.MessagesStatus(it) }
    }
}
