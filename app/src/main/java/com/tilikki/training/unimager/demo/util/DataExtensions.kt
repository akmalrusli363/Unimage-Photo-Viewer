package com.tilikki.training.unimager.demo.util

import java.text.DateFormat
import java.util.*
import kotlin.math.absoluteValue

fun Int?.toString(): String {
    return if (this != null) {
        "$this"
    } else {
        "n/a"
    }
}

fun Int?.value(): Int {
    return this ?: 0
}

fun Int?.unsignedValue(): Int {
    return when {
        this == null -> -1
        this < 0 -> this.absoluteValue
        else -> this
    }
}

fun Date.formatAsString(): String {
    val dateFormatter = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT)
    return dateFormatter.format(this)
}
