package com.example.currencyconversion.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Currency

/**
 * Define the access method on Currency Table
 */
@Dao
interface CurrencyDao: BaseDao<Currency> {

    // Get all data from Currency table
    @Query("SELECT * FROM Currency")
    fun getAll(): LiveData<List<Currency>>

    // get a specific information from the currency table
    @Query("SELECT * FROM Currency WHERE currencyCode = :code")
    fun get(code: String): Currency

}