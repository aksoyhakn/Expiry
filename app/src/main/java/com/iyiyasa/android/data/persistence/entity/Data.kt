package com.iyiyasa.android.data.persistence.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */


@Entity
data class Data(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val data: String
)