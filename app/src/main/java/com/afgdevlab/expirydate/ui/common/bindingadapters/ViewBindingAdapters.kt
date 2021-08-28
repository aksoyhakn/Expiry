package com.afgdevlab.expirydate.ui.common.bindingadapters

import android.view.View
import androidx.databinding.BindingAdapter
import com.afgdevlab.expirydate.extensions.setRXSafeOnClickListener


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


object ViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("bind:visibleIf")
    fun changeVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.GONE
    }

    @JvmStatic
    @BindingAdapter("bind:invisibleIf")
    fun changeInVisibility(view: View, visible: Boolean) {
        view.visibility = if (visible) View.VISIBLE else View.INVISIBLE
    }


    @JvmStatic
    @BindingAdapter("onSafeClick")
    fun onSafeClick(view: View, listener: View.OnClickListener) {
        view.setRXSafeOnClickListener {
            listener.onClick(view)
        }
    }

    @JvmStatic
    @BindingAdapter("onSafeDebounceClick")
    fun onSafeDebounceClick(view: View, listener: View.OnClickListener) {
        view.setOnClickListener {
            listener.onClick(view)
        }
    }
}
