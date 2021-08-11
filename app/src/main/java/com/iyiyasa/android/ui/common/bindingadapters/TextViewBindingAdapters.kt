package com.iyiyasa.android.ui.common.bindingadapters

import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.iyiyasa.android.extensions.getToday


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

object TextViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("bind:setToday")
    fun setToday(view: TextView, isUp: Boolean) {
        if (isUp) {
            view.text = view.context.getToday()
        }
    }

}