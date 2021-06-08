package com.tilikki.training.unimager.demo.util

import org.junit.Assert
import org.junit.Test
import kotlin.math.absoluteValue

class DataExtensionsKtTest {
    private val numberValue = 2
    private val signedNumberValue = -2
    private val nullNumber: Int? = null
    
    @Test
    fun intNullableToString_notNull_returnNumber() {
        Assert.assertEquals("$numberValue", numberValue.toString())
    }

    @Test
    fun intNullableToString_null_returnNotAvailableSign() {
        Assert.assertEquals("n/a", nullNumber.toString())
    }

    @Test
    fun value_ifNotNull_returnValue() {
        Assert.assertEquals(numberValue, numberValue.value())
    }

    @Test
    fun value_ifNull_returnZero() {
        Assert.assertEquals(0, nullNumber.value())
    }

    @Test
    fun unsignedValue_ifAnyNonSignedValue_returnValue() {
        Assert.assertEquals(numberValue, numberValue.unsignedValue())
    }

    @Test
    fun unsignedValue_ifSignedValue_returnUnsignedValue() {
        Assert.assertEquals(signedNumberValue.absoluteValue, signedNumberValue.unsignedValue())
    }

    @Test
    fun unsignedValue_ifNull_returnSignedValue() {
        Assert.assertEquals(-1, nullNumber.unsignedValue())
    }
}