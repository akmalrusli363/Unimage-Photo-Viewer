package com.tilikki.training.unimager.demo.model.util

data class Resolution(
    val width: Int,
    val height: Int
) {
    override fun toString(): String {
        return "$width x $height"
    }

    fun getRatio(): Float {
        return (width / height.toFloat())
    }

    fun getOrientation(): String {
        val ratio = getRatio()
        return when {
            ratio > 1.1 -> "Landscape"
            ratio < 0.9 -> "Portrait"
            else -> "Square"
        }
    }
}
