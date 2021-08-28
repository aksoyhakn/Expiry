package com.afgdevlab.expirydate.data.service

import android.os.Parcelable
import com.google.gson.Gson
import kotlinx.android.parcel.Parcelize

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Parcelize
data class JWTTokenResponse(
    var iss: String? = "",
    var sub: String? = "",
    var aud: String? = "",
    var exp: Long? = null,
    var nbf: Int? = -1,
    var iat: Double? = null,
    var jti: String? = "",
    var email: String? = "",
    var name: String? = "",
    var surName: String? = "",
    var profileImage: String? = "",
    var birthDate: String? = "",
    var gender: String? = "",
    var user_id: String? = "",
    var culture: String = "",
    var email_confirmed: Boolean? = false,
    var profile_completed: Boolean? = false,
    var push_notification_enabled: Boolean? = false
) : Parcelable {

    companion object {

        fun initJWTToken(token: String): JWTTokenResponse {
            val split: List<String> = token.split(".")
            val base64 = split[1]
            val decode = android.util.Base64.decode(base64, android.util.Base64.DEFAULT);
            return Gson().fromJson(String(decode), JWTTokenResponse::class.java)
        }
    }
}