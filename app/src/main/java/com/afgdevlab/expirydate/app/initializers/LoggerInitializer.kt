package com.afgdevlab.expirydate.app.initializers

import android.app.Application
import com.afgdevlab.expirydate.BuildConfig
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


class LoggerInitializer @Inject constructor() : AppInitializer {
    override fun init(application: Application) {
        Logger.addLogAdapter(object : AndroidLogAdapter() {
            override fun isLoggable(priority: Int, tag: String?): Boolean {
                return BuildConfig.DEBUG
            }
        })
    }
}