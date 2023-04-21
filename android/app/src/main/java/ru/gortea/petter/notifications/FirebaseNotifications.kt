package ru.gortea.petter.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.gortea.petter.R
import ru.gortea.petter.token.storage.TokenRepository

class FirebaseNotifications(
    private val deviceTokenRepository: TokenRepository
) {
    fun initialToken(tokenUpdated: () -> Unit = {}) {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) return@OnCompleteListener

                onTokenUpdated(task.result)
                tokenUpdated()
            }
        )
    }

    fun onTokenUpdated(token: String) {
        deviceTokenRepository.updateToken(token)
    }

    fun createChannel(context: Context, manager: NotificationManager) {
        val channelId = context.getString(R.string.notification_channel_id)
        if (manager.getNotificationChannel(channelId) != null) return

        val name = context.getString(R.string.notification_channel_title)
        val descriptionText = context.getString(R.string.notification_channel_description)

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        manager.createNotificationChannel(channel)
    }
}
