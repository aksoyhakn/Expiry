package com.iyiyasa.android.extensions

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


fun <T> LiveData<T>.observeNonNull(owner: LifecycleOwner, observer: (t: T) -> Unit) {
    this.observe(owner, Observer {
        it?.let(observer)
    })
}

fun <T> MutableLiveData<T>.mutation(actions: (MutableLiveData<T>) -> Unit) {
    actions(this)
    this.value.also { this.value = it }
}

@Suppress("detekt.UnsafeCast")
fun <T> MutableLiveData<T>.toLiveData() = this as LiveData<T>

fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }