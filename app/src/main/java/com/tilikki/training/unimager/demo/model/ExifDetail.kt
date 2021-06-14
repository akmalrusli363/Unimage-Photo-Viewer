package com.tilikki.training.unimager.demo.model

data class ExifDetail(
    val brand: String?,
    val model: String?,
    val exposureTime: String?,
    val aperture: Float?,
    val focalLength: Float?,
    val iso: Int?
) {
    fun isEmpty(): Boolean {
        return brand.isNullOrBlank()
                && model.isNullOrBlank()
                && exposureTime.isNullOrBlank()
                && aperture == null
                && focalLength == null
                && iso == null
    }
}
