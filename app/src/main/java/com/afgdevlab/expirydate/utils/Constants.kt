package com.afgdevlab.expirydate.utils

import com.afgdevlab.expirydate.BuildConfig

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

object Constants {

    object App {
        const val TIMESTAMP_FORMAT = "yyyy-MM-dd'T'HH:mm:ssZ"
        const val NOTIFI_FORMAT = "dd-MM-yyyy"
        const val DB_NAME = "iyi-yasa"
        const val PREF_NAME = "iyi-yasa-pref"

        enum class LoggedInMode constructor(val type: Int) {
            LOGGED_IN_MODE_LOGGED_OUT(0),
            LOGGED_IN_MODE_PROFILE(1),
            LOGGED_IN_MODE_SERVER(2)
        }
    }

    object Camera {
        val PICK_PICTURE_FROM_DOCUMENTS = 34961
        val PICK_PICTURE_FROM_GALLERY = 34962
        val PICK_PICTURE_FROM_CHOOSER = 34963
        val TAKE_PICTURE = 34964
        val CAPTURE_VIDEO = 34965
        val REQUEST_CODE_CAMERA_PERMISSION = 100
    }


    object DynamicLinkConstants {
        val DOMAIN = "sweaters.page.link"
        val HOST = "sweatersapp.com"
        val SCHEME = "https"
        val ANDROID_BUNDLE_ID = BuildConfig.APPLICATION_ID
        val IOS_BUNDLE_ID = "com.sweaters.iosbeta"
    }

    object AddProduct {
        val ADD_PRODUCT = "ADD_PRODUCT"
    }

    object Notification {
        val NOTIFICATION_CHANNEL_ID = "NOTIFICATION_CHANNEL_ID"
        val NOTIFICATION_CHANNEL_NAME = "NOTIFICATION_CHANNEL_NAME"
    }

    object Barcode {
        val BARCODE_PRODUCT = "BARCODE_PRODUCT"
        val BARCODE_ERROR= "BARCODE_ERROR"
    }

}