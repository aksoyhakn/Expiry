package com.afgdevlab.expirydate.extensions

import android.animation.AnimatorSet
import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.drawable.GradientDrawable
import android.os.Handler
import android.os.Looper
import android.os.SystemClock
import android.util.DisplayMetrics
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.view.WindowManager
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.AlphaAnimation
import android.view.animation.AnimationUtils
import android.view.animation.TranslateAnimation
import com.afgdevlab.expirydate.R
import com.jakewharton.rxbinding2.view.RxView
import java.util.concurrent.TimeUnit


fun View.setOnDebouncedClickListener(action: () -> Unit) {
    val actionDebouncer = ActionDebouncer(action)
    // This is the only place in the project where we should actually use setOnClickListener
    setOnClickListener {
        actionDebouncer.notifyAction()
    }
}


fun View.setSafeOnClickListener(onSafeClick: (View) -> Unit) {
    val safeClickListener = SafeClickListener {
        onSafeClick(it)
    }
    setOnClickListener(safeClickListener)
}

fun Context.getStatusBarHeight(): Int {
    var result = 0
    val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
        result = resources.getDimensionPixelSize(resourceId);
    }
    return result
}

fun View.underline() {
    Paint.UNDERLINE_TEXT_FLAG
}

fun View.changeHeight(height: Int) {
    if (layoutParams != null) {
        layoutParams.height = height
    }
}

fun View.changeWidth(width: Int) {
    if (layoutParams != null) {
        layoutParams.width = width
    }
}

private class SafeClickListener(
    private var defaultInterval: Int = 1000,
    private val onSafeCLick: (View) -> Unit
) : View.OnClickListener {
    private var lastTimeClicked: Long = 0
    override fun onClick(v: View) {
        if (SystemClock.elapsedRealtime() - lastTimeClicked < defaultInterval) {
            return
        }
        lastTimeClicked = SystemClock.elapsedRealtime()
        onSafeCLick(v)
    }
}

fun View.expandOrCollapse(currentHeight: Int, newHeight: Int){
    var slideAnimator = ValueAnimator
        .ofInt(currentHeight, newHeight)
        .setDuration(200)

    slideAnimator.addUpdateListener {animation1->
        var value = animation1.animatedValue;
        this.layoutParams.height = value as Int
        this.requestLayout()
    }

    var animationSet =  AnimatorSet();
    animationSet.interpolator = AccelerateDecelerateInterpolator()
    animationSet.play(slideAnimator);
    animationSet.start()
}

fun View.slideUp() {
    Handler(Looper.getMainLooper()).postDelayed({
        this.visibility=View.VISIBLE
        val animate = TranslateAnimation(
            0F,
            0F,
            this.height.toFloat(),
            0F
        )

        animate.duration = 500
        animate.fillAfter = true
        this.startAnimation(animate)
    },200)
}

fun View.slideDown() {
    this.visibility=View.GONE
    val animate = TranslateAnimation(
        0F,
        0F,
        0F,
        this.height.toFloat() + 100
    ) // toYDelta

    animate.duration = 500
    animate.fillAfter = true
    this.startAnimation(animate)
}

fun View.fadeIn(duration: Long){
    this.clearAnimation()
    val anim = AlphaAnimation(this.alpha, 1.0f)
    anim.duration = duration
    this.startAnimation(anim)
    this.visibility=View.VISIBLE
}

fun View.fadeOut(duration: Long){
    this.clearAnimation()
    val anim = AlphaAnimation(this.alpha, 0.0f)
    anim.duration = duration
    this.startAnimation(anim)
    this.visibility=View.GONE
}

fun View.removeOnDebouncedClickListener() {
    setOnClickListener(null)
    isClickable = false
}

fun View.setRXSafeOnClickListener(onClick: (View) -> Unit) {
    RxView.clicks(this).throttleFirst(200, TimeUnit.MILLISECONDS).subscribe {
        zoomInAndOut {
            onClick(this)
        }
    }
}

private class ActionDebouncer(private val action: () -> Unit) {

    companion object {
        const val DEBOUNCE_INTERVAL_MILLISECONDS = 600L
    }

    private var lastActionTime = 0L

    fun notifyAction() {
        val now = SystemClock.elapsedRealtime()

        val millisecondsPassed = now - lastActionTime
        val actionAllowed = millisecondsPassed > DEBOUNCE_INTERVAL_MILLISECONDS
        lastActionTime = now

        if (actionAllowed) {
            action.invoke()
        }
    }
}

fun View.setMargins(
    leftMarginDp: Int? = null,
    topMarginDp: Int? = null,
    rightMarginDp: Int? = null,
    bottomMarginDp: Int? = null
) {
    if (layoutParams is ViewGroup.MarginLayoutParams) {
        val params = layoutParams as ViewGroup.MarginLayoutParams
        leftMarginDp?.run { params.leftMargin = this }
        topMarginDp?.run { params.topMargin = this }
        rightMarginDp?.run { params.rightMargin = this }
        bottomMarginDp?.run { params.bottomMargin = this }
        requestLayout()
    }
}

fun View.linearGradient(
    orientation: GradientDrawable.Orientation,
    colorList: IntArray
) {
    this.background = GradientDrawable(orientation, colorList)
}



fun View.zoomInAndOut(
    listener: () -> Unit
) {
    val animZoomIn = AnimationUtils.loadAnimation(this.context, R.anim.zoom_out)
    this.startAnimation(animZoomIn)

    this.context.handler(100) {
        val animZoomOut = AnimationUtils.loadAnimation(this.context, R.anim.zoom_in)
        this.startAnimation(animZoomOut)
    }

    this.context.handler(200) {
        listener()
    }
}
