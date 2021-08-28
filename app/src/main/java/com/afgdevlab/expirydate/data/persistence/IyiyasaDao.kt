package com.afgdevlab.expirydate.data.persistence

import androidx.room.*
import com.afgdevlab.expirydate.data.persistence.entity.Data

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
}