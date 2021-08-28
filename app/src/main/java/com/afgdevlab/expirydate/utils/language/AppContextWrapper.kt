package com.afgdevlab.expirydate.utils.language

import android.content.Context
import android.content.ContextWrapper
import android.os.Build
import android.os.LocaleList
import java.util.*


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class AppContextWrapper {

    companion object {
        fun wrap(
            contxt: Context,
            appLang: AppLanguageProvider.LanguageType
        ): ContextWrapper {

            val locale: Locale = when (appLang) {
                AppLanguageProvider.LanguageType.TURKISH -> Locale(
                    AppLanguageProvider.LanguageType.TURKISH.code()
                )
                AppLanguageProvider.LanguageType.ENGLISH -> Locale(
                    AppLanguageProvider.LanguageType.ENGLISH.code()
                )
            }

            Locale.setDefault(locale)

            var context = contxt
            val configuration = context.resources.configuration

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {

                configuration.setLocale(locale)
                val localeList = LocaleList(locale)
                LocaleList.setDefault(localeList)
                configuration.setLocales(localeList)
                configuration.setLayoutDirection(locale);
                context = context.createConfigurationContext(configuration)

            } else {

                configuration.locale = locale
                configuration.setLayoutDirection(locale);
                context.resources.updateConfiguration(
                    configuration, context.resources.displayMetrics
                )
            }

            return ContextWrapper(context)
        }
    }

}