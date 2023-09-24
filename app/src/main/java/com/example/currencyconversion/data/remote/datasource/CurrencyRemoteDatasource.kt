package com.example.currencyconversion.data.remote.datasource

import com.example.currencyconversion.data.remote.endpoint.CurrencyApiEndpointInterface
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CurrencyRemoteDatasource @Inject constructor(
    private val endpoint: CurrencyApiEndpointInterface
): BaseDataSource() {

    //suspend fun getCurrencyRate() = endpoint.getCurrencyRate()

    suspend fun getCurrencyList() = getResult { endpoint.getCurrencyList() }

}