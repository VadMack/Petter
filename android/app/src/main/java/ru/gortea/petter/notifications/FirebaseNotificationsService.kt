package ru.gortea.petter.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import ru.gortea.petter.R
import ru.gortea.petter.main.MainActivity
import ru.gortea.petter.ui_kit.R as UiKitR


class FirebaseNotificationsService : FirebaseMessagingService() {

    private val manager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    private val notifications = FirebaseNotifications()

    override fun onMessageReceived(message: RemoteMessage) {
        sendNotification(message)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification ?: return
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_ONE_SHOT
        )

        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, getString(R.string.notification_channel_id))
            .setLargeIcon(BitmapFactory.decodeResource(resources, UiKitR.drawable.ic_paw))
            .setSmallIcon(UiKitR.drawable.ic_paw)
            .setContentTitle(notification.title)
            .setContentText(notification.body)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        notifications.createChannel(this, manager)

        manager.notify(notification.title.hashCode(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        notifications.onTokenUpdated(token)
    }
}
