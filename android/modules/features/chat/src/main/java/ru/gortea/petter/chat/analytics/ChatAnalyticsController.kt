package ru.gortea.petter.chat.analytics

interface ChatAnalyticsController {
    fun opened()
    fun messageReceived()
    fun messageSent(sentTime: Long)
}
