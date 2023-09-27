package com.example.currencyconversion.data.remote.endpoint

import com.example.currencyconversion.data.entities.CurrencyConversionRate
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface CurrencyApiEndpointInterface {

    /**
     * Currency name list end point
     */
    @GET("currencies.json?app_id=0cc886cd32264016ae6c5aefee9b8403")
    suspend fun getCurrencyNameList(): Response<Any>

    /**
     * Latest Currency rate end point
     */
    @GET("latest.json")
    suspend fun getCurrencyRate(@Query("app_id") appId: String): Response<CurrencyConversionRate>
}