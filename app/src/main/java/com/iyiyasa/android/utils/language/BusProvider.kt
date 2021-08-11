package com.iyiyasa.android.utils.language

import android.os.Handler
import android.os.Looper
import com.squareup.otto.Bus
import com.squareup.otto.ThreadEnforcer


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class BusProvider {

    companion object {
        private var bus: LdsBus? = null

        fun getInstance(): LdsBus {
            if (bus == null) {
                bus = BusProvider().LdsBus()
            }
            return bus!!
        }
    }

    inner class LdsBus : Bus(ThreadEnforcer.ANY) {

        fun postDelayed(event: Any, delayMillis: Long) {
            Handler(Looper.getMainLooper()).postDelayed({ post(event) }, delayMillis)
        }
    }
}