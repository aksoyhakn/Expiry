package com.afgdevlab.expirydate.utils.notification


import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.graphics.Color
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.ui.home.HomeActivity

class NotificationUtils(base: Context, naming: String) : ContextWrapper(base) {

    var MYCHANNEL_ID = ""

    private var manager: NotificationManager? = null

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            MYCHANNEL_ID = naming
            createChannels(naming)
        }
    }

    private fun createChannels(channel: String) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                "${channel}ID",
                "${channel}NAME",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel.enableVibration(true)

            getManager().createNotificationChannel(channel)
        }
    }

    // Get Manager
    fun getManager(): NotificationManager {
        if (manager == null) manager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        return manager as NotificationManager
    }

    fun getNotificationBuilder(mesaj: String): NotificationCompat.Builder {
        val intent = Intent(this, HomeActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)
        return NotificationCompat.Builder(applicationContext, MYCHANNEL_ID)
            .setContentTitle("Alarm!")
            .setContentText(mesaj)
            .setSmallIcon(R.drawable.ic_search)
            .setColor(Color.YELLOW)
            .setContentIntent(pendingIntent)
            .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
            .setAutoCancel(true)
    }
}