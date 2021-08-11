package com.iyiyasa.android.utils.deeplink

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.iyiyasa.android.base.activity.BaseActivity.Companion.taskCounter
import com.iyiyasa.android.extensions.isNotNull
import com.iyiyasa.android.ui.splash.SplashActivity
import com.iyiyasa.android.utils.notification.AppFirebaseMessagingService.Companion.deeplinkFromPush


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class LandingPageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState == null) {
            handleDeepLink()
        }
    }

    private fun handleDeepLink() {
        val data = intent.dataString
        if (data.isNotNull()) {

            if (!deeplinkFromPush.isNullOrBlank()) {
                intent.data = Uri.parse(deeplinkFromPush)
            }

            if (taskCounter == 0) {
                val directiveIntent = Intent(this, SplashActivity::class.java)
                directiveIntent.apply {
                    this.data = intent.data
                    if (intent.extras != null) {
                        putExtras(intent.extras!!)
                    }
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    flags = Intent.FLAG_ACTIVITY_SINGLE_TOP
                    flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(this)
                    overridePendingTransition(0, 0)

                }
            } else {
                DeeplinkProvider.getInstance()?.run(this, intent)
                deeplinkFromPush = null
                this.finish()
            }
        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        handleDeepLink()
    }

    override fun onDestroy() {
        super.onDestroy()
        overridePendingTransition(0, 0);
    }
}