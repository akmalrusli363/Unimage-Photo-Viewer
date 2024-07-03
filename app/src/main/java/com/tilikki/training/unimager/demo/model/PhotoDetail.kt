package com.tilikki.training.unimager.demo.model

import com.tilikki.training.unimager.demo.model.util.Resolution
import com.tilikki.training.unimager.demo.network.model.PhotoTopicData
import java.util.Date

data class PhotoDetail(
    val id: String,
    val createdAt: Date,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int?,
    val views: Int?,
    val downloads: Int?,
    val description: String?,
    val altDescription: String?,
    val thumbnailUrl: String,
    val previewUrl: String,
    val fullSizeUrl: String,
    val downloadUrl: String,
    val apiUrl: String,
    val htmlUrl: String,
    val user: User,
    val exif: ExifDetail?,
    val topics: List<PhotoTopicData> = listOf(),
) {
    fun getResolution(): Resolution {
        return Resolution(width, height)
    }

    fun getOrientation(): String {
        return getResolution().getOrientation()
    }
}

