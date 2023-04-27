package ru.gortea.petter.chat.data.p2p

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.await
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import okhttp3.OkHttpClient
import ru.gortea.petter.chat.data.encryption.MessageEncryptor
import ru.gortea.petter.chat.data.model.SentMessage
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.chat.data.model.ServerMessage
import ru.gortea.petter.chat.data.model.ServerMessageState
import ru.gortea.petter.network.PetterNetwork.BASE_URL
import ru.gortea.petter.network.PetterNetwork.json
import ru.gortea.petter.token.storage.TokenRepository
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

internal class WebSocketChatRepository(
    private val conversationId: String,
    private val encryptor: MessageEncryptor,
    private val recipientId: String,
    private val senderId: String,
    private val okHttpClient: OkHttpClient,
    private val jwtRepository: TokenRepository
) {

    private val topicUrl = "/topic/chat/$conversationId"
    private val sendDestination get() = "/app/chat/$conversationId"

    private val socketUrl = "ws://$BASE_URL/ws"
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val headers: Map<String, String> by lazy {
        mapOf("Authorization" to "Bearer ${jwtRepository.getToken()}")
    }

    private val stompClient: StompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, socketUrl, headers, okHttpClient)
            .withClientHeartbeat(30000)
    }

    init {
        if (!stompClient.isConnected) {
            stompClient.connect()
        }
    }

    fun subscribeLifecycle(): Flow<LifecycleEvent> {
        return stompClient.lifecycle()
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    fun subscribe(): Flow<ServerMessageState> {
        return stompClient.topic(topicUrl)
            .map { json.decodeFromString<ServerMessage>(it.payload) }
            .map<ServerMessageState> { ServerMessageState.Content(it) }
            .onErrorReturn { ServerMessageState.Fail(it) }
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    fun send(message: String): Flow<SentMessageState> = flow {
        val messageModel = SentMessage(
            chatRoomId = conversationId,
            content = encryptor.encrypt(message),
            decodedContent = message,
            senderId = senderId,
            recipientId = recipientId
        )
        emit(SentMessageState.Loading(messageModel))

        try {
            stompClient
                .send(sendDestination, json.encodeToString(messageModel))
                .await()
            emit(SentMessageState.Success(messageModel))
        } catch (error: Throwable) {
            emit(SentMessageState.Fail(messageModel, error))
        }
    }

    fun close() {
        stompClient.disconnect()
        coroutineScope.cancel()
    }
}
