package com.afgdevlab.expirydate.base.activity

import android.content.Intent
import androidx.databinding.ViewDataBinding
import com.afgdevlab.expirydate.utils.transition.Transition


abstract class BaseFadeInOutActivity<T:ViewDataBinding>(var layout :Int) : BaseActivity<T>(layout) {

    private var transition: Transition = Transition.TransitionFadeInOut()

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

    fun setTransition(transition: Transition) {
        this.transition = transition
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransitionExit()
    }
}