package com.tilikki.training.unimager.demo.model

import com.tilikki.training.unimager.demo.database.EntityPhoto
import java.util.*

data class Photo(
    val id: String,
    val createdAt: Date,
    val width: Int,
    val height: Int,
    val color: String,
    val like: Int?,
    val description: String?,
    val altDescription: String?,
    val thumbnailUrl: String,
    val imageUrl: String,
    val apiUrl: String,
    val htmlUrl: String,
) {
    fun toDatabaseEntityPhoto(): EntityPhoto {
        return EntityPhoto(
            id = id,
            createdAt = createdAt,
            width = width,
            height = height,
            color = color,
            like = like,
            description = description,
            altDescription = altDescription,
            thumbnailUrl = thumbnailUrl,
            fullSizeUrl = imageUrl,
            detailUrl = apiUrl,
            webLinkUrl = htmlUrl,
        )
    }
}
