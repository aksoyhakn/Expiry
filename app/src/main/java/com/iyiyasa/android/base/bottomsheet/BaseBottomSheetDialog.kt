package com.iyiyasa.android.base.bottomsheet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.FragmentManager
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.iyiyasa.android.R
import com.iyiyasa.android.utils.analytics.FirebaseAnalyticsHelper
import javax.inject.Inject


abstract class BaseBottomSheetDialog<DB : ViewDataBinding> : BottomSheetDialogFragment() {

    lateinit var dataBinding: DB

    @Inject
    lateinit var firebaseAnalyticsHelper: FirebaseAnalyticsHelper

    @LayoutRes
    abstract fun getLayoutResId(): Int

    abstract fun bindScreen()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NO_FRAME, R.style.BottomSheetDialogStyle)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutResId(), container, false)
        return dataBinding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bindScreen().also { bindScreen() }
    }

    fun showBottomSheet(fragmentManager: FragmentManager) {
        show(fragmentManager, "BOTTOM_SHEET")
    }

    fun dismissBottomSheet() {
        dismissAllowingStateLoss()
    }
}