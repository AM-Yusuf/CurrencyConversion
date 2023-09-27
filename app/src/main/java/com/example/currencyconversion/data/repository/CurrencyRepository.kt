package com.example.currencyconversion.data.repository

import android.util.Log
import com.example.currencyconversion.data.entities.RateX
import com.example.currencyconversion.data.entities.Rates
import com.example.currencyconversion.data.local.RatesDao
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.remote.datasource.CurrencyRemoteDatasource
import com.example.currencyconversion.util.performGetOperation
import com.example.currencyconversion.util.performGetOperationTest
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

class CurrencyRepository @Inject constructor(
    private val remoteDataSource: CurrencyRemoteDatasource,
    private val localDataSource: RatesDao
) {

    suspend fun getAll() = performGetOperation(
        databaseQuery = { localDataSource.getAll() },
        networkCall = { remoteDataSource.getCurrencyRate() },
        saveCallResult = {


            val rateList = RateX::class.memberProperties.map { prop ->
                prop.name to prop.get(it.rates).toString().toFloat()
            }

            rateList.forEach { (currencyName, rate) ->
                localDataSource.insert( Rates(currencyName, rate))
            }

        }
    )


    suspend fun getAllTest() = performGetOperationTest(
        databaseQuery = { localDataSource.getAll() },
        networkCall = { remoteDataSource.getCurrencyRate() },
        saveCallResult = {


            val rateList = RateX::class.memberProperties.map { prop ->
                prop.name to prop.get(it.rates).toString().toFloat()
            }

            rateList.forEach { (currencyName, rate) ->
                localDataSource.insert( Rates(currencyName, rate))
            }

        }
    )

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