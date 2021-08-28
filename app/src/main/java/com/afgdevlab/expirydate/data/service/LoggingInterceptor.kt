package com.afgdevlab.expirydate.data.service

import com.afgdevlab.expirydate.BuildConfig
import com.afgdevlab.expirydate.data.preference.PreferenceHelperImp
import com.orhanobut.logger.Logger
import okhttp3.Interceptor
import okhttp3.Protocol
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.io.IOException
import java.net.ProtocolException
import javax.inject.Inject
import kotlin.jvm.Throws


/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

class LoggingInterceptor @Inject constructor(var preferenceHelperImp: PreferenceHelperImp) :
    Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()


        val requestBuilder = request.newBuilder().method(request.method, request.body)
        requestBuilder.header("X-Client-Id", BuildConfig.BASE_X_CLIENT_ID)
        requestBuilder.addHeader("X-Client-Secret", BuildConfig.BASE_X_CLIENT_SECRET)

        if (!preferenceHelperImp.getAccessToken().isNullOrEmpty()) {
            requestBuilder.header("Authorization", preferenceHelperImp.getAccessToken()!!)
        }

        val newRequest = requestBuilder.build()

        val response: Response
        response = try {
            chain.proceed(newRequest)
        } catch (e: ProtocolException) {
            Response.Builder()
                .request(request)
                .code(204)
                .protocol(Protocol.HTTP_1_1)
                .build()
        }

        var rawJson: String? = null
        try {
            rawJson = response.body?.string()
            Logger.json(rawJson)
        } catch (e: Exception) {
            Logger.e("Null response body")
        }

        return response.newBuilder()
            .body(rawJson.toString().toResponseBody(response.body?.contentType())).build()
    }
}