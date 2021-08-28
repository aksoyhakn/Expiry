package com.afgdevlab.expirydate.di

import android.app.Application
import android.content.Context
import com.afgdevlab.expirydate.BuildConfig
import com.afgdevlab.expirydate.app.IyiyasaApp
import com.afgdevlab.expirydate.data.preference.PreferenceHelper
import com.afgdevlab.expirydate.data.preference.PreferenceHelperImp
import com.afgdevlab.expirydate.data.service.LoggingInterceptor
import com.afgdevlab.expirydate.data.service.IyiyasaDataSource
import com.afgdevlab.expirydate.data.service.IyiyasaService
import com.afgdevlab.expirydate.utils.Constants
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.readystatesoftware.chuck.ChuckInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    internal fun providePrefFileName(): String = Constants.App.PREF_NAME

    @Provides
    internal fun providePrefHelper(appPreferenceHelper: PreferenceHelperImp): PreferenceHelper =
        appPreferenceHelper

    @Provides
    fun provideContext(application: Application): Context =
        IyiyasaApp.instance.applicationContext

    @Provides
    fun provideChuckInterceptor(application: Application) = ChuckInterceptor(application)

    @Provides
    fun provideInterceptor(context: Context) = LoggingInterceptor(
        PreferenceHelperImp(context, providePrefFileName())
    )

    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: LoggingInterceptor,
        chuckInterceptor: ChuckInterceptor
    ): OkHttpClient {
        if (BuildConfig.GADGET_ENABLED) {
            return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(chuckInterceptor)
                .build()
        } else {
            return OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .addInterceptor(loggingInterceptor)
                .build()
        }
    }

    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, context: Context): Retrofit {
        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(CoroutineCallAdapterFactory())
            .build()
    }

    @Provides
    @Singleton
    fun provideSweatERSService(retrofit: Retrofit): IyiyasaService {
        return retrofit.create(IyiyasaService::class.java)
    }

    @Provides
    @Singleton
    fun provideSweatERSClient(
        sosyalLigService: IyiyasaService
    ): IyiyasaDataSource {
        return IyiyasaDataSource(sosyalLigService)
    }
}