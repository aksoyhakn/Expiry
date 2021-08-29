package com.afgdevlab.expirydate.ui.barcode.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import com.afgdevlab.expirydate.data.service.model.BaseResponse
import kotlinx.android.parcel.Parcelize


/**
 * Created by hakanaksoy on 12.08.2021.
 * Loodos
 */

@Parcelize
data class BarcodeResponse(
    @SerializedName("ID") val id: Int?,
    @SerializedName("dc_BarcodeId") val dc_BarcodeId: String?,
    @SerializedName("dc_ProductName") val dc_ProductName: String?,
    @SerializedName("Exception") val exception: String?
) : BaseResponse(),Parcelable
