package com.afgdevlab.expirydate.base.activity

import com.afgdevlab.expirydate.base.activity.BaseActivity
import android.content.Intent
import androidx.databinding.ViewDataBinding

abstract class BaseNoAnimActivity(var layout:Int) : BaseActivity<ViewDataBinding>(layout) {

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    private fun overridePendingTransitionEnter() {
        overridePendingTransition(0, 0)
    }

    private fun overridePendingTransitionExit() {
        overridePendingTransition(0, 0)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }
}