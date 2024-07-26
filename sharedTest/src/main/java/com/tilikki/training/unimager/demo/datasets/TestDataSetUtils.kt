package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.network.model.Exif

fun generateIndexedPhotoId(photoId: String, index: Int): String {
    return "$photoId-$index"
}

fun generatePhotoDescription(photoId: String): String {
    return "photo description of $photoId"
}

fun generatePhotoAltDescription(photoId: String): String {
    return "alt description for $photoId"
}

fun generateIndexedPhotoDescription(photoId: String, index: Int): String {
    return generatePhotoDescription(generateIndexedPhotoId(photoId, index))
}

fun generateIndexedPhotoAltDescription(photoId: String, index: Int): String {
    return generatePhotoAltDescription(generateIndexedPhotoId(photoId, index))
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
