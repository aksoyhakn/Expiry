package com.afgdevlab.expirydate.data.persistence

import androidx.room.*
import com.afgdevlab.expirydate.data.persistence.entity.Data
import com.afgdevlab.expirydate.data.persistence.entity.NotificationChannelID

/**
 * Created by hakanaksoy on 11.08.2021.
 * Loodos
 */


@Dao
interface IyiyasaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertData(data: Data)

    @Update
    fun updateData(data: Data)

    @Delete
    fun deleteData(data: Data)

    @Query("DELETE FROM Data")
    fun deleteAllData()

    @Query("SELECT * FROM Data WHERE data.ID == :id")
    fun getDataByID(id: String): List<Data>

    @Query("SELECT * FROM Data")
    fun getData(): List<Data>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertNotificationChannelID(notificationChannelID: NotificationChannelID)

    @Delete
    fun deleteNotificationChannelID(notificationChannelID: NotificationChannelID)

    @Query("SELECT * FROM NotificationChannelID WHERE notificationchannelid.productName == :name")
    fun getNotificationChannelID(name: String): NotificationChannelID
}