package com.iyiyasa.android.data.service


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class Session {
    companion object {

        private var currentSession: Session? = Session()
        val current: Session
            get() {
                if (currentSession == null) {
                    currentSession = Session()
                }
                return currentSession as Session
            }
    }

    fun clearSession() {
        currentSession = null
    }
}