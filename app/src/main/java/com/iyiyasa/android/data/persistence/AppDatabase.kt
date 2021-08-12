package com.iyiyasa.android.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iyiyasa.android.data.persistence.entity.Data

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@Database(entities = [Data::class], version = 1, exportSchema = true)
abstract class AppDatabase : RoomDatabase() {
    abstract fun iyiyasaDAO(): IyiyasaDao

    companion object {
        private var instance: AppDatabase? = null
        fun getDatabase(context: Context): AppDatabase? {

            if (instance == null) {
                instance = Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "expiry.db"
                ).allowMainThreadQueries()
                    .build()
            }
            return instance
        }
    }
}