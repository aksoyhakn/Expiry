package com.iyiyasa.android.base.activity

import com.iyiyasa.android.base.activity.BaseActivity
import com.iyiyasa.android.utils.transition.Transition
import android.content.Intent
import androidx.databinding.ViewDataBinding

abstract class BaseSlideActivity<T:ViewDataBinding>(var layout :Int) : BaseActivity<T>(layout) {

    private var transition: Transition = Transition.TransitionSlide()

    override fun finish() {
        super.finish()
        overridePendingTransitionExit()
    }

    override fun startActivity(intent: Intent) {
        super.startActivity(intent)
        overridePendingTransitionEnter()
    }

    private fun overridePendingTransitionEnter() {
        overridePendingTransition(transition.enterAnim, transition.exitAnim)
    }

    private fun overridePendingTransitionExit() {
        overridePendingTransition(transition.enterAnimBack, transition.exitAnimBack)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }
}