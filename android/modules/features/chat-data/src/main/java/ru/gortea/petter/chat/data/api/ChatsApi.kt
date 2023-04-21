package ru.gortea.petter.chat.data.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import ru.gortea.petter.chat.data.model.ChatRoomIdModel
import ru.gortea.petter.chat.data.model.InterlocutorsModel
import ru.gortea.petter.chat.data.model.ServerMessage

interface ChatsApi {
    @GET("api/chat-message")
    suspend fun getMessages(
        @Query("chatRoomId") conversationId: String,
        @Query("page") page: Int,
        @Query("size") pageSize: Int,
        @Query("sort") sort: String = "sentTime,DESC"
    ): List<ServerMessage>

    @POST("api/chat-room")
    suspend fun createRoom(@Body interlocutors: InterlocutorsModel): ChatRoomIdModel
}
