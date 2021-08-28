package com.afgdevlab.expirydate.app.initializers

import android.app.Application
import com.afgdevlab.expirydate.BuildConfig
import com.microsoft.appcenter.AppCenter
import com.microsoft.appcenter.analytics.Analytics
import com.microsoft.appcenter.crashes.Crashes
import javax.inject.Inject

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class AppCenterInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        AppCenter.start(
            application, BuildConfig.APPCENTER_KEY,
            Analytics::class.java,
            Crashes::class.java
        )
    }
}