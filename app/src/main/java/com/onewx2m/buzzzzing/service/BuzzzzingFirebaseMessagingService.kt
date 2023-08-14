package com.onewx2m.buzzzzing.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.onewx2m.buzzzzing.R
import com.onewx2m.buzzzzing.ui.MainActivity
import com.onewx2m.core_ui.util.Constants.NAVIGATION_PARCEL

class BuzzzzingFirebaseMessagingService : FirebaseMessagingService() {

    companion object {

        const val CHANNEL_ID = "notification_remote_channel"
        private const val TITLE = "title"
        private const val BODY = "body"
        private const val TYPE = "type"
        private const val REDIRECT_TARGET_ID = "redirectTargetId"
    }

    private lateinit var notificationManager: NotificationManager

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)

        createNotificationChannel()
        sendNotification(message.data)
    }

    private fun createNotificationChannel() {
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                this.getString(R.string.notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH,
            )

        notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    private fun sendNotification(data: Map<String, String>) {
        val title = data[TITLE] ?: ""
        val body = data[BODY] ?: ""
        val type = data[TYPE] ?: ""
        val redirectTargetId = data[REDIRECT_TARGET_ID]?.toIntOrNull() ?: 0

        val pendingIntent = getPendingIntent(type, redirectTargetId)

        val builder =
            NotificationCompat.Builder(this, CHANNEL_ID)

        builder
            .setSmallIcon(com.onewx2m.design_system.R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setFullScreenIntent(pendingIntent, true)
            .setAutoCancel(true)

        notificationManager.notify(System.currentTimeMillis().toInt(), builder.build())
    }

    private fun getPendingIntent(
        type: String,
        redirectTargetId: Int,
    ): PendingIntent? {
        val intent = Intent(this, MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        intent.putExtra(
            NAVIGATION_PARCEL,
            NotificationUtil.getNavigationParcel(type, redirectTargetId),
        )
        return PendingIntent.getActivity(
            this,
            System.currentTimeMillis().toInt(),
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT,
        )
    }
}
