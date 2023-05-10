package ru.gortea.petter.analytics

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.logEvent
import ru.gortea.petter.chat.analytics.ChatAnalyticsController

class ChatAnalyticsControllerImpl(
    private val analytics: FirebaseAnalytics
) : ChatAnalyticsController {

    override fun opened() {
        analytics.logEvent("chat_opened", Bundle.EMPTY)
    }

    override fun messageReceived() {
        analytics.logEvent("chat_message_received", Bundle.EMPTY)
    }

    override fun messageSent(sentTime: Long) {
        analytics.logEvent("chat_message_sent") {
            param("sentTime", sentTime)
        }
    }
}
