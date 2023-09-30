package com.example.currencyconversion.data.repository

import android.content.Context
import android.util.Log
import com.example.currencyconversion.data.entities.Rate
import com.example.currencyconversion.data.entities.RateX
import com.example.currencyconversion.data.local.RateDao
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.remote.datasource.CurrencyRemoteDatasource
import com.example.currencyconversion.util.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import kotlin.reflect.full.memberProperties


class CurrencyRepository @Inject constructor(
    private val remoteDataSource: CurrencyRemoteDatasource,
    private val localDataSource: RateDao,
    @ApplicationContext private val context: Context
) {

    /**
     * Make api call and save the response to local database
     */
    suspend fun getCurrencyRates(): Flow<List<Rate>> {

        val result: Flow<List<Rate>> = localDataSource.getAll()

        if (context.isNetworkConnected()) {
            val apiResponse = remoteDataSource.getCurrencyRate()

            when(apiResponse.status) {
                Resource.Status.SUCCESS -> {
                    Log.d("API_DEBUG", "Success")
                    val conversionRate = apiResponse.data
                    conversionRate?.rates?.let {
                        val rateList = RateX::class.memberProperties.map { prop ->
                            prop.name to prop.get(it).toString().toFloat()
                        }

                        rateList.forEach { (currencyName, rate) ->
                            localDataSource.insert( Rate(currencyName, rate))
                        }
                    }
                }
                Resource.Status.ERROR -> {
                    Log.d("API_DEBUG", "Error")
                    // Error handling
                }
                Resource.Status.LOADING -> {
                    Log.d("API_DEBUG", "Loading")
                    // data loading state
                }
                Resource.Status.COMPLETED -> {
                    Log.d("API_DEBUG", "completed")
                    // complete loading
                }
            }

        } else {
            Log.d("API_DEBUG", "No network connection")
            // Handling No network connection
        }

        return result
    }

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