package com.example.currencyconversion.data.local

import androidx.room.Dao
import androidx.room.Query
import com.example.currencyconversion.data.entities.Rate
import kotlinx.coroutines.flow.Flow

@Dao
interface RateDao: BaseDao<Rate> {

    @Query("SELECT * FROM rates")
    fun getAll(): Flow<List<Rate>>

    @Query("SELECT * FROM rates WHERE currencyCode = :code")
    fun get(code: String): Rate

}