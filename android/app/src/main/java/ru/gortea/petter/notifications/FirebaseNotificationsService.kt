package ru.gortea.petter.notifications

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.serialization.decodeFromString
import ru.gortea.petter.R
import ru.gortea.petter.arch.android.activity.getComponent
import ru.gortea.petter.chat.data.messages.model.ServerMessage
import ru.gortea.petter.chat.data.messages.model.toMessageModel
import ru.gortea.petter.main.MainActivity
import ru.gortea.petter.network.PetterNetwork.json
import ru.gortea.petter.notifications.di.NotificationsServiceComponent
import ru.gortea.petter.ui_kit.R as UiKitR


class FirebaseNotificationsService : FirebaseMessagingService() {

    private val manager by lazy { getSystemService(NOTIFICATION_SERVICE) as NotificationManager }

    private val component by lazy { getComponent<NotificationsServiceComponent>() }
    private val notifications by lazy { FirebaseNotifications(component.tokenRepository) }
    private val lastMessageRepository by lazy { component.messageRoomRepository }

    override fun onMessageReceived(message: RemoteMessage) {
        val modelJson = message.data["model"] ?: return
        val model = json.decodeFromString<ServerMessage>(modelJson)
        lastMessageRepository.messageReceived(model.toMessageModel())
        if (lastMessageRepository.roomCompanionId() != model.senderId) {
            sendNotification(message)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val notification = remoteMessage.notification ?: return
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtras(remoteMessage.toIntent())
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
        val userId = remoteMessage.data["userId"]
        manager.notify(userId.hashCode(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        notifications.onTokenUpdated(token)
    }
}
