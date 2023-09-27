package com.example.currencyconversion.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Rates
import kotlinx.coroutines.flow.Flow

@Dao
interface RatesDao: BaseDao<Rates> {

    @Query("SELECT * FROM rates")
    fun getAll(): LiveData<List<Rates>>

    @Query("SELECT * FROM rates WHERE currencyCode = :code")
    fun get(code: String): Rates

}