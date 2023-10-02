package com.example.currencyconversion.data.model

/**
 * To display the currency code, currency name & exchange amount
 */
data class ExchangeResult(
    val currencyCode: String,
    val currencyName: String,
    val currencyRate: String
)


data class ExchangeData(
    val currencyCode: String,
    val currencyName: String,
    val currencyRate: Float
)
