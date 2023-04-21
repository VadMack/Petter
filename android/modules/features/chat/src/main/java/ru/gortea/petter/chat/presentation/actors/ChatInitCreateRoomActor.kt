package ru.gortea.petter.chat.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.ChatCreateRoomRepository
import ru.gortea.petter.chat.presentation.ChatCommand
import ru.gortea.petter.chat.presentation.ChatEvent
import ru.gortea.petter.chat.presentation.ChatRoomCreationEvent

internal class ChatInitCreateRoomActor(
    private val repository: ChatCreateRoomRepository
) : Actor<ChatCommand, ChatEvent> {

    override fun process(commands: Flow<ChatCommand>): Flow<ChatEvent> {
        return commands.filterIsInstance<ChatCommand.InitChatRoomCreation>()
            .flatMapLatest { repository.get() }
            .map { ChatRoomCreationEvent.ChatRoomCreationStatus(it) }
    }
}
