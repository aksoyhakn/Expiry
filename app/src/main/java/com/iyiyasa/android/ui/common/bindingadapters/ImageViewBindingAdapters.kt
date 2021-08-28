package com.iyiyasa.android.ui.common.bindingadapters

import android.graphics.PorterDuff
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.iyiyasa.android.R
import com.iyiyasa.android.extensions.*


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


object ImageViewBindingAdapters {

    @JvmStatic
    @BindingAdapter("bind:setImage")
    fun setImage(view: ImageView, url: String?) {
        url?.let {
            view.loadImage(view.context, url, null)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:setImageCricleCropCache")
    fun setImageCricleCropCache(view: ImageView, url: String?) {
        view.loadGlideImageCircleCropCache(view.context, url!!, null)
    }

    @JvmStatic
    @BindingAdapter("bind:setPublicImageCircleCropCache")
    fun setPublicImageCircleCropCache(view: ImageView, url: String?) {
        view.loadGlideImageCircleCropCache(view.context, url!!, null)
    }

    @JvmStatic
    @BindingAdapter("bind:setImageCache")
    fun setImageCache(view: ImageView, url: String?) {
        if (!url!!.contains(".gif")) {
            view.loadGlideImageCache(
                view.context,
                url,
                null
            )
        } else {
            view.loadGIF(view.context, url)
        }
    }

    @JvmStatic
    @BindingAdapter("bind:setImageColorFilter")
    fun setImageColorFilter(view: ImageView, isFilter: Boolean) {
        if (isFilter) {
            view.setColorFilter(
                view.context.resColor(R.color.colorPrimary),
                PorterDuff.Mode.SRC_IN
            )
        } else {
            view.setColorFilter(view.context.resColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
    }


    @JvmStatic
    @BindingAdapter("bind:lastDateControl")
    fun lastDateControl(view: ImageView, countDate: String) {
        countDate.notNull {
            when(it){
                "1"-> view.setBackgroundResource(R.drawable.ic_date4)
                "2"-> view.setBackgroundResource(R.drawable.ic_date3)
                "3"-> view.setBackgroundResource(R.drawable.ic_date2)
                else-> view.setBackgroundResource(R.drawable.ic_date1)
            }
        }
    }
}
