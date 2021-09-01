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
            ratio > 1.1 -> LANDSCAPE
            ratio < 0.9 -> PORTRAIT
            else -> SQUARE
        }
    }

    companion object {
        const val SQUARE = "Square"
        const val PORTRAIT = "Portrait"
        const val LANDSCAPE = "Landscape"
    }
}
