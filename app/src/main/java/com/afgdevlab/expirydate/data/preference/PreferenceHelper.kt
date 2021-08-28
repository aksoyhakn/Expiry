package com.afgdevlab.expirydate.data.preference

import com.afgdevlab.expirydate.utils.Constants

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

interface PreferenceHelper {
    fun getLanguage(): String?
    fun setLanguage(languageCode: String?)

    fun getBaseURL(): String?
    fun setBaseURL(languageCode: String?)

    fun getAccessToken(): String?
    fun setAccessToken(languageCode: String?)

    fun getCurrentUserLoggedInMode(): Int
    fun setCurrentUserLoggedInMode(mode: Constants.App.LoggedInMode)

    fun setOpenOnboarding(isOpen: Boolean?)
    fun getOpenOnboarding(): Boolean?
}