package com.example.currencyconversion.util

import com.example.currencyconversion.ui.home.HomeLogicMethod
import org.junit.Assert.assertEquals
import org.junit.Test

class TimeDifferenceMethodUnitTest {

    @Test
    fun didNotPassed30MinutesTest() {

        // Arrange
        val currentTimeMillis = System.currentTimeMillis()
        val thirtyMinutesBeforeFromNow = currentTimeMillis - (30*60*1000)

        // Act
        val didPassThirtyMinutes = HomeLogicMethod.isTimestampOlderThan30Minutes(thirtyMinutesBeforeFromNow, currentTimeMillis)

        // Assert
        assertEquals(false, didPassThirtyMinutes)
    }

    @Test
    fun passed30MinutesTest() {

        // Arrange
        val currentTimeMillis = System.currentTimeMillis()
        val thirty1MinutesBeforeFromNow = currentTimeMillis - (31*60*1000)

        // Act
        val didPassThirtyMinutes = HomeLogicMethod.isTimestampOlderThan30Minutes(thirty1MinutesBeforeFromNow, currentTimeMillis)

        // Assert
        assertEquals(true, didPassThirtyMinutes)
    }
}