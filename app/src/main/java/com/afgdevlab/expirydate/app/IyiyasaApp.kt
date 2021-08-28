package com.afgdevlab.expirydate.app

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.afgdevlab.expirydate.app.initializers.AppInitializers
import com.afgdevlab.expirydate.di.DaggerAppInitializerComponent
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.ExperimentalCoroutinesApi
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@ExperimentalCoroutinesApi
@HiltAndroidApp
class IyiyasaApp : Application() {

    @Inject
    lateinit var initializers: AppInitializers

    override fun onCreate() {
        super.onCreate()
        DaggerAppInitializerComponent.builder().build()
        initializers.init(this)
        instance = this
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }

    companion object {
        lateinit var instance: IyiyasaApp
    }
}
