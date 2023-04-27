package ru.gortea.petter.chat.presentation.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.ChatRepository
import ru.gortea.petter.chat.presentation.ChatCommand
import ru.gortea.petter.chat.presentation.ChatEvent

internal class ChatLoadPageActor(
    private val chatRepository: ChatRepository
) : Actor<ChatCommand, ChatEvent> {

    override fun process(commands: Flow<ChatCommand>): Flow<ChatEvent> {
        return commands.filterIsInstance<ChatCommand.LoadPage>()
            .mapLatest { chatRepository.loadPage() }
            .flatMapMerge { emptyFlow() }
    }
}
