package com.afgdevlab.expirydate.utils.deeplink

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import com.google.android.gms.tasks.Task
import com.google.firebase.dynamiclinks.DynamicLink
import com.google.firebase.dynamiclinks.FirebaseDynamicLinks
import com.google.firebase.dynamiclinks.ShortDynamicLink
import com.google.gson.annotations.SerializedName
import com.afgdevlab.expirydate.extensions.isNull
import com.afgdevlab.expirydate.utils.Constants
import com.afgdevlab.expirydate.utils.deeplink.DeeplinkProvider.DeeplinkConstants.PAGE_NAME
import com.afgdevlab.expirydate.utils.deeplink.DeeplinkProvider.DeeplinkConstants.PATH_HOME
import com.afgdevlab.expirydate.utils.language.BusProvider
import kotlinx.android.parcel.Parcelize


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


class DeeplinkProvider {

    object DeeplinkConstants {
        const val ARG_PARAMS = "deeplinkParams"
        const val PAGE_NAME = "pageName"
        const val PATH_HOME = "home"
    }

    var deepLink: Uri? = null
        private set
    var path: String? = null
    var params: DeeplinkParam = DeeplinkParam("", hashMapOf())


    companion object {

        var deeplinkProvider: DeeplinkProvider? = null

        fun getInstance(): DeeplinkProvider? {
            if (deeplinkProvider == null) {
                synchronized(DeeplinkProvider::class.java) {
                    if (deeplinkProvider == null) {
                        deeplinkProvider = DeeplinkProvider()
                    }
                }
            }
            return deeplinkProvider
        }

    }

    fun share(
        path: String,
        parameter: String,
        shareCallback: ShareCallback
    ) {
        shortenLink(
            createInviteLink(
                createShareUri(path, parameter)
            ).uri, shareCallback
        )
    }

    private fun createShareUri(
        path: String,
        parameter: String
    ): Uri {
        val builder = Uri.Builder()
        builder.scheme(Constants.DynamicLinkConstants.SCHEME)
            .authority(Constants.DynamicLinkConstants.HOST)
            .appendQueryParameter("pageName", path)
            .appendQueryParameter("id", parameter)
        return builder.build()
    }

    private fun createInviteLink(
        link: Uri
    ): DynamicLink {
        return FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLink(link)
            .setDynamicLinkDomain(Constants.DynamicLinkConstants.DOMAIN)
            .setNavigationInfoParameters(DynamicLink.NavigationInfoParameters.Builder().build())
            .setAndroidParameters(
                DynamicLink.AndroidParameters.Builder(Constants.DynamicLinkConstants.ANDROID_BUNDLE_ID)
                    .build()
            )
            .setIosParameters(
                DynamicLink.IosParameters.Builder(Constants.DynamicLinkConstants.IOS_BUNDLE_ID)
                    .build()
            )
            .buildDynamicLink()
    }

    private fun shortenLink(
        linkUri: Uri,
        shareCallback: ShareCallback
    ) {
        FirebaseDynamicLinks.getInstance().createDynamicLink()
            .setLongLink(linkUri)
            .buildShortDynamicLink()
            .addOnCompleteListener { task: Task<ShortDynamicLink> ->
                if (task.isSuccessful) {
                    val shortLink = task.result!!.shortLink
                    shareCallback.onShare(shortLink)
                } else {
                    shareCallback.onShare(linkUri)
                }
            }
    }

    interface ShareCallback {
        fun onShare(link: Uri?)
    }

    fun run(currentActivity: Activity, deeplink: String) {
        controlDeeplink(currentActivity, Uri.parse(deeplink))
    }

    fun run(currentActivity: Activity, intent: Intent) {
        FirebaseDynamicLinks.getInstance()
            .getDynamicLink(intent)
            .addOnSuccessListener(currentActivity) { pendingDynamicLinkData ->
                val deepLink: Uri
                if (pendingDynamicLinkData != null) {
                    deepLink = pendingDynamicLinkData.link!!
                    controlDeeplink(currentActivity, deepLink)
                } else {
                    controlDeeplink(currentActivity, intent.data)
                }
            }
            .addOnFailureListener(currentActivity) { e ->
                controlDeeplink(currentActivity, intent.data)
            }
    }

    private fun controlDeeplink(currentActivity: Activity, deepLink: Uri?) {
        this.deepLink = deepLink

        val queryParamNames = deepLink?.queryParameterNames?.toList()
        if (!deepLink?.pathSegments.isNullOrEmpty() && deepLink?.pathSegments?.get(0) != null && deepLink.pathSegments?.get(
                0
            )!!.isNotEmpty()
        ) {
            params.params?.put(PAGE_NAME, deepLink.pathSegments[0])
        }

        if (!queryParamNames.isNullOrEmpty()) {
            for (i in queryParamNames.indices) {
                params.params?.put(
                    queryParamNames[i],
                    deepLink.getQueryParameter(queryParamNames[i])!!
                )
            }
        }

        val bundle = Bundle()
        bundle.putParcelable("ARG_PARAMS", params)
        var aClass: Class<*>? = null
        if (!params.params.isNull()) {
            val pageName = params.params?.get(PAGE_NAME)
            if (!pageName.isNullOrBlank()) {
                params.pageName = pageName
                when (pageName) {
                    PATH_HOME -> {

                    }
                }
            }
        }

        if (currentActivity is LandingPageActivity) {
            currentActivity.finish()
        }
        if (aClass != null) {
            BusProvider.getInstance().post(DeeplinkDirectionEvent(aClass, bundle))
        }
        this.deepLink = null
        this.path = null
        this.params = DeeplinkParam("", hashMapOf())
        aClass = null

    }
}


@Parcelize
class DeeplinkParam(
    @SerializedName("pageName") var pageName: String?,
    @SerializedName("params") val params: HashMap<String, String>?
) : Parcelable


@Parcelize
class DeeplinkDirectionEvent(var aClass: Class<*>?, var bundle: Bundle) : Parcelable