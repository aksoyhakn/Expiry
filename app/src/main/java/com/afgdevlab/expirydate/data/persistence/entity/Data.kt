package com.afgdevlab.expirydate.data.persistence.entity

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.afgdevlab.expirydate.data.service.model.BaseResponse
import kotlinx.android.parcel.Parcelize

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Entity
@Parcelize
data class Data(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val ID: Int? = null,
    @ColumnInfo(name = "productBarcodeID") val productBarcodeID: String,
    @ColumnInfo(name = "productName") val productName: String,
    @ColumnInfo(name = "productDate") val productDate: String,
    @ColumnInfo(name = "productDateControl") val productDateControl: String?,
    @ColumnInfo(name = "isOpenProductDate") var isOpenProductDate: String?=null,
    @ColumnInfo(name = "isOpenProduct") var isOpenProduct: Boolean? = false,
) : Parcelable



@Entity
@Parcelize
data class NotificationChannelID(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id") val ID: Int? = null,
    @ColumnInfo(name = "productName") val productName: String,
    @ColumnInfo(name = "notificationChannelID") val notificationChannelID: String
) : Parcelable