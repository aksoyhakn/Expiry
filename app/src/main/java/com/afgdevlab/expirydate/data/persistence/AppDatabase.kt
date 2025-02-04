package com.afgdevlab.expirydate.data.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.data.persistence.entity.NotificationChannelID

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */

@Database(entities = [Data::class, NotificationChannelID::class], version = 1, exportSchema = true)
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