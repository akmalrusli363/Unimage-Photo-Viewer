package com.tilikki.training.unimager.demo.util

import android.icu.number.NumberFormatter
import android.icu.text.CompactDecimalFormat
import android.os.Build
import androidx.annotation.RequiresApi
import java.util.Locale

object DigitHelper {
    private val SUFFIXES = charArrayOf('k', 'm', 'g', 't', 'p', 'e')

    fun format(number: Int): String = format(number.toLong())

    fun format(number: Long): String {
        if (number < 1000) {
            return number.toString()
        }
        val string = number.toString()
        val magnitude = (string.length - 1) / 3
        val digits = (string.length - 1) % 3 + 1

        val value = CharArray(4)
        for (i in 0 until digits) {
            value[i] = string[i]
        }
        var valueLength = digits
        if (digits == 1 && string[1] != '0') {
            value[valueLength++] = '.'
            value[valueLength++] = string[1]
        }
        value[valueLength++] = SUFFIXES[magnitude - 1]
        return String(value, 0, valueLength)
    }

    @RequiresApi(Build.VERSION_CODES.N)
    fun formatIcu(number: Long): String {
        return CompactDecimalFormat.getInstance().format(number)
    }

    @RequiresApi(Build.VERSION_CODES.R)
    fun formatApi30(number: Long): String {
        return NumberFormatter.withLocale(Locale.ENGLISH).format(number).toString()
    }
}