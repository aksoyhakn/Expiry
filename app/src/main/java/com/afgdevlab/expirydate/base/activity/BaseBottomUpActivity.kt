package com.afgdevlab.expirydate.base.activity

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.afgdevlab.expirydate.utils.transition.Transition


abstract class BaseBottomUpActivity<T:ViewDataBinding>(var layout :Int) : BaseActivity<T>(layout) {

    private var transition: Transition = Transition.TransitionSlideUpDown()

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