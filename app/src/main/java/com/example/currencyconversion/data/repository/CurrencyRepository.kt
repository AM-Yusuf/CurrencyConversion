package com.example.currencyconversion.data.repository

import android.util.Log
import com.example.currencyconversion.data.entities.RateX
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.remote.datasource.CurrencyRemoteDatasource
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val remoteDataSource: CurrencyRemoteDatasource
) {

    suspend fun getCurrencyNameList() = remoteDataSource.getCurrencyNameList()

    suspend fun getCurrencyRate(): RateX? {
        val response = remoteDataSource.getCurrencyRate()
        return when(response.status) {
            Resource.Status.SUCCESS -> {
                return  response.data?.rates
            }

            // do other operation depending on the response status

            else -> {
                Log.d("API","Api result fail: ${response.message}")
                null
            }
        }
    }

}