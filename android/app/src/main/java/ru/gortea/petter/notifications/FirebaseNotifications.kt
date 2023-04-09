package ru.gortea.petter.notifications

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import ru.gortea.petter.R

class FirebaseNotifications {
    fun initialToken() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(
            OnCompleteListener { task ->
                if (!task.isSuccessful) return@OnCompleteListener

                onTokenUpdated(task.result)
            }
        )
    }

    fun onTokenUpdated(token: String) {
        // todo send token to server
        println("xxx: token $token")
    }

    fun createChannel(context: Context, manager: NotificationManager) {
        val channelId = context.getString(R.string.notification_channel_id)
        if(manager.getNotificationChannel(channelId) != null) return

        val name = context.getString(R.string.notification_channel_title)
        val descriptionText = context.getString(R.string.notification_channel_description)

        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(channelId, name, importance).apply {
            description = descriptionText
        }

        manager.createNotificationChannel(channel)
    }
}
