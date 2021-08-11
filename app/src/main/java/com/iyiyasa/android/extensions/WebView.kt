package com.iyiyasa.android.extensions

import android.webkit.GeolocationPermissions
import android.webkit.WebChromeClient
import android.webkit.WebView
import androidx.databinding.BindingAdapter
import java.util.*


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@BindingAdapter("loadHtml", "setTitle")
fun WebView.loadHtml(
    description: String?,
    title: String?
) {
    isVerticalScrollBarEnabled = false
    isHorizontalScrollBarEnabled = false
    isScrollContainer = false
    settings.javaScriptEnabled = true
    settings.loadWithOverviewMode = true
    settings.useWideViewPort = true
    settings.builtInZoomControls = false
    settings.defaultFontSize = 45

    webChromeClient = object : WebChromeClient() {
        override fun onGeolocationPermissionsShowPrompt(
            origin: String,
            callback: GeolocationPermissions.Callback
        ) {
            callback.invoke(origin, true, false)
        }
    }
    /*webViewClient = object : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
            Utils.checkLink(url, view?.context as BaseSlideActivity, title)
            return true
        }
    }
    webChromeClient = WebChromeClient()
    description?.let {
        if (it.contains("font-family")) {
            loadDataWithBaseURL(
                null,
                description,
                "text/html",
                "utf-8",
                null
            )
        } else {
            loadDataWithBaseURL(
                "file:///android_asset/",
                getStyledFont(it),
                "text/html; charset=UTF-8",
                null,
                "about:blank"
            )
        }
    }
    }*/
}

fun getStyledFont(description: String): String {
    val addBodyStart = !description.toLowerCase(Locale.getDefault()).contains("<body>")
    val addBodyEnd = !description.toLowerCase(Locale.getDefault()).contains("</body")
    return "<style type=\"text/css\">@font-face {font-family: CustomFont; " +
            "src: url(\"file:///android_asset/fonts/jost_regular_font.ttf\")}" +
            "body {font-family: CustomFont;color:#607486;}</style>" +
            (if (addBodyStart) "<body>" else "") + description + if (addBodyEnd) "</body>" else ""
}