package com.example.currencyconversion.util

import org.junit.Test
import org.junit.Assert.*

class LogicUnitTest {

    /**
     * Currency Conversion unit test.
     * Convert 100BDT to BDT, Result should be 100BDT.
     */
    @Test
    fun currencyConversion_100BDT_to_100BDT() {
        // Arrange
        val inputtedAmount = 100f  // user inputted value
        val currencyRate = 110.48978f // 1 USD = 110.48978 BDT
        val currencyValuePerUSD = 110.48978f // 1 USD = 110.48978 BDT

        // Act
        val result = LogicUnit.currencyConversion(inputtedAmount, currencyRate, currencyValuePerUSD)

        // Assert
        assertEquals(result, inputtedAmount)

    }

    /**
     * Currency Conversion unit test.
     * Exchange 100USD to other currency where 1USD equals following mock rate.
     * The expected exchange should be return the following expectedResult list
     */
    @Test
    fun currencyConversion_100usd_to_other() {
        // Arrange
        val inputtedAmount = 100f // user inputted value = 100 USD
        val currencyRatesPerUSD = listOf(3.67304, 78.127805, 100.78873, 396.684171, 1.806168, 830.0f, 349.842994) // rates per 1 USD
        val selectedCurrencyRatePerUSD = 1f // 1 USD
        val expectedResult = listOf(367.304, 7812.7805, 10078.873, 39668.4171, 180.6168, 83000, 34984.2994) // expected Exchange value
        val results: MutableList<Float> = mutableListOf()

        // Act
        currencyRatesPerUSD.forEach { currencyRatePerUSD ->
            results.add(LogicUnit.currencyConversion(inputtedAmount, currencyRatePerUSD.toFloat(), selectedCurrencyRatePerUSD))
        }

        // Assert
        results.forEachIndexed { index, result ->
            assertEquals(result.toLong(), expectedResult[index].toLong())
        }

    }
}