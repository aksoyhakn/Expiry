package com.iyiyasa.android.extensions

import com.iyiyasa.android.R
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager

internal fun FragmentManager.removeFragment(
    tag: String,
    slideIn: Int = R.anim.slide_left,
    slideOut: Int = R.anim.slide_right
) {
    findFragmentByTag(tag)?.let {
        this.beginTransaction()
            .disallowAddToBackStack()
            .setCustomAnimations(slideIn, slideOut)
            .remove(it)
            .commitNow()
    }
}

internal fun FragmentManager.removeFragment(
    slideIn: Int = R.anim.slide_left,
    slideOut: Int = R.anim.slide_right
) {
    this.beginTransaction()
        .disallowAddToBackStack()
        .setCustomAnimations(slideIn, slideOut)
        .commitNow()
}

internal fun FragmentManager.addFragment(
    containerViewId: Int,
    fragment: Fragment,
    tag: String,
    slideIn: Int = R.anim.slide_left,
    slideOut: Int = R.anim.slide_right
) {
    this.beginTransaction().disallowAddToBackStack()
        .setCustomAnimations(slideIn, slideOut)
        .add(containerViewId, fragment, tag)
        .commit()
}

