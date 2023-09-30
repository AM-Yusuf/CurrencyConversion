package com.example.currencyconversion.util

object LogicUnit {

    fun currencyConversion(inputtedAmount: Float, currencyRate: Float, currencyValuePerUSD: Float): Float {
       return ( currencyRate / currencyValuePerUSD) * inputtedAmount
    }
}