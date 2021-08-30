package com.afgdevlab.expirydate.extensions

import android.Manifest
import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.view.*
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import com.afgdevlab.expirydate.R
import com.afgdevlab.expirydate.databinding.DialogDefaultBinding
import com.afgdevlab.expirydate.databinding.DialogOpenProductBinding
import com.afgdevlab.expirydate.utils.Constants.Camera.REQUEST_CODE_CAMERA_PERMISSION
import pub.devrel.easypermissions.EasyPermissions
import pub.devrel.easypermissions.PermissionRequest

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

fun Context.isCameraPermission(): Boolean = EasyPermissions.hasPermissions(
    this,
    Manifest.permission.CAMERA
)


fun Activity.requestCameraPermission2() {
    EasyPermissions.requestPermissions(
        this,
        resString(R.string.camera_permission),
        REQUEST_CODE_CAMERA_PERMISSION,
        Manifest.permission.CAMERA
    )
}


fun Activity.requestCameraPermission() {
    EasyPermissions.requestPermissions(
        PermissionRequest.Builder(this, REQUEST_CODE_CAMERA_PERMISSION, Manifest.permission.CAMERA)
            .setRationale(resString(R.string.camera_permission))
            .setPositiveButtonText(resString(R.string.camera_permission_button_positive))
            .setNegativeButtonText(resString(R.string.camera_permission_button_negative))
            .setTheme(R.style.AlertDialogTheme)
            .build()
    )
}


fun Context.showDialog(mesaj:String,isSuccess: Boolean? = false) {
    this.notNull {
        val dialog = Dialog(it)
        val binding: DialogDefaultBinding =
            DataBindingUtil.inflate(dialog.layoutInflater, R.layout.dialog_default, null, false)

        dialog.setCancelable(false)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        binding.isSuccess=isSuccess
        binding.mesaj=mesaj
    }
}




fun Context.showProductStatus(isOpenProduct:Boolean,listenerOpenPackage: () -> Unit,listenerDelete: () -> Unit) {
    this.notNull {
        val dialog = Dialog(it)
        val binding: DialogOpenProductBinding =
            DataBindingUtil.inflate(dialog.layoutInflater, R.layout.dialog_open_product, null, false)

        dialog.setCancelable(false)
        dialog.setContentView(binding.root)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.attributes?.windowAnimations = R.style.DialogAnimation
        dialog.setCanceledOnTouchOutside(true)
        dialog.show()

        if(isOpenProduct){
            binding.llOpenpackage.visibility=View.GONE
        }

        binding.llOpenpackage.setRXSafeOnClickListener {
            dialog.dismiss()
            listenerOpenPackage()
        }

        binding.llDelete.setRXSafeOnClickListener {
            dialog.dismiss()
            listenerDelete()
        }

    }
}

