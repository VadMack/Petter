package ru.gortea.petter.chat.data.mvi.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapMerge
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.chat.data.messages.model.toMessageModel
import ru.gortea.petter.chat.data.model.ServerMessageState.Content
import ru.gortea.petter.chat.data.mvi.ChatDataCommand
import ru.gortea.petter.chat.data.mvi.ChatDataEvent
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepository

internal class ChatDataSubscribeMessagesActor(
    private val recipientId: String,
    private val messageRoomRepository: MessageRoomRepository,
    private val repository: WebSocketChatRepository
) : Actor<ChatDataCommand, ChatDataEvent> {

    override fun process(commands: Flow<ChatDataCommand>): Flow<ChatDataEvent> {
        return commands.filterIsInstance<ChatDataCommand.SubscribeToMessages>()
            .onEach { messageRoomRepository.roomWithUserOpened(recipientId) }
            .flatMapMerge { repository.subscribe() }
            .onEach {
                when (it) {
                    is Content -> messageRoomRepository.messageReceived(it.content.toMessageModel())
                    else -> Unit
                }
            }
            .map { ChatDataEvent.Internal.MessageReceived(it) }
    }
}
