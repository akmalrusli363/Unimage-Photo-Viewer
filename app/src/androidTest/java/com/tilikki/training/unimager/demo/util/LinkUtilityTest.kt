package com.tilikki.training.unimager.demo.util

import android.net.Uri
import org.junit.Assert
import org.junit.Test

class LinkUtilityTest {

    @Test
    fun convertToUri_https() {
        val siteUri = "https://example.com"
        val expectedUri = Uri.parse(siteUri)
        val convertedUri =
            LinkUtility.convertToUri(siteUri)
        Assert.assertEquals(expectedUri, convertedUri)
        Assert.assertEquals(expectedUri.toString(), convertedUri.toString())
    }

    @Test
    fun convertToUri_noPrefix() {
        val siteUri = "example.com"
        mapAndAssertUri(siteUri)
    }

    @Test
    fun convertToUri_httpAsHttps() {
        val siteUri = "http://example.com"
        mapAndAssertUri(siteUri)
    }

    @Test
    fun convertToUri_ftpAsHttps() {
        val siteUri = "ftp://example.com"
        mapAndAssertUri(siteUri)
    }

    private fun mapAndAssertUri(siteUri: String) {
        val expectedUri = Uri.parse(siteUri)
            .buildUpon()
            .scheme("https")
            .build()
        val convertedUri =
            LinkUtility.convertToUri(siteUri)
        Assert.assertEquals(expectedUri, convertedUri)
        Assert.assertEquals(expectedUri.toString(), convertedUri.toString())
    }
}