package com.afgdevlab.expirydate.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.afgdevlab.expirydate.base.viewmodel.BaseViewModel
import com.afgdevlab.expirydate.data.service.model.FriendlyMessage
import com.afgdevlab.expirydate.extensions.isNull
import com.afgdevlab.expirydate.extensions.notNull
import com.afgdevlab.expirydate.ui.common.components.loading.LottieProgressDialog
import com.afgdevlab.expirydate.utils.AutoClearedFragmentValue
import com.afgdevlab.expirydate.utils.LiveSession
import com.afgdevlab.expirydate.utils.analytics.FirebaseAnalyticsHelper
import hideKeyboard
import javax.inject.Inject

abstract class BaseFragment<T : ViewDataBinding>(var layoutId:Int) : Fragment() {

    protected var dataBinding: T by AutoClearedFragmentValue()

    private var progress: LottieProgressDialog? = null

    abstract fun getBaseViewModel(): BaseViewModel

    open fun bindScreen(){
        dataBinding.lifecycleOwner=this
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(
            inflater,
            layoutId,
            container,
            false
        )
        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bindScreen()

        toogleLoading()
        toogleErrorOrFail()
    }

    private fun toogleLoading(){
        getBaseViewModel().fragmentLoading.observe(this, Observer {
            if(it){
                showLoading()
            }else{
                hideLoading()
            }
        })
    }

    private fun toogleErrorOrFail(){
        getBaseViewModel().fragmentErrorOrFail.observe(this, Observer {
            when(it){
                is FriendlyMessage ->{}
                else->{ }
            }
        })
    }

    private fun showLoading(){
        hideLoading()
        this.context.notNull { context->
            progress.isNull {
                progress = LottieProgressDialog(context)
            }
            progress?.show()
        }
    }

    private fun hideLoading(){
        progress.notNull { it.cancel() }
    }

    fun closeKeyboard(){
        requireContext().hideKeyboard()
    }

}