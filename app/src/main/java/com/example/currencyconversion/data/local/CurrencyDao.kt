package com.example.currencyconversion.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Currency

@Dao
interface CurrencyDao: BaseDao<Currency> {

    @Query("SELECT * FROM Currency")
    fun getAll(): LiveData<List<Currency>>

    @Query("SELECT * FROM Currency WHERE currencyCode = :code")
    fun get(code: String): Currency

}