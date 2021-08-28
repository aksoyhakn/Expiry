package com.afgdevlab.expirydate.base.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.service.model.FriendlyMessage
import com.afgdevlab.expirydate.extensions.initStatusBar
import com.afgdevlab.expirydate.extensions.isNull
import com.afgdevlab.expirydate.extensions.notNull
import com.afgdevlab.expirydate.ui.common.components.loading.LottieProgressDialog
import com.afgdevlab.expirydate.utils.AutoClearedActivityValue
import com.afgdevlab.expirydate.utils.AutoClearedFragmentValue
import com.afgdevlab.expirydate.utils.LiveSession
import com.afgdevlab.expirydate.utils.analytics.FirebaseAnalyticsHelper
import com.afgdevlab.expirydate.utils.language.AppContextWrapper
import com.afgdevlab.expirydate.utils.language.AppLanguageProvider
import hideKeyboard
import javax.inject.Inject

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

abstract class BaseActivity<T : ViewDataBinding>(
    var layoutID: Int
) : AppCompatActivity() {

    protected var dataBinding: T by AutoClearedActivityValue()

    private var activityLoading: LottieProgressDialog? = null

    abstract fun getBaseViewModel(): BaseViewModel

    open fun bindScreen(){
        dataBinding.lifecycleOwner=this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dataBinding = DataBindingUtil.setContentView(this, layoutID)

        setToolbar()
        bindScreen()

        toogleLoading()
        toogleErrorOrFail()
    }

    private fun toogleLoading(){
        getBaseViewModel().activityLoading.observe(this, Observer {
            if(it){
                showLoading()
            }else{
                hideLoading()
            }
        })
    }

    private fun toogleErrorOrFail(){
        getBaseViewModel().activityErrorOrFail.observe(this, Observer {
            when(it){
                is FriendlyMessage->{}
                else->{ }
            }
        })
    }

    private fun setToolbar() {
        this.initStatusBar()
    }

    fun showLoading(){
        hideLoading()
        activityLoading.isNull {
            activityLoading = LottieProgressDialog(this)
        }
        activityLoading?.show()
    }

    fun hideLoading(){
        activityLoading.notNull { it.cancel() }
    }

    fun closeKeyboard(){
        hideKeyboard()
    }


    override fun onBackPressed() {
        if (supportFragmentManager.backStackEntryCount > 0) {
            super.onBackPressed()
        } else {
            hideKeyboard()
            finish()
        }
    }


    override fun applyOverrideConfiguration(overrideConfiguration: Configuration?) {
        super.applyOverrideConfiguration(
            AppContextWrapper.wrap(
                applicationContext,
                AppLanguageProvider.instance.getAppLanguage(applicationContext)
            ).resources.configuration
        )
    }

    override fun attachBaseContext(newBase: Context) {

        super.attachBaseContext(
            AppContextWrapper.wrap(
                newBase,
                AppLanguageProvider.instance.getAppLanguage(newBase)
            )
        )
    }

    companion object {
        var taskCounter = 0
    }
}