package com.iyiyasa.android.extensions

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.text.style.ClickableSpan
import android.text.style.URLSpan
import android.text.style.UnderlineSpan
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.core.view.doOnPreDraw
import com.iyiyasa.android.R


fun TextView.setMaxLinesForEllipsizing() = doOnPreDraw {
    val numberOfCompletelyVisibleLines = (measuredHeight - paddingTop - paddingBottom) / lineHeight
    maxLines = numberOfCompletelyVisibleLines
}

fun String.toSpanned(): Spanned {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
        return Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY)
    } else {
        @Suppress("DEPRECATION")
        return Html.fromHtml(this)
    }
}

fun TextView.customAfterTextChanged(action: (Editable?) -> Unit) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
        override fun afterTextChanged(editable: Editable?) {
            action(editable)
        }
    })
}

fun TextView.setMergeString(
    text: String,
    hereClickText: String,
    clickTextColor:Int,
    clickable: () -> Unit
) {

    val spannable = SpannableString(text)

    spannable.setTextColor(
        context,
        R.color.colorPrimary,
        start = text.indexOf(hereClickText),
        end = text.indexOf(hereClickText) + hereClickText.length
    )

    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            clickable()
        }

        override fun updateDrawState(ds: TextPaint) {
            ds.color = context.resColor(clickTextColor)
            ds.isUnderlineText = true;
        }
    }

    spannable.setSpan(
        clickableSpan,
        text.indexOf(hereClickText),
        text.indexOf(hereClickText) + hereClickText.length,
        0
    );

    this.text = spannable
    this.movementMethod = LinkMovementMethod.getInstance()
}

fun TextView.makeScrollableInsideScrollView() {
    movementMethod = ScrollingMovementMethod()
    setOnTouchListener { v, event ->
        v.parent.requestDisallowInterceptTouchEvent(true)
        when (event.action and MotionEvent.ACTION_MASK) {
            MotionEvent.ACTION_UP -> {
                v.parent.requestDisallowInterceptTouchEvent(false)
                performClick()
            }
        }
        false
    }
}

fun TextView.handleUrlClicks(onClicked: ((String) -> Unit)? = null) {
    //create span builder and replaces current text with it
    text = SpannableStringBuilder.valueOf(text).apply {
        //search for all URL spans and replace all spans with our own clickable spans
        getSpans(0, length, URLSpan::class.java).forEach {
            //add new clickable span at the same position
            setSpan(
                object : ClickableSpan() {
                    override fun onClick(widget: View) {
                        onClicked?.invoke(it.url)
                    }
                },
                getSpanStart(it),
                getSpanEnd(it),
                Spanned.SPAN_INCLUSIVE_EXCLUSIVE
            )
            //remove old URLSpan
            removeSpan(it)
        }
    }
    //make sure movement method is set
    movementMethod = LinkMovementMethod.getInstance()
}

fun SpannableString.underline(start: Int, end: Int): SpannableString {
    this.setSpan(UnderlineSpan(), start, end, 0)
    return this
}

inline fun TextView.doBeforeTextChanged(
    crossinline action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
) = addTextChangedListener(beforeTextChanged = action)

inline fun TextView.doOnTextChanged(
    crossinline action: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit
) = addTextChangedListener(onTextChanged = action)


inline fun TextView.doAfterTextChanged(
    crossinline action: (text: Editable?) -> Unit
) = addTextChangedListener(afterTextChanged = action)


inline fun TextView.addTextChangedListener(
    crossinline beforeTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline onTextChanged: (
        text: CharSequence?,
        start: Int,
        count: Int,
        after: Int
    ) -> Unit = { _, _, _, _ -> },
    crossinline afterTextChanged: (text: Editable?) -> Unit = {}
): TextWatcher {
    val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            afterTextChanged.invoke(s)
        }

        override fun beforeTextChanged(text: CharSequence?, start: Int, count: Int, after: Int) {
            beforeTextChanged.invoke(text, start, count, after)
        }

        override fun onTextChanged(text: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChanged.invoke(text, start, before, count)
        }
    }
    addTextChangedListener(textWatcher)

    return textWatcher
}





