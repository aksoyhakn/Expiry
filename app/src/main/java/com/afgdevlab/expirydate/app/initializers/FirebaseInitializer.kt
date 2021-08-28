package com.afgdevlab.expirydate.app.initializers

import android.app.Application
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import javax.inject.Inject

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class FirebaseInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        FirebaseMessaging.getInstance().token
            .addOnCompleteListener(OnCompleteListener { task ->
                if (!task.isSuccessful) {
                    return@OnCompleteListener
                }
                val token = task.result
            })
    }
}
