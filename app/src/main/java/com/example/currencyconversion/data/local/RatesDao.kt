package com.example.currencyconversion.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Rates

@Dao
interface RatesDao: BaseDao<Rates> {

    @Query("SELECT * FROM rates")
    fun getAll(): List<Rates>

    @Query("SELECT * FROM rates WHERE currencyCode = :code")
    fun get(code: String): Rates

}