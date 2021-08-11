package com.iyiyasa.android.data.preference

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
import com.iyiyasa.android.BuildConfig
import com.iyiyasa.android.utils.Constants
import javax.inject.Inject

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class PreferenceHelperImp @Inject constructor(
    context: Context,
    prefFileName: String
) : PreferenceHelper {

    companion object {
        const val PREF_KEY_LANGUAGE = "PREF_KEY_LANGUAGE"
        const val PREF_KEY_BASE_URL = "PREF_KEY_BASE_URL"
        const val PREF_KEY_ACCESS_TOKEN = "PREF_KEY_ACCESS_TOKEN"
        const val PREF_KEY_USER_LOGGED_IN_MODE = "PREF_KEY_USER_LOGGED_IN_MODE"
        const val PREF_KEY_OPEN_ONBOARDING = "PREF_KEY_OPEN_ONBOARDING"
    }

    private val mPrefs: SharedPreferences =
        context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

    override fun getLanguage(): String? = mPrefs.getString(PREF_KEY_LANGUAGE, "")

    override fun setLanguage(languageCode: String?) {
        mPrefs.edit { putString(PREF_KEY_LANGUAGE, languageCode) }
    }

    override fun getBaseURL(): String? =
        mPrefs.getString(PREF_KEY_BASE_URL, BuildConfig.BASE_URL)

    override fun setBaseURL(languageCode: String?) {
        mPrefs.edit { putString(PREF_KEY_BASE_URL, languageCode) }
    }

    override fun getAccessToken(): String? = mPrefs.getString(PREF_KEY_ACCESS_TOKEN, "")

    override fun setAccessToken(accessToken: String?) =
        mPrefs.edit { putString(PREF_KEY_ACCESS_TOKEN, "Bearer $accessToken") }

    override fun getCurrentUserLoggedInMode() = mPrefs.getInt(
        PREF_KEY_USER_LOGGED_IN_MODE,
        Constants.App.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type
    )

    override fun setCurrentUserLoggedInMode(mode: Constants.App.LoggedInMode) {
        mPrefs.edit { putInt(PREF_KEY_USER_LOGGED_IN_MODE, mode.type) }
    }

    fun isUserLoggedIn() =
        getCurrentUserLoggedInMode() != (Constants.App.LoggedInMode.LOGGED_IN_MODE_LOGGED_OUT.type)
                && getCurrentUserLoggedInMode() != (Constants.App.LoggedInMode.LOGGED_IN_MODE_PROFILE.type)

    override fun getOpenOnboarding(): Boolean? = mPrefs.getBoolean(PREF_KEY_OPEN_ONBOARDING, false)

    override fun setOpenOnboarding(isOpen: Boolean?) =
        mPrefs.edit { putBoolean(PREF_KEY_OPEN_ONBOARDING, isOpen ?: false) }
}