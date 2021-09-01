package com.tilikki.training.unimager.demo.model.util

import org.junit.Assert.assertEquals
import org.junit.Test

class ResolutionTest {

    @Test
    fun getOrientation_landscape() {
        val resolution = Resolution(width = 640, height = 480)
        assertEquals(Resolution.LANDSCAPE, resolution.getOrientation())
    }

    @Test
    fun getOrientation_portrait() {
        val resolution = Resolution(width = 480, height = 640)
        assertEquals(Resolution.PORTRAIT, resolution.getOrientation())
    }

    @Test
    fun getOrientation_square() {
        val resolution = Resolution(width = 500, height = 500)
        assertEquals(Resolution.SQUARE, resolution.getOrientation())
    }

    @Test
    fun getOrientation_squarishLandscape() {
        val resolution = Resolution(width = 880, height = 840)
        assertEquals(Resolution.SQUARE, resolution.getOrientation())
    }

    @Test
    fun getOrientation_squarishPortrait() {
        val resolution = Resolution(width = 880, height = 900)
        assertEquals(Resolution.SQUARE, resolution.getOrientation())
    }
}
