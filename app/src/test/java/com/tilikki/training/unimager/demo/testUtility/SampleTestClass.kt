package com.tilikki.training.unimager.demo.testUtility

import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

abstract class MyUnitTest

@RunWith(JUnit4::class)
class DemoJUnit4UnitTest : MyUnitTest() {
    @Test
    fun simpleTest() {
        val words = listOf("lorem", "ipsum")
        Assert.assertEquals(2, words.size)
    }
}

class DemoNoRunWithUnitTest : MyUnitTest()