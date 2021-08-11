package com.iyiyasa.android.utils.analytics

import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.base.fragment.BaseFragment


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

interface AnalyticsHelper {

    fun setScreen(
        screenName: String,
        activity: BaseActivity<*>?
    )

    fun setScreenFragment(
        screenName: String,
        fragment: BaseFragment<*>?
    )

    fun event(
        event: AnalyticsActions.EVENT
    )

    fun event(
        id: String, name: String, type: String
    )
}

object AnalyticsActions {
    enum class EVENT {

        LOGIN {
            override fun eventId() = "click_login"
            override fun eventName() = "LOGIN"
            override fun eventType() = "LOGIN"
        };

        abstract fun eventId(): String
        abstract fun eventName(): String
        abstract fun eventType(): String
    }
}

object SCREEN {
    const val Splash = "Splash"
}