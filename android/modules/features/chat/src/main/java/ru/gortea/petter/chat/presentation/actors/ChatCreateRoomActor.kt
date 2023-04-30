package ru.gortea.petter.chat.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.ChatCreateRoomRepository
import ru.gortea.petter.chat.presentation.ChatCommand
import ru.gortea.petter.chat.presentation.ChatEvent
import ru.gortea.petter.profile.data.remote.model.UserIdModel

internal class ChatCreateRoomActor(
    private val repository: ChatCreateRoomRepository
) : Actor<ChatCommand, ChatEvent> {

    override fun process(commands: Flow<ChatCommand>): Flow<ChatEvent> {
        return commands.filterIsInstance<ChatCommand.CreateChatRoomId>()
            .mapLatest { repository.invalidate(UserIdModel(it.userId)) }
            .flatMapMerge { emptyFlow() }
    }
}
