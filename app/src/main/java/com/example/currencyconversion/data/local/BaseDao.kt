package com.example.currencyconversion.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Update

/**
 * An interface for common data access method
 */
@Dao
interface BaseDao<T> {

    // Insert an object to table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(vararg obj: T)

    // Insert list of data to table
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(list: List<T>)

    // Update all items in the table
    @Update
    fun updateAll(list: List<T>)

    // Update an object in the table
    @Update
    suspend fun update(vararg obj: T)

    // Delete an object from the table
    @Delete
    suspend fun delete(obj: T)
}