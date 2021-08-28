package com.afgdevlab.expirydate.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.afgdevlab.expirydate.data.persistence.AppDatabase
import com.afgdevlab.expirydate.data.persistence.IyiyasaDao
import com.afgdevlab.expirydate.utils.Constants
import com.afgdevlab.expirydate.utils.LiveSession
import com.afgdevlab.expirydate.utils.analytics.AnalyticsHelper
import com.afgdevlab.expirydate.utils.analytics.FirebaseAnalyticsHelper
import com.afgdevlab.expirydate.utils.language.AppLanguageProvider
import com.afgdevlab.expirydate.utils.language.BusProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Module
@InstallIn(SingletonComponent::class)
object PersistenceModule {

    @Provides
    fun provideAppLanguageProvider(): AppLanguageProvider = AppLanguageProvider.instance

    @Provides
    @Singleton
    fun providesLiveSession(): LiveSession = LiveSession()

    @Provides
    @Singleton
    fun providesFirebaseAnalyticsHelper(context: Context): AnalyticsHelper =
        FirebaseAnalyticsHelper(context)

    @Provides
    fun provideBusProvider(): BusProvider = BusProvider()

    @Provides
    fun provideAppDatabase(
        application: Application
    ): AppDatabase {
        return Room
            .databaseBuilder(application, AppDatabase::class.java, Constants.App.DB_NAME)
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    fun provideSweatERSDao(appDatabase: AppDatabase): IyiyasaDao {
        return appDatabase.iyiyasaDAO()
    }

}