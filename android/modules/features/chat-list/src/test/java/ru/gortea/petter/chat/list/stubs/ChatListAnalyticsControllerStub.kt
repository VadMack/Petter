package ru.gortea.petter.chat.list.stubs

import ru.gortea.petter.chat.list.analytics.ChatListAnalyticsController

internal class ChatListAnalyticsControllerStub : ChatListAnalyticsController {
    override fun opened() = Unit

    override fun messageReceived() = Unit
}
