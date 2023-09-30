package com.example.currencyconversion.util

object LogicUnit {

    /**
     * Currency exchange function
     * @param inputtedAmount
     * @param currencyRate
     * @param currencyValuePerUSD
     * @return exchange rate
     */
    fun currencyConversion(inputtedAmount: Float, currencyRate: Float, currencyValuePerUSD: Float): Float {
       return ( currencyRate / currencyValuePerUSD) * inputtedAmount
    }

    /**
     * check if a timestamp is more than 30 minutes old,
     * @param timestamp
     * @return Boolean true or false
     */
    fun isTimestampOlderThan30Minutes(timestamp: Long): Boolean {
        val currentTimeMillis = System.currentTimeMillis()
        val thirtyMinutesInMillis = 30 * 60 * 1000 // 30 minutes in milliseconds

        // Calculate the difference between the current time and the timestamp
        val timeDifference = currentTimeMillis - timestamp

        // Check if the time difference is greater than 30 minutes
        return timeDifference > thirtyMinutesInMillis
    }

}