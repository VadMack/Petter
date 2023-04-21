package ru.gortea.petter.chat.data.mvi.actors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterIsInstance
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import ru.gortea.chat.data.messages.MessageRoomRepository
import ru.gortea.petter.arch.Actor
import ru.gortea.petter.chat.data.mvi.ChatDataCommand
import ru.gortea.petter.chat.data.mvi.ChatDataEvent
import ru.gortea.petter.chat.data.p2p.WebSocketChatRepository
import ua.naiksoftware.stomp.dto.LifecycleEvent.Type.CLOSED
import ua.naiksoftware.stomp.dto.LifecycleEvent.Type.ERROR
import ua.naiksoftware.stomp.dto.LifecycleEvent.Type.FAILED_SERVER_HEARTBEAT
import ua.naiksoftware.stomp.dto.LifecycleEvent.Type.OPENED

internal class ChatDataSubscribeLifecycleActor(
    private val recipientId: String,
    private val messageRoomRepository: MessageRoomRepository,
    private val repository: WebSocketChatRepository
) : Actor<ChatDataCommand, ChatDataEvent> {
    override fun process(commands: Flow<ChatDataCommand>): Flow<ChatDataEvent> {
        return commands.filterIsInstance<ChatDataCommand.SubscribeToLifecycle>()
            .flatMapLatest { repository.subscribeLifecycle() }
            .onEach { event ->
                when (event.type) {
                    OPENED, null -> Unit
                    CLOSED,
                    FAILED_SERVER_HEARTBEAT -> messageRoomRepository.roomWithUserClosed(recipientId)
                    ERROR -> Unit
                }
            }
            .map { ChatDataEvent.Internal.ChatLifecycle(it) }
    }
}
