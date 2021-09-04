package com.afgdevlab.expirydate.data.service

import com.google.gson.annotations.SerializedName


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

    var remoteConfigOptions: RemoteConfigOptions? = null
}



data class RemoteConfigOptions(
    @SerializedName("latestVersionCode") val latestVersionCode: String?,
    @SerializedName("isForceUpdate") val isForceUpdate: Boolean?,
    @SerializedName("activityShowCount") val activityShowCount: Int? = 3,
)