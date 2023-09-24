package com.example.currencyconversion.data.entities

data class CurrencyConversionRate(
    val base: String = "",
    val disclaimer: String = "",
    val license: String = "",
    val rates: RateX = RateX(),
    val timestamp: Int = 0
)