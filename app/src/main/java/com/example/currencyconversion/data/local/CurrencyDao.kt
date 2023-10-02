package com.example.currencyconversion.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Currency
import kotlinx.coroutines.flow.Flow

/**
 * Define the access method on Currency Table
 */
@Dao
interface CurrencyDao: BaseDao<Currency> {

    // Get all data from Currency table
    @Query("SELECT * FROM Currency")
    fun getAll(): Flow<List<Currency>>

    // get a specific information from the currency table
    @Query("SELECT * FROM Currency WHERE currencyCode = :code")
    fun get(code: String): Currency

}