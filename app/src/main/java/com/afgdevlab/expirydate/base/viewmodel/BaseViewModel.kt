package com.afgdevlab.expirydate.base.viewmodel

import androidx.lifecycle.ViewModel
import com.afgdevlab.expirydate.utils.SingleLiveData
import com.afgdevlab.expirydate.utils.analytics.FirebaseAnalyticsHelper
import javax.inject.Inject

open class BaseViewModel : ViewModel() {

    @Inject
    lateinit var firebaseAnalyticsHelper: FirebaseAnalyticsHelper

    var activityLoading = SingleLiveData<Boolean>()
    protected fun toogleActivityLoading(status:Boolean) {
        activityLoading.value=status
    }

    var fragmentLoading = SingleLiveData<Boolean>()
    protected fun toogleFragmentLoading(status:Boolean) {
        fragmentLoading.value=status
    }

    var activityErrorOrFail = SingleLiveData<Any>()
    protected fun activityErrorOrFail(status:Any) {
        activityErrorOrFail.value=status
    }

    var fragmentErrorOrFail = SingleLiveData<Any>()
    protected fun fragmentErrorOrFail(status:Any) {
        fragmentErrorOrFail.value=status
    }

}
