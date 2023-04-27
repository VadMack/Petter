package ru.gortea.petter.chat.data.mvi.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.mapLatest
import ru.gortea.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.mvi.ChatDataCommand
import ru.gortea.petter.chat.data.mvi.ChatDataEvent
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepository

internal class ChatDataDisconnectSocketActor(
    private val recipientId: String,
    private val messageRoomRepository: MessageRoomRepository,
    private val repository: WebSocketChatRepository
) : Actor<ChatDataCommand, ChatDataEvent> {

    override fun process(commands: Flow<ChatDataCommand>): Flow<ChatDataEvent> {
        return commands.filterIsInstance<ChatDataCommand.CloseConnection>()
            .mapLatest {
                messageRoomRepository.roomWithUserClosed(recipientId)
                repository.close()
            }
            .map { ChatDataEvent.Internal.ConnectionClosed }
    }
}
