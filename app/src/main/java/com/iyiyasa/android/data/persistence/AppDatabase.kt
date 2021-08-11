package com.iyiyasa.android.data.persistence

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iyiyasa.android.data.persistence.entity.Data

/**
 * Created by hakanaksoy on 11.05.2021.
 * Loodos
 */

@Database(entities = [Data::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun iyiyasaDAO(): IyiyasaDao
}