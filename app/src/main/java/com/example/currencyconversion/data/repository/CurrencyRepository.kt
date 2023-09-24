package com.example.currencyconversion.data.repository

import com.example.currencyconversion.data.local.CurrencyDao
import com.example.currencyconversion.data.remote.datasource.CurrencyRemoteDatasource
import javax.inject.Inject

class CurrencyRepository @Inject constructor(
    private val localDataSource: CurrencyDao,
    private val remoteDataSource: CurrencyRemoteDatasource
) {

    suspend fun getCurrencyList() = remoteDataSource.getCurrencyList()

}