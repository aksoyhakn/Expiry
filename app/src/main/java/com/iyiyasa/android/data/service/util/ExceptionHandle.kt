package com.iyiyasa.android.data.service.util

import android.graphics.drawable.Drawable
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

class ExceptionHandle {

    companion object {

        enum class ERROR {
            CONNECT_ERROR,
            SOCKET_TIMEOUT,
            GENERAL_ERROR
        }

        var error: ERROR? = ERROR.GENERAL_ERROR

        fun handleException(e: Throwable?): ERROR? {
            when (e) {

                is SocketTimeoutException -> {
                    error = ERROR.SOCKET_TIMEOUT
                }

                is ConnectException -> {
                    error = ERROR.CONNECT_ERROR
                }

                is UnknownHostException -> {
                    error = ERROR.CONNECT_ERROR
                }
            }
            return error
        }
    }
}

data class ErrorModel(
    var icon: Drawable,
    var title: String?,
    var desription: String?,
    var buttonName: String? = null
)
