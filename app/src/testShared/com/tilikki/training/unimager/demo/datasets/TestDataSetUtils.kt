package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.network.model.Exif

fun generateIndexedPhotoId(photoId: String, index: Int): String {
    return "$photoId-$index"
}

fun generatePhotoAltDescription(photoId: String): String {
    return "alt description for $photoId"
}

fun generateExif(): Exif {
    return Exif(
        brand = TestDataConstants.DEMO_EXIF_BRAND,
        model = TestDataConstants.DEMO_EXIF_MODEL,
        exposureTime = TestDataConstants.DEMO_EXIF_EXPOSURE_TIME,
        aperture = TestDataConstants.DEMO_EXIF_APERTURE,
        focalLength = TestDataConstants.DEMO_EXIF_FOCAL_LENGTH,
        iso = TestDataConstants.DEMO_EXIF_ISO
    )
}
