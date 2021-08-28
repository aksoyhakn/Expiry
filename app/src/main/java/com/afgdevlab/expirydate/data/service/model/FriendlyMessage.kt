package com.afgdevlab.expirydate.data.service.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Parcelize
data class FriendlyMessage(
    @SerializedName("title") val title: String?,
    @SerializedName("message") val message: String?,
    @SerializedName("cancelable") var cancelable: Boolean? = true,
    @SerializedName("buttonNegative") var buttonNegative: String?,
    @SerializedName("buttonPositive") var buttonPositive: String?,
    @SerializedName("buttonNeutral") var buttonNeutral: String?
) : Parcelable