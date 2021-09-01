package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.model.util.Resolution
import kotlin.math.abs

class PicsumPhotoProvider(photoId: String) {
    companion object {
        const val LIPSUM_BASE_URL = "https://picsum.photos/"
        const val LIPSUM_GET_BY_PHOTO_ID = "$LIPSUM_BASE_URL/id/"
    }

    val photoIdHash = photoId.hashCode() % 1000

    fun generatePicsumPhotoUrl(size: PhotoSize): String {
        val ratioIndex = abs(photoIdHash % 3)
        val aspectRatio = PhotoRatio.values()[ratioIndex]
        val resolution = getGeneratedResolution(aspectRatio, size)
        return "$LIPSUM_GET_BY_PHOTO_ID/$photoIdHash/${resolution.width}/${resolution.height}"
    }

    private fun getGeneratedResolution(ratio: PhotoRatio, size: PhotoSize): Resolution {
        val height = size.baseSize
        val width = (height * ratio.ratio).toInt()
        return Resolution(width, height)
    }
}

enum class PhotoRatio(internal val ratio: Double) {
    LANDSCAPE(4 / 3.0), SQUARE(1.0), PORTRAIT(3 / 4.0)
}

enum class PhotoSize(internal val baseSize: Int) {
    SMALL(240), MEDIUM(480), LARGE(720)
}
