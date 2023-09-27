package com.example.currencyconversion.data.repository

import android.util.Log
import com.example.currencyconversion.data.entities.Rate
import com.example.currencyconversion.data.entities.RateX
import com.example.currencyconversion.data.local.RateDao
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.remote.datasource.CurrencyRemoteDatasource
import com.example.currencyconversion.util.performGetOperationTest
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.full.memberProperties

class CurrencyRepository @Inject constructor(
    private val remoteDataSource: CurrencyRemoteDatasource,
    private val localDataSource: RateDao
) {


    /**
     * Make api call and save the response to local database
     */
    suspend fun getCurrencyRates(): Flow<List<Rate>> {

        val apiResponse = remoteDataSource.getCurrencyRate()

        val result: Flow<List<Rate>> = localDataSource.getAll()

        when(apiResponse.status) {
            Resource.Status.SUCCESS -> {
                Log.d("API_DEBUG", "Success1")
                val conversionRate = apiResponse.data
                conversionRate?.rates?.let {
                    val rateList = RateX::class.memberProperties.map { prop ->
                        prop.name to prop.get(it).toString().toFloat()
                    }

                    rateList.forEach { (currencyName, rate) ->
                        localDataSource.insert( Rate(currencyName, rate))
                    }
                }
                localDataSource.getAll()
            }
            Resource.Status.ERROR -> {
                // Error handling
            }
            Resource.Status.LOADING -> {
                // data loading state
            }
            Resource.Status.COMPLETED -> {
                // complete loading
            }
        }

        return result
    }


    suspend fun getAllTest() = performGetOperationTest(
        databaseQuery = { localDataSource.getAll() },
        networkCall = { remoteDataSource.getCurrencyRate() },
        saveCallResult = {


            val rateList = RateX::class.memberProperties.map { prop ->
                prop.name to prop.get(it.rates).toString().toFloat()
            }

            rateList.forEach { (currencyName, rate) ->
                localDataSource.insert( Rate(currencyName, rate))
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