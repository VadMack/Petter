package ru.gortea.petter.chat.data.mvi.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.mvi.ChatDataCommand
import ru.gortea.petter.chat.data.mvi.ChatDataEvent
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepository

internal class ChatDataSubscribeMessagesActor(
    private val repository: WebSocketChatRepository
) : Actor<ChatDataCommand, ChatDataEvent> {
    override fun process(commands: Flow<ChatDataCommand>): Flow<ChatDataEvent> {
        return commands.filterIsInstance<ChatDataCommand.SubscribeToMessages>()
            .flatMapMerge { repository.subscribe() }
            .map { ChatDataEvent.Internal.MessageReceived(it) }
    }
}
