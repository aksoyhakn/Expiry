package com.afgdevlab.expirydate.di

import com.afgdevlab.expirydate.app.initializers.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Module
@InstallIn(SingletonComponent::class)
object InitializerModule {

    @Provides
    @IntoSet
    fun provideLogger(): AppInitializer = LoggerInitializer()

    @Provides
    @IntoSet
    fun provideAppCenterInitializer(): AppInitializer = AppCenterInitializer()

    @Provides
    @IntoSet
    fun provideFirebaseInitializer(): AppInitializer = FirebaseInitializer()

    @Provides
    @IntoSet
    fun provideCrashlyticsInitializer(): AppInitializer = CrashlyticsInitializer()
}
