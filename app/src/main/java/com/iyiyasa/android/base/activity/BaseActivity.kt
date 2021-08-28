package com.iyiyasa.android.base.activity

import android.content.Context
import android.content.res.Configuration
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.Observer
import com.iyiyasa.android.base.viewmodel.BaseViewModel
import com.iyiyasa.android.data.service.model.FriendlyMessage
import com.iyiyasa.android.extensions.initStatusBar
import com.iyiyasa.android.extensions.isNull
import com.iyiyasa.android.extensions.notNull
import com.iyiyasa.android.ui.common.components.loading.LottieProgressDialog
import com.iyiyasa.android.utils.AutoClearedActivityValue
import com.iyiyasa.android.utils.AutoClearedFragmentValue
import com.iyiyasa.android.utils.LiveSession
import com.iyiyasa.android.utils.analytics.FirebaseAnalyticsHelper
import com.iyiyasa.android.utils.language.AppContextWrapper
import com.iyiyasa.android.utils.language.AppLanguageProvider
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