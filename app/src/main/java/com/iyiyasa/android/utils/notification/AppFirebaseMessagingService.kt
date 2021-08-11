package com.iyiyasa.android.utils.notification

import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.iyiyasa.android.utils.language.BusProvider
import java.io.Serializable


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class AppFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        if (!remoteMessage.data["deeplink"].isNullOrBlank()) {
            deeplinkFromPush = remoteMessage.data["deeplink"]
        }
        NotificationManager().handleNotification(remoteMessage, applicationContext)
        BusProvider.getInstance().post(NotificationEvent())
    }

    companion object {
        var deeplinkFromPush: String? = null
    }
}

class NotificationEvent() : Serializable