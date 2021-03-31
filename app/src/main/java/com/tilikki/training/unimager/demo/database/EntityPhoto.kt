package com.tilikki.training.unimager.demo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.tilikki.training.unimager.demo.model.Photo
import java.util.*

@Entity
data class EntityPhoto constructor(
    @PrimaryKey
    val id: String,
    val createdAt: Long,
    val width: Int,
    val height: Int,
    val color: String,
    val like: Int,
    val description: String,
    val altDescription: String,
    val thumbnailUrl: String,
    val fullSizeUrl: String,
    val detailUrl: String,
    val webLinkUrl: String
) {
    fun toDomainEntityPhoto(): Photo {
        return Photo(
            id = id,
            createdAt = Date(createdAt),
            width = width,
            height = height,
            color = color,
            like = like,
            description = description,
            altDescription = altDescription,
            thumbnailUrl = thumbnailUrl,
            imageUrl = fullSizeUrl,
            apiUrl = detailUrl,
            htmlUrl = webLinkUrl
        )
    }
}