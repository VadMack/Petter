package ru.gortea.petter.chat.list

import app.cash.turbine.test
import io.kotest.core.coroutines.backgroundScope
import io.kotest.core.spec.style.FunSpec
import org.junit.jupiter.api.Assertions.assertEquals
import ru.gortea.petter.chat.data.messages.model.MessageModel
import ru.gortea.petter.chat.data.messages.model.MessageModelState
import ru.gortea.petter.chat.list.di.ChatListTestComponent
import ru.gortea.petter.chat.list.presentation.createChatListStore
import ru.gortea.petter.data.model.DataState
import ru.gortea.petter.data.model.isContent
import ru.gortea.petter.navigation.Router
import java.time.LocalDateTime

internal class ChatListStoreTest : FunSpec({
    coroutineTestScope = true

    test("Update last message of existed chat") {
        val component = ChatListTestComponent()
        val msgRepo = component.messageRoomRepository
        val message = MessageModel(
            id = "id",
            roomId = "id",
            senderId = "user1",
            recipientId = "user2",
            content = "Content",
            dateTime = LocalDateTime.of(2023, 1, 1, 0, 0),
            state = MessageModelState.SUCCESS
        )

        val store = createChatListStore(Router.stub(), component)
        store.attach(backgroundScope)

        store.stateFlow.test {
            var item = awaitItem()
            while (!item.chats.isContent) {
                item = awaitItem()
            }

            var messages = (item.chats as DataState.Content).content
            assertEquals(null, messages[0].lastMessage)

            msgRepo.messageReceived(message)

            item = awaitItem()
            messages = (item.chats as DataState.Content).content
            assertEquals(message, messages[0].lastMessage)
        }
    }

    test("Update last message of new chat") {
        val component = ChatListTestComponent(isEmpty = true)
        val msgRepo = component.messageRoomRepository
        val message = MessageModel(
            id = "id",
            roomId = "id",
            senderId = "user1",
            recipientId = "user2",
            content = "Content",
            dateTime = LocalDateTime.of(2023, 1, 1, 0, 0),
            state = MessageModelState.SUCCESS
        )

        val store = createChatListStore(Router.stub(), component)
        store.attach(backgroundScope)

        store.stateFlow.test {
            var item = awaitItem()
            while (!item.chats.isContent) {
                item = awaitItem()
            }

            var messages = (item.chats as DataState.Content).content
            assertEquals(emptyList<MessageModel>(), messages)

            msgRepo.messageReceived(message)

            item = awaitItem()
            messages = (item.chats as DataState.Content).content
            assertEquals(message, messages[0].lastMessage)
        }
    }
})
