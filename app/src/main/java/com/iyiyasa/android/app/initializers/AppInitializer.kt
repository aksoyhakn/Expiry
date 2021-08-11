package com.iyiyasa.android.app.initializers

import android.app.Application

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


interface AppInitializer {
    fun init(application: Application)
}