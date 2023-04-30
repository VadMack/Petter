package ru.gortea.petter.chat.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.gortea.petter.chat.data.messages.model.ServerMessage
import ru.gortea.petter.chat.data.model.ChatRoomIdModel
import ru.gortea.petter.chat.data.model.InterlocutorsModel

interface ChatsApi {
    @GET("api/chat-message")
    suspend fun getMessages(
        @Query("chatRoomId") conversationId: String,
        @Query("skip") offset: Int,
        @Query("limit") pageSize: Int
    ): List<ServerMessage>

    @POST("api/chat-room")
    suspend fun createRoom(@Body interlocutors: InterlocutorsModel): ChatRoomIdModel
}
