package ru.gortea.petter.chat.list.data.api

import retrofit2.http.GET
import retrofit2.http.Query
import ru.gortea.petter.chat.list.data.model.ChatRoom

interface ChatListApi {

    @GET("api/chat-room")
    suspend fun getChats(@Query("userId") userId: String): List<ChatRoom>
}
