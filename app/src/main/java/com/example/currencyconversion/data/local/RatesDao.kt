package com.example.currencyconversion.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Rate
import com.example.currencyconversion.data.model.ExchangeData
import kotlinx.coroutines.flow.Flow

/**
 * Define the access method on Rates Table
 */
@Dao
interface RateDao: BaseDao<Rate> {

    // get all rates list from rates table
    @Query("SELECT * FROM rates")
    fun getAll(): Flow<List<Rate>>

    // get only one information from the rate table
    @Query("SELECT * FROM rates WHERE currencyCode = :code")
    fun get(code: String): Rate

    @Query("SELECT * FROM rates INNER JOIN currency ON currency.currencyCode = rates.currencyCode")
    fun getExchangeData(): Flow<List<ExchangeData>>

}