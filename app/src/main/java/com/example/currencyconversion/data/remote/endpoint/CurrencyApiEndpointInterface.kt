package com.example.currencyconversion.data.remote.endpoint

import com.example.currencyconversion.data.entities.CurrencyConversionRate
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.GET


interface CurrencyApiEndpointInterface {

    @GET("latest.json?app_id=0cc886cd32264016ae6c5aefee9b8403")
    suspend fun getCurrencyList(): Response<CurrencyConversionRate>
}