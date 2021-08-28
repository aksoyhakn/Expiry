package com.afgdevlab.expirydate.data.service.util

import com.afgdevlab.expirydate.data.service.model.BaseResponse
import retrofit2.Response


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

sealed class State<T> {
    class Loading<T>(val isLoading: Boolean) : State<T>()

    data class Success<T>(val data: Response<T>) : State<T>()

    data class Error<T>(val throwable: Throwable) : State<T>()

    data class Fail<T>(val baseResponse: BaseResponse) : State<T>()

    data class IdentityError<T>(val baseResponse: String) : State<T>()

    companion object {

        fun <T> loading(isLoading: Boolean) = Loading<T>(isLoading)

        fun <T> success(data: Response<T>) =
            Success(data)

        fun <T> error(throwable: Throwable) =
            Error<T>(throwable)

        fun <T> apiFail(baseResponse: BaseResponse) =
            Fail<T>(baseResponse)

        fun <T> apiIdentity(baseResponse: String) =
            IdentityError<T>(baseResponse)
    }
}
