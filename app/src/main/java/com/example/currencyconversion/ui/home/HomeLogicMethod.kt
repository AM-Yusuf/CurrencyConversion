package com.example.currencyconversion.ui.home

object HomeLogicMethod {

    /**
     * check if a timestamp is more than 30 minutes old,
     * @param timestamp
     * @return Boolean true or false
     */
    fun isTimestampOlderThan30Minutes(timestamp: Long, currentTimeMillis: Long): Boolean {

        val thirtyMinutesInMillis = 30 * 60 * 1000 // 30 minutes in milliseconds

        // Calculate the difference between the current time and the timestamp
        val timeDifference = currentTimeMillis - timestamp

        // Check if the time difference is greater than 30 minutes
        return timeDifference > thirtyMinutesInMillis
    }


    /**
     * Currency exchange function
     * @param amount
     * @param sourceToUsdRate
     * @param targetToUsdRate
     * @return exchange result
     */
    fun convertCurrency(
        amount: Double,  // Amount in the source currency
        sourceToUsdRate: Double,  // Exchange rate from source currency to USD
        targetToUsdRate: Double  // Exchange rate from target currency to USD
    ): String {
        // Convert the amount to USD
        val amountInUsd = amount / sourceToUsdRate

        // Convert USD to the target currency
        val amountInTargetCurrency = amountInUsd * targetToUsdRate

        return amountInTargetCurrency.toString()
    }

}