package com.example.currencyconversion.util

import com.example.currencyconversion.ui.home.HomeLogicMethod
import org.junit.Test
import org.junit.Assert.*

class ExchangeCalculationUnitTest {

    /**
     * Currency Conversion unit test.
     * Convert 100BDT to BDT, Result should be 100BDT.
     */
    @Test
    fun currencyConversion_100BDT_to_100BDT() {
        // Arrange
        val inputtedAmount = 100  // user inputted value
        val sourceToUsdRate = 110.48978 // 1 USD = 110.48978 BDT
        val targetToUsdRate = 110.48978 // 1 USD = 110.48978 BDT

        // Act
        val result = HomeLogicMethod.convertCurrency(inputtedAmount.toDouble(), sourceToUsdRate, targetToUsdRate)

        // Assert
        assertEquals(result.toFloat(), inputtedAmount.toFloat())
    }

    /**
     * Currency Conversion unit test.
     * Exchange 100USD to other currency where 1USD equals following mock rate.
     * The expected exchange should be return the following expectedResult list
     */
    @Test
    fun currencyConversion_100usd_to_other() {
        // Arrange
        val inputtedAmount = 100 // user inputted value = 100 USD
        val targetToUsdRates = listOf(3.67304, 78.127805, 100.78873, 396.684171, 1.806168, 830.0f, 349.842994) // Mock rates based on 1USD(rates per 1 USD)
        val sourceToUsdRates = 1 // 1 USD
        val expectedResult = listOf(367.304, 7812.7805, 10078.873, 39668.4171, 180.6168, 83000, 34984.2994) // expected Exchange value based on mock rates
        val results: MutableList<Float> = mutableListOf()

        // Act
        targetToUsdRates.forEach { targetToUsdRate ->
            results.add(HomeLogicMethod.convertCurrency(inputtedAmount.toDouble(), sourceToUsdRates.toDouble(), targetToUsdRate.toDouble()).toFloat())
        }

        // Assert
        results.forEachIndexed { index, result ->
            assertEquals(expectedResult[index].toFloat(), result)
        }
    }
}