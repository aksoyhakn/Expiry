package com.afgdevlab.expirydate.utils.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.extensions.notNull
import com.afgdevlab.expirydate.extensions.resString
import com.afgdevlab.expirydate.utils.Constants

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        intent.getStringExtra(Constants.Notification.NOTIFICATION_CHANNEL_ID).notNull {
            val notificationUtils = NotificationUtils(context,it)
            val notification = notificationUtils.getNotificationBuilder(context.resString(R.string.home_expiry)).build()
            notificationUtils.getManager().notify(150, notification)
        }
    }
}