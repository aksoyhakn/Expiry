package com.iyiyasa.android.utils.language

import android.content.Context
import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.data.preference.PreferenceHelperImp
import java.io.Serializable
import java.util.*
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class AppLanguageProvider @Inject constructor() {
    private var appLanguage: LanguageType? = DEFAULT_LANG

    fun getAppLanguage(context: Context): LanguageType {

        val currentLanguage = PreferenceHelperImp(
            context,
            PreferenceHelperImp.PREF_KEY_LANGUAGE
        ).getLanguage()

        appLanguage = if (currentLanguage.isNullOrEmpty()) {
            when (Locale.getDefault().displayLanguage) {
                LanguageType.TURKISH.value() -> {
                    LanguageType.TURKISH
                }
                LanguageType.ENGLISH.value() -> {
                    LanguageType.ENGLISH
                }
                else -> DEFAULT_LANG
            }
        } else {
            when (currentLanguage) {
                LanguageType.TURKISH.code() -> LanguageType.TURKISH
                LanguageType.ENGLISH.code() -> LanguageType.ENGLISH
                else -> DEFAULT_LANG
            }
        }
        return appLanguage!!
    }

    fun setLanguage(activity: BaseActivity<*>, languageType: LanguageType) {
        PreferenceHelperImp(
            activity,
            PreferenceHelperImp.PREF_KEY_LANGUAGE
        ).setLanguage(languageType.code())
        BusProvider.getInstance().post(LanguageChangeEvent())
    }

    companion object {

        val DEFAULT_LANG = LanguageType.TURKISH
        private var appLanguageProvider: AppLanguageProvider? = null

        val instance: AppLanguageProvider
            get() {
                if (appLanguageProvider == null) {
                    appLanguageProvider =
                        AppLanguageProvider()
                }
                return appLanguageProvider as AppLanguageProvider
            }
    }

    enum class LanguageType {

        TURKISH {
            override fun value() = "Türkçe"
            override fun code() = "tr"
        },
        ENGLISH {
            override fun value() = "English"
            override fun code() = "en"
        };

        abstract fun value(): String
        abstract fun code(): String
    }
}

class LanguageChangeEvent() : Serializable