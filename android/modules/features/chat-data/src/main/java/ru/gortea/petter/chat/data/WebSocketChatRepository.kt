package ru.gortea.petter.chat.data

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.asFlow
import kotlinx.coroutines.rx2.asFlow
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import okhttp3.OkHttpClient
import ru.gortea.petter.chat.data.model.MessageState
import ru.gortea.petter.chat.data.model.ReceivedMessage
import ru.gortea.petter.chat.data.model.SentMessage
import ru.gortea.petter.chat.data.model.SentMessageState
import ru.gortea.petter.network.PetterNetwork.BASE_URL
import ru.gortea.petter.network.PetterNetwork.json
import ru.gortea.petter.profile.data.local.UserLocalRepository
import ru.gortea.petter.token.storage.TokenRepository
import ua.naiksoftware.stomp.Stomp
import ua.naiksoftware.stomp.StompClient
import ua.naiksoftware.stomp.dto.LifecycleEvent

class WebSocketChatRepository internal constructor(
    private val userId: String,
    private val okHttpClient: OkHttpClient,
    private val jwtRepository: TokenRepository,
    private val userRepository: UserLocalRepository
) {
    private val topicUrl = "/$userId/queue/messages"
    private val sendDestination = "/app/chat/$userId"

    private val socketUrl = "ws://$BASE_URL/ws"
    private val coroutineScope = CoroutineScope(Dispatchers.IO)

    private val headers: Map<String, String> by lazy {
        mapOf("Authorization" to "Bearer ${jwtRepository.getToken()}")
    }

    private val stompClient: StompClient by lazy {
        Stomp.over(Stomp.ConnectionProvider.OKHTTP, socketUrl, headers, okHttpClient)
            .withClientHeartbeat(30000)
    }

    private var senderId: String = ""

    init {
        collectChatLifecycle()
        coroutineScope.launch {
            senderId = userRepository.getCurrentUser().id
        }
    }

    private fun collectChatLifecycle() {
        coroutineScope.launch {
            stompClient.lifecycle()
                .asFlow()
                .flowOn(Dispatchers.IO)
                .onEach { lifecycleEvent: LifecycleEvent ->
                    println("xxx: $lifecycleEvent")
//                when (lifecycleEvent.type!!) {
//                    OPENED -> Log.d(TAG, "Stomp connection opened")
//                    ERROR -> Log.e(TAG, "Error", lifecycleEvent.exception)
//                    FAILED_SERVER_HEARTBEAT,
//                    CLOSED -> {
//                        Log.d(TAG, "Stomp connection closed")
//                    }
//                }
                }
                .collect()
        }
    }

    fun subscribe(): Flow<MessageState> {
        return stompClient.topic(topicUrl)
            .map { json.decodeFromString<ReceivedMessage>(it.payload) }
            .map<MessageState> { MessageState.Content(it) }
            .onErrorReturn { MessageState.Fail(it) }
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    fun send(message: String): Flow<SentMessageState> {
        val messageModel = SentMessage(message, senderId, userId)
        return stompClient.send(sendDestination, json.encodeToString(messageModel))
            .toObservable<Unit>()
            .map<SentMessageState> { SentMessageState.Success(message) }
            .onErrorReturn { SentMessageState.Fail(message, it) }
            .asFlow()
            .flowOn(Dispatchers.IO)
    }

    fun close() {
        stompClient.disconnect()
        coroutineScope.cancel()
    }
}
