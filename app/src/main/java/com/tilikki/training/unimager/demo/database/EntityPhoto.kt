package com.tilikki.training.unimager.demo.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tilikki.training.unimager.demo.model.Photo
import java.util.*

@Entity
data class EntityPhoto constructor(
    @PrimaryKey
    val id: String? = null,
    @TypeConverters(EntityTypeConverter::class)
    val createdAt: Date? = null,
    val width: Int? = null,
    val height: Int? = null,
    val color: String? = null,
    val like: Int? = null,
    val description: String? = null,
    val altDescription: String? = null,
    val thumbnailUrl: String? = null,
    val fullSizeUrl: String? = null,
    val detailUrl: String? = null,
    val webLinkUrl: String? = null
) {
    fun toDomainEntityPhoto(): Photo {
        return Photo(
            id = id,
            createdAt = createdAt,
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