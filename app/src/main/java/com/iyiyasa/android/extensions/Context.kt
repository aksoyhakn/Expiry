package com.iyiyasa.android.extensions

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.text.SpannableString
import android.util.DisplayMetrics
import android.view.*
import androidx.core.content.ContextCompat
import com.iyiyasa.android.R

fun Activity?.setWindowFlag(on: Boolean) {
    this?.let {
        val win = it.window
        val winParams = win.attributes
        if (on) {
            winParams.flags =
                winParams.flags or WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        } else {
            winParams.flags =
                winParams.flags and WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS.inv()
        }
        win.attributes = winParams
    }
}

fun Activity?.initStatusBar(): Int {
    this?.let {
        if (Build.VERSION.SDK_INT in 19..20) {
            it.setWindowFlag(true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        //make fully Android Transparent Status bar
        if (Build.VERSION.SDK_INT >= 21) {
            it.setWindowFlag(false)
            window.statusBarColor = ContextCompat.getColor(it, R.color.statusbar)
        }
    }

    return -1
}

fun Context.getNavigationbarHeight(): Int {
    val result = 0
    val hasMenuKey = ViewConfiguration.get(this).hasPermanentMenuKey()
    val hasBackKey = KeyCharacterMap.deviceHasKey(KeyEvent.KEYCODE_BACK)

    if (!hasMenuKey && !hasBackKey) { //The device has a navigation bar
        val orientation: Int = resources.configuration.orientation
        val resourceId: Int
        resourceId = if (isTablet()) {
            resources.getIdentifier(
                if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height" else "navigation_bar_height_landscape",
                "dimen",
                "android"
            )
        } else {
            resources.getIdentifier(
                if (orientation == Configuration.ORIENTATION_PORTRAIT) "navigation_bar_height" else "navigation_bar_width",
                "dimen",
                "android"
            )
        }
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId)
        }
    }
    return result
}

fun Context?.loadJSONFromAsset(jsonFileName: String): String? {
    this.notNull {
        val manager = it.assets
        val `is` = manager.open(jsonFileName)

        val size = `is`.available()
        val buffer = ByteArray(size)
        `is`.read(buffer)
        `is`.close()
        return String(buffer, Charsets.UTF_8)
    }

    return null
}

fun Context.appendTextColor(
    findCountText: String,
    textColor: Int
): SpannableString {
    val spannable = SpannableString(findCountText)

    spannable.setTextColor(
        this,
        textColor,
        start = 0,
        end = findCountText.length
    )

    return spannable
}


fun Context.handler(duration: Long, listener: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({
        listener()
    }, duration)
}

fun Context.getToday(): String {
    return when (CURRENT_TIME.time.hourOfDay) {
        in 5..12 -> {
            "resString(R.string.today_goodmorning)"
        }
        in 12..19 -> {
            " resString(R.string.today_goodday)"
        }
        in 12..23 -> {
            "resString(R.string.today_goodevening)"
        }
        else -> {
            "resString(R.string.today_goodnight)"
        }
    }
}

