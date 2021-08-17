package com.iyiyasa.android.data.persistence.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iyiyasa.android.data.service.model.BaseResponse

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


@Entity
data class Data(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "ID") val ID: Int? = null,
    @ColumnInfo(name = "dc_BarcodeId") val dc_BarcodeId: String,
    @ColumnInfo(name = "dc_ProductName") val dc_ProductName: String,
    @ColumnInfo(name = "dc_ProductDate") val dc_ProductDate: String
)