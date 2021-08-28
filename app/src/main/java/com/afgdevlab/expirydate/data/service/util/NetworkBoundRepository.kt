package com.afgdevlab.expirydate.data.service.util

import androidx.annotation.MainThread
import com.afgdevlab.expirydate.data.service.model.BaseResponse
import kotlinx.coroutines.flow.flow
import retrofit2.Response


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

abstract class NetworkBoundRepository<RESULT, REQUEST> {

    fun asFlow() = flow<State<RESULT>> {

        emit(State.loading(true))

        try {
            val apiResponse = fetchFromRemote()
            val remotePosts = apiResponse.body()

            if (apiResponse.isSuccessful && remotePosts != null) {
                if (apiResponse.body() is BaseResponse && (
                            (apiResponse.body() as BaseResponse).isBadRequest ||
                                    (apiResponse.body() as BaseResponse).isNotFound ||
                                    (apiResponse.body() as BaseResponse).isError
                            )
                ) {
                    emit(
                        State.apiFail(
                            BaseResponse(
                                (apiResponse.body() as BaseResponse).processStatus,
                                (apiResponse.body() as BaseResponse).friendlyMessage
                            )
                        )
                    )
                } else {
                    emit(State.success(apiResponse))
                }

            } else {
                if (apiResponse.raw().request.url.toString().contains("connect/token")) {
                    emit(State.apiIdentity(apiResponse.errorBody()?.string()!!))
                } else {
                    emit(State.apiFail(BaseResponse(apiResponse.message())))
                }
            }
        } catch (e: Throwable) {
            emit(State.error(e))
            e.printStackTrace()
        }
    }

    @MainThread
    protected abstract suspend fun fetchFromRemote(): Response<RESULT>
}