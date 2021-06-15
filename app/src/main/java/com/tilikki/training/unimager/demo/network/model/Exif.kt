package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName
import com.tilikki.training.unimager.demo.model.ExifDetail

data class Exif(
    @SerializedName("make")
    val brand: String?,
    @SerializedName("model")
    val model: String?,
    @SerializedName("exposure_time")
    val exposureTime: String?,
    @SerializedName("aperture")
    val aperture: Float?,
    @SerializedName("focal_length")
    val focalLength: Float?,
    @SerializedName("iso")
    val iso: Int?
) {
    fun asDomainEntityExif(): ExifDetail {
        return ExifDetail(
            brand, model, exposureTime, aperture, focalLength, iso
        )
    }
}
