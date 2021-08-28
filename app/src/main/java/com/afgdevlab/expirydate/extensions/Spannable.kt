package com.afgdevlab.expirydate.extensions

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.TextPaint
import android.text.style.ForegroundColorSpan
import android.text.style.MetricAffectingSpan
import android.text.style.RelativeSizeSpan
import android.text.style.TypefaceSpan
import androidx.core.content.res.ResourcesCompat

fun SpannableString.setTextSize(
    size: Float,
    start: Int = 0,
    end: Int = this.length
) {
    this.setSpan(
        RelativeSizeSpan(size),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun SpannableString.setTextColor(
    cxt: Context,
    colorId: Int,
    start: Int = 0,
    end: Int = this.length
) {
    this.setSpan(
        ForegroundColorSpan(cxt.resColor(colorId)),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
    )
}

fun SpannableString.setFontFamily(
    cxt: Context,
    fontId: Int,
    start: Int = 0,
    end: Int = this.length
) {
    this.setSpan(
        TypefaceSpan(ResourcesCompat.getFont(cxt, fontId)),
        start,
        end,
        Spannable.SPAN_EXCLUSIVE_INCLUSIVE
    )
}


class TypefaceSpan(private val typeface: Typeface?) : MetricAffectingSpan() {
    override fun updateDrawState(paint: TextPaint) {
        paint.typeface = typeface
    }

    override fun updateMeasureState(paint: TextPaint) {
        paint.typeface = typeface
    }
}