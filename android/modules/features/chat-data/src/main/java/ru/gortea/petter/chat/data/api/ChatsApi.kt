package ru.gortea.petter.chat.data.api

import retrofit2.http.GET
import retrofit2.http.Path
import ru.gortea.petter.chat.data.model.ServerMessage

interface ChatsApi {
    @GET("topic/chat/{id}")
    fun getMessages(
        @Path("id") conversationId: String,
        page: Int,
        pageSize: Int
    ): List<ServerMessage>
}
