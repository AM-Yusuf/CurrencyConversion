package com.example.currencyconversion.data.remote.endpoint

import com.example.currencyconversion.data.model.CurrencyConversionRate
import com.example.currencyconversion.data.model.CurrencyNameList
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Exchange rate api Endpoint interface
 */
interface CurrencyApiEndpointInterface {

    /**
     * Currency name list end-point
     */
    @GET("currencies.json")
    suspend fun getCurrencyNameList(@Query("app_id") appId: String): Response<CurrencyNameList>


    /**
     * Latest Currency rate end-point
     */
    @GET("latest.json")
    suspend fun getCurrencyRate(@Query("app_id") appId: String): Response<CurrencyConversionRate>
}