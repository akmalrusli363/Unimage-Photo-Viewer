package com.tilikki.training.unimager.demo.util

import android.content.Context
import android.content.res.Resources
import android.view.View
import android.widget.TextView
import androidx.annotation.PluralsRes
import androidx.test.core.app.ApplicationProvider
import com.tilikki.training.unimager.demo.R
import org.junit.Assert
import org.junit.Test

class ViewUtilityTest {

    private val context: Context = ApplicationProvider.getApplicationContext()

    private val resources: Resources = context.resources

    @Test
    fun setVisibility_success() {
        val myView = TextView(context)
        myView.text = "Hello!"

        ViewUtility.setVisibility(myView, false)
        Assert.assertEquals(myView.visibility, View.GONE)
        ViewUtility.setVisibility(myView, true)
        Assert.assertEquals(myView.visibility, View.VISIBLE)
    }

    @Test
    fun toggleVisibilityPairs_success() {
        val viewA = TextView(context)
        viewA.text = "Hello, A!"
        val viewB = TextView(context)
        viewB.text = "Hi, B!"
        ViewUtility.toggleVisibilityPairs(viewA, viewB, true)
        Assert.assertEquals(viewA.visibility, View.VISIBLE)
        Assert.assertEquals(viewB.visibility, View.GONE)
        ViewUtility.toggleVisibilityPairs(viewA, viewB, false)
        Assert.assertEquals(viewA.visibility, View.GONE)
        Assert.assertEquals(viewB.visibility, View.VISIBLE)
    }

    @Test
    fun displayPluralText_onePhoto() {
        testPluralText(1)
    }

    @Test
    fun displayPluralText_twoPhotos() {
        testPluralText(2)
    }

    @Test
    fun displayPluralText_zeroPhotos() {
        testPluralText(0)
    }

    private fun testPluralText(number: Int) {
        @PluralsRes
        val plurals = R.plurals.total_photos_format
        val expectedValue = resources.getQuantityString(plurals, number, number)
        val returnValue = ViewUtility.displayPluralText(resources, plurals, number)
        Assert.assertEquals(expectedValue, returnValue)
    }
}