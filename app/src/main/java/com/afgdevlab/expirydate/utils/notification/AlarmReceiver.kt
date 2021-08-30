package com.afgdevlab.expirydate.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.afgdevlab.expirydate.utils.Constants

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val notificationUtils = NotificationUtils(
            context,
            intent.getStringExtra(Constants.Notification.NOTIFICATION_CHANNEL_ID) ?: "1"
        )
        val notification = notificationUtils.getNotificationBuilder(
            intent.getStringExtra(Constants.Notification.NOTIFICATION_CHANNEL_NAME) ?: ""
        ).build()

        notificationUtils.getManager().notify(150, notification)
    }
}