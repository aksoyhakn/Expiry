package com.afgdevlab.expirydate.extensions

import android.content.Context
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.afgdevlab.expirydate.R

fun ImageView.loadImage(
    context: Context,
    url: String,
    placeholder: Drawable?
) {
    if (placeholder != null) {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)
            .placeholder(placeholder)
            .thumbnail(0.5f)
            .centerCrop()
            .into(this)
    } else {
        Glide.with(context)
            .load(url)
            .diskCacheStrategy(DiskCacheStrategy.NONE)
            .skipMemoryCache(false)
            .thumbnail(0.5f)
            .centerCrop()
            .into(this)
    }
}

fun ImageView.loadGIF(
    context: Context,
    url: String
) {
    Glide.with(context).load(url).into(this);
}

fun ImageView.loadGlideImageCache(
    context: Context,
    url: String,
    placeholder: Drawable?
) {

    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                this@loadGlideImageCache.setImageDrawable(placeholder)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)
}


fun ImageView.loadGlideImageCircleCropCache(
    context: Context,
    url: String,
    placeholder: Drawable?
) {

    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .circleCrop()
        .listener(object : RequestListener<Drawable> {
            override fun onLoadFailed(
                e: GlideException?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                isFirstResource: Boolean
            ): Boolean {
                this@loadGlideImageCircleCropCache.setImageDrawable(placeholder)
                return false
            }

            override fun onResourceReady(
                resource: Drawable?,
                model: Any?,
                target: com.bumptech.glide.request.target.Target<Drawable>?,
                dataSource: DataSource?,
                isFirstResource: Boolean
            ): Boolean {
                return false
            }

        })
        .into(this)
}


fun ImageView.lastDateControl(countDate: Int) {
    countDate.notNull {
        when(it){
            in 0..23-> this.setBackgroundResource(R.drawable.ic_date4)
            in 24..144-> this.setBackgroundResource(R.drawable.ic_date3)
            in 145..696-> this.setBackgroundResource(R.drawable.ic_date2)
            else-> this.setBackgroundResource(R.drawable.ic_date1)
        }
    }
}