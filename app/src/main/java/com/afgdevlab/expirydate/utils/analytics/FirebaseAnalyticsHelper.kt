package com.afgdevlab.expirydate.utils.analytics

import android.content.Context
import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.afgdevlab.expirydate.base.activity.BaseActivity
import com.afgdevlab.expirydate.base.fragment.BaseFragment
import javax.inject.Inject


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class FirebaseAnalyticsHelper @Inject constructor(
    context: Context
) : AnalyticsHelper {

    private var firebaseAnalytics: FirebaseAnalytics = FirebaseAnalytics.getInstance(context)

    init {
        firebaseAnalytics.setAnalyticsCollectionEnabled(true)
    }

    override fun setScreen(
        screenName: String,
        activity: BaseActivity<*>?
    ) {
        activity?.let {
            val params = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, it.javaClass.simpleName)
            }
            firebaseAnalytics.run {
                logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
            }
        }
    }

    override fun setScreenFragment(
        screenName: String,
        fragment: BaseFragment<*>?
    ) {
        fragment?.let {
            val params = Bundle().apply {
                putString(FirebaseAnalytics.Param.SCREEN_NAME, screenName)
                putString(FirebaseAnalytics.Param.SCREEN_CLASS, it.javaClass.simpleName)
            }
            firebaseAnalytics.run {
                logEvent(FirebaseAnalytics.Event.SCREEN_VIEW, params)
            }
        }
    }


    override fun event(
        event: AnalyticsActions.EVENT
    ) {
        event(event.eventId(), event.eventName(), event.eventType())
    }


    override fun event(
        id: String, name: String, type: String
    ) {
        val params = Bundle().apply {
            putString(FirebaseAnalytics.Param.ITEM_ID, id)
            putString(FirebaseAnalytics.Param.ITEM_NAME, name)
            putString(FirebaseAnalytics.Param.CONTENT_TYPE, type)
        }
        firebaseAnalytics.run {
            logEvent(FirebaseAnalytics.Event.SELECT_CONTENT, params)
        }
    }
}
