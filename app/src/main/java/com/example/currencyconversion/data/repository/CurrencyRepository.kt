package com.example.currencyconversion.data.repository

import android.content.Context
import android.util.Log
import com.example.currencyconversion.data.entities.Currency
import com.example.currencyconversion.data.entities.Rate
import com.example.currencyconversion.data.local.CurrencyDao
import com.example.currencyconversion.data.local.PrefDataStore
import com.example.currencyconversion.data.local.RateDao
import com.example.currencyconversion.data.model.CurrencyNameList
import com.example.currencyconversion.data.model.ExchangeData
import com.example.currencyconversion.data.model.RateX
import com.example.currencyconversion.data.remote.Resource
import com.example.currencyconversion.data.remote.datasource.CurrencyRemoteDatasource
import com.example.currencyconversion.ui.home.HomeLogicMethod.isTimestampOlderThan30Minutes
import com.example.currencyconversion.util.isNetworkConnected
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.reflect.full.memberProperties


class CurrencyRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val remoteDataSource: CurrencyRemoteDatasource,
    private val rateLocalDataSource: RateDao,
    private val currencyLocalDataSource: CurrencyDao,
    private val prefDataStore: PrefDataStore
) {

    // get last api call time stamp
    private val lastApiCallTime = runBlocking { prefDataStore.getLastApiCallTime.first() ?: 0 }

    /**
     * Make api call and save the response to local database
     * @return list of rates as a flow
     */
    suspend fun getCurrencyRates(): Flow<List<ExchangeData>> {

        // Fetch data from local database
        val result: Flow<List<ExchangeData>> = rateLocalDataSource.getExchangeData()

        // if previous api call timeStamp greater then 30 minute and network is connected
        if (context.isNetworkConnected() && isTimestampOlderThan30Minutes(lastApiCallTime, System.currentTimeMillis())) {

            // hit remote source to get currency name list
            getCurrencyNameList()

            // hit remote source to get te latest currency rate
            val apiResponse = remoteDataSource.getCurrencyRate()

            // check response status
            when(apiResponse.status) {
                Resource.Status.SUCCESS -> {
                    Log.d("API_DEBUG", "Success")

                    // get the api response result data
                    val conversionRate = apiResponse.data

                    conversionRate?.rates?.let {

                        // store the api call time stamp to prefDataStore
                        withContext(Dispatchers.IO) {
                            prefDataStore.setLastApiCallTime(System.currentTimeMillis())
                        }

                        // Convert CurrencyRates object to Pair<String, Float>
                        val rateList = RateX::class.memberProperties.map { prop ->
                            prop.name to prop.get(it).toString().toFloat()
                        }

                        // store in local data base
                        rateList.forEach { (currencyName, rate) ->
                            rateLocalDataSource.insert( Rate(currencyName, rate))
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
            // Handling No network connection
            Log.d("API_DEBUG", "No network connection or api cal too early")
        }
        return result
    }



    /**
     * get currency name list
     */
    private suspend fun getCurrencyNameList() {

        // hit remote source to get te latest currency rate
        val apiResponse = remoteDataSource.getCurrencyNameList()

        // check response status
        when(apiResponse.status) {
            Resource.Status.SUCCESS -> {
                Log.d("API_DEBUG", "Success")

                // get the api response result data
                val conversionRate = apiResponse.data

                conversionRate?.let {

                    // Convert CurrencyRates object to Pair<String, Float>
                    val nameList = CurrencyNameList::class.memberProperties.map { prop ->
                        prop.name to prop.get(it).toString()
                    }

                    // store in local data base
                    nameList.forEach { (currencyCode, currencyName) ->
                        currencyLocalDataSource.insert( Currency(currencyCode, currencyName))
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
    }
}