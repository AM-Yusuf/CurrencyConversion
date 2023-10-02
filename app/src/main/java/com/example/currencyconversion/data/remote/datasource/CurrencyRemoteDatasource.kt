package com.example.currencyconversion.data.remote.datasource

import com.example.currencyconversion.BuildConfig
import com.example.currencyconversion.data.remote.endpoint.CurrencyApiEndpointInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRemoteDatasource @Inject constructor(
    private val endpoint: CurrencyApiEndpointInterface
): BaseDataSource() {

    /**
     * Currency rate api call
     * Fetch currency rates from remote source
     */
    suspend fun getCurrencyRate() = getResult { endpoint.getCurrencyRate(BuildConfig.APP_ID) }


    /**
     * currency name list api call
     */
    suspend fun getCurrencyNameList() = getResult { endpoint.getCurrencyNameList(BuildConfig.APP_ID) }

}