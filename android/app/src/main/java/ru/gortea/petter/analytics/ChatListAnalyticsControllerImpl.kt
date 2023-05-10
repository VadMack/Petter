package ru.gortea.petter.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import ru.gortea.petter.chat.list.analytics.ChatListAnalyticsController

class ChatListAnalyticsControllerImpl(
    private val analytics: FirebaseAnalytics
) : ChatListAnalyticsController {

    override fun opened() {
        analytics.logEvent("chat_list_opened", Bundle.EMPTY)
    }

    override fun messageReceived() {
        analytics.logEvent("chat_list_message_received", Bundle.EMPTY)
    }
}
