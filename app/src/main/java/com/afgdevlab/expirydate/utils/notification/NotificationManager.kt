package com.afgdevlab.expirydate.utils.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.RemoteMessage
import com.google.gson.Gson
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.utils.deeplink.LandingPageActivity
import com.orhanobut.logger.Logger
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class NotificationManager {
    val ARGS_PUSHTYPE = "pushType"
    val EXTRA_NOTIFICATION_ID = "EXTRA_NOTIFICATION_ID"
    val PUSH_TYPE_INTERACTIVE = "interactive"

    fun handleNotification(remoteMessage: RemoteMessage?, context: Context) {
        var title: String? = ""
        var body: String? = ""
        var tyype: String? = ""
        var button1: String? = ""
        var button1Url: String? = ""
        var button2: String? = ""
        var button2Url: String? = ""
        var link: String? = ""
        var bigImageUrl: String? = ""
        if (remoteMessage != null) {
            if (remoteMessage.data["title"] != null) {
                title = remoteMessage.data["title"]
            }
            if (remoteMessage.notification != null || remoteMessage.notification?.title != null) {
                title = remoteMessage.notification?.title!!
            }

            if (remoteMessage.data["message"] != null) {
                body = remoteMessage.data["message"]
            }

            if (remoteMessage.notification != null || remoteMessage.notification?.body != null) {
                body = remoteMessage.notification?.body!!
            }

            if (remoteMessage.data["type"] != null) {
                tyype = remoteMessage.data["type"]
            }
            if (remoteMessage.data["button_1"] != null) {
                button1 = remoteMessage.data["button_1"]
            }
            if (remoteMessage.data["button_1_url"] != null) {
                button1Url = remoteMessage.data["button_1_url"]
            }
            if (remoteMessage.data["button_2"] != null) {
                button2 = remoteMessage.data["button_2"]
            }
            if (remoteMessage.data["button_2_url"] != null) {
                button2Url = remoteMessage.data["button_2_url"]
            }
            if (remoteMessage.data["ins_dl_external"] != null || remoteMessage.data["ins_dl_internal"] != null) {
                link = remoteMessage.data["ins_dl_external"]
            }
            if (remoteMessage.data["deeplink"] != null) {
                link = remoteMessage.data["deeplink"]
            }
            if (remoteMessage.data["image_url"] != null) {
                bigImageUrl = remoteMessage.data["image_url"]
            }
            val lotusNotification = BaseNotification(
                body,
                title,
                tyype,
                button1,
                button1Url,
                button2,
                button2Url,
                link,
                bigImageUrl
            )
            createNotification(context, lotusNotification)
        }
    }

    private fun createNotification(context: Context, lotusNotification: BaseNotification) {
        val r = Random()
        val notificationId = r.nextInt(10000 - 10) + 10
        val channelId = createNotificationChannel(context)
        val mBuilder = NotificationCompat.Builder(context, channelId)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setAutoCancel(false)
            .setWhen(0)
            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)

        if (lotusNotification.title != null) {
            mBuilder.setContentTitle(lotusNotification.title)
        }
        if (lotusNotification.body != null) {
            mBuilder.setContentText(lotusNotification.body)
        }

        if (lotusNotification.type != null && lotusNotification.type == PUSH_TYPE_INTERACTIVE) {
            if (lotusNotification.button1 != null) {
                val intent = Intent(context, LandingPageActivity::class.java)
                if (lotusNotification.button1Url != null) {
                    intent.data = Uri.parse(lotusNotification.button1Url)
                }
                intent.putExtra(ARGS_PUSHTYPE, lotusNotification.type)
                intent.putExtra(EXTRA_NOTIFICATION_ID, notificationId)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                val button1Intent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.addAction(0, lotusNotification.button1, button1Intent)
            }
            if (lotusNotification.button2 != null) {
                val intent = Intent(context, LandingPageActivity::class.java)
                if (lotusNotification.button2Url != null) {
                    intent.data = Uri.parse(lotusNotification.button2Url)
                }
                intent.putExtra(ARGS_PUSHTYPE, lotusNotification.type)
                intent.putExtra(EXTRA_NOTIFICATION_ID, notificationId)
                intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                val button2Intent =
                    PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
                mBuilder.addAction(0, lotusNotification.button2, button2Intent)
            }
        }
        if (lotusNotification.link != null) {
            val intent = Intent(context, LandingPageActivity::class.java)
            intent.putExtra(ARGS_PUSHTYPE, lotusNotification.type)
            intent.putExtra(EXTRA_NOTIFICATION_ID, notificationId)
            intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
            intent.data = Uri.parse(lotusNotification.button2Url)
            val contentIntent =
                PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            mBuilder.setContentIntent(contentIntent)
        }

        val notification = mBuilder.build()
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(notificationId, notification)
        if (lotusNotification.bigImageUrl != null && lotusNotification.bigImageUrl.isNotEmpty()) {
            var title = ""
            var desc = ""
            if (lotusNotification.title != null) {
                title = lotusNotification.title
            }
            if (lotusNotification.body != null) {
                desc = lotusNotification.body
            }
            BigImageLoader(
                mBuilder,
                notificationManager,
                notificationId,
                title,
                desc
            ).execute(lotusNotification.bigImageUrl)
        }
        try {
            Logger.json(Gson().toJson(lotusNotification, BaseNotification::class.java))
        } catch (e: Exception) {
            Logger.e("Broken push object")
        }

    }

    private fun createNotificationChannel(context: Context): String {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "my_service"
            val channelName = "My Notification Service"
            val chan = NotificationChannel(
                channelId,
                channelName,
                android.app.NotificationManager.IMPORTANCE_HIGH
            )
            chan.lightColor = Color.WHITE
            chan.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            val service =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            service.createNotificationChannel(chan)
            return channelId
        }
        return "test"// context.resources.getString(R.string.default_notification_channel_id)
    }

    private class BigImageLoader(
        private val builder: NotificationCompat.Builder?,
        private val notificationManager: NotificationManagerCompat,
        private val notificationId: Int,
        private val title: String,
        private val description: String
    ) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg params: String): Bitmap? {
            val `in`: InputStream
            try {
                val url = URL(params[0])
                val connection = url.openConnection() as HttpURLConnection
                connection.doInput = true
                connection.connect()
                `in` = connection.inputStream
                return BitmapFactory.decodeStream(`in`)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return null
        }

        override fun onPostExecute(bitmap: Bitmap?) {
            super.onPostExecute(bitmap)
            if (bitmap != null && builder != null) {
                builder.setStyle(
                    NotificationCompat.BigPictureStyle()
                        .bigPicture(bitmap).bigLargeIcon(null).setBigContentTitle(title)
                        .setSummaryText(
                            description
                        )
                )
                notificationManager.notify(notificationId, builder.build())
            }
        }
    }
}