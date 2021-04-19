package com.tilikki.training.unimager.demo.model

import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.model.util.Resolution
import java.util.*

data class Photo(
    val id: String,
    val createdAt: Date,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int?,
    val description: String?,
    val altDescription: String?,
    val thumbnailUrl: String,
    val previewUrl: String,
    val fullSizeUrl: String,
    val apiUrl: String,
    val htmlUrl: String,
    val owner: String,
) {
    fun getResolution(): Resolution {
        return Resolution(width, height)
    }

    fun toDatabaseEntityPhoto(): EntityPhoto {
        return EntityPhoto(
            id = id,
            createdAt = createdAt,
            width = width,
            height = height,
            color = color,
            likes = likes,
            description = description,
            altDescription = altDescription,
            thumbnailUrl = thumbnailUrl,
            previewUrl = previewUrl,
            fullSizeUrl = fullSizeUrl,
            detailUrl = apiUrl,
            webLinkUrl = htmlUrl,
            owner = owner,
        )
    }
}
