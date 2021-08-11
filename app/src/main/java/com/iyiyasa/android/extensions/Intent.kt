package com.iyiyasa.android.extensions

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings


/**Example**/
/**
 * context.callThePhone("5554443322")
 * context.callThePhone("555 444 33 22")
 * context.callThePhone("05554443322")
 * context.callThePhone("0 555 444 33 22")
 * context.callThePhone("+905554443322")
 * context.callThePhone("+90 555 444 33 22")
 *
 * The context variable can be null
 */
fun Context?.callThePhone(phone: String) {
    if (this.isNull()) return
    val uri = Uri.parse("tel:$phone")
    val it = Intent(Intent.ACTION_DIAL, uri)
    this?.startActivity(it)
}

/**Example**/
/**
 *  context.sendEMail("loodos@loodos.com")
 * context.sendEMail("loodos@loodos.com.tr")
 *
 * The context variable can be null
 */
fun Context?.sendEMail(email: String) {
    if (this.isNull()) return
    val uri = Uri.parse("mailto:$email")
    val it = Intent(Intent.ACTION_SENDTO, uri)
    this?.startActivity(it)
}
/**Example**/
/**
 * context.sendSMS("5554443322")
 * context.sendSMS("555 444 33 22")
 * context.sendSMS("05554443322")
 * context.sendSMS("0 555 444 33 22")
 * context.sendSMS("+905554443322")
 * context.sendSMS("+90 555 444 33 22")
 *
 *  The context variable can be null
 */
fun Context?.sendSMS(phone: String) {
    if (this.isNull()) return
    val uri = Uri.parse("smsto:$phone")
    val it = Intent(Intent.ACTION_SENDTO, uri)
    this?.startActivity(it)
}

fun Context.redirectStore() {
    try {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("market://details?id=$packageName")
            )
        )
    } catch (anfe: ActivityNotFoundException) {
        startActivity(
            Intent(
                Intent.ACTION_VIEW,
                Uri.parse("https://play.google.com/store/apps/details?id=$packageName")
            )
        )
    }
}

fun Context?.openPDF(url: String) {
    val i = Intent(Intent.ACTION_VIEW)
    i.data = Uri.parse(url)
    this?.startActivity(i)
}

fun Context.DirectPermissions() {
    val intent = Intent()
    intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri: Uri = Uri.fromParts("package", packageName, null)
    intent.data = uri
    startActivity(intent)
}


fun Context?.share(text: String? = "", subject: String? = "", title: String? = "") {

    val sharingIntent = Intent(Intent.ACTION_SEND)

    sharingIntent.type = "text/plain"
    sharingIntent.putExtra(Intent.EXTRA_TEXT, text)
    sharingIntent.putExtra(Intent.EXTRA_SUBJECT, subject)

    val chooserIntent = Intent.createChooser(
        sharingIntent,
        if (title.isNullOrEmpty()) "" else title
    )
    this?.startActivity(chooserIntent)
}

fun Context?.directionMap(
    dest: String?
) {
    val intent = Intent(
        Intent.ACTION_VIEW,
        // Uri.parse("http://maps.google.com/maps?saddr=20.344,34.34&daddr=20.5666,45.345")
        Uri.parse("http://maps.google.com/maps?&daddr=$dest")
    )
    this?.startActivity(intent)
}


fun Context?.displayMap(dest: String?) {
    val gmmIntentUri = Uri.parse("geo:$dest")
    val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
    mapIntent.setPackage("com.google.android.apps.maps")
    this?.startActivity(mapIntent)
}

fun Context?.sendToNotificationPermissions() {
    val intent = Intent()
    when {
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O -> {
            intent.action = Settings.ACTION_APP_NOTIFICATION_SETTINGS
            intent.putExtra(Settings.EXTRA_APP_PACKAGE, this?.packageName)
        }
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> {
            intent.action = "android.settings.APP_NOTIFICATION_SETTINGS"
            intent.putExtra("app_package", this?.packageName)
            intent.putExtra("app_uid", this?.applicationInfo?.uid)
        }
        else -> {
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
            intent.addCategory(Intent.CATEGORY_DEFAULT)
            intent.data = Uri.parse("package:" + this?.packageName)
        }
    }
    this?.startActivity(intent)
}



