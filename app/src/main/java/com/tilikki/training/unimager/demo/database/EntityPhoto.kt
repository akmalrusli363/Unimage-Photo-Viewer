package com.tilikki.training.unimager.demo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.tilikki.training.unimager.demo.model.Photo
import java.util.*

@Entity(tableName = "photos")
@TypeConverters(EntityTypeConverter::class)
data class EntityPhoto constructor(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "created")
    val createdAt: Date,
    @ColumnInfo(name = "width")
    val width: Int,
    @ColumnInfo(name = "height")
    val height: Int,
    @ColumnInfo(name = "color")
    val color: String,
    @ColumnInfo(name = "likes")
    val likes: Int? = null,
    @ColumnInfo(name = "description")
    val description: String? = null,
    @ColumnInfo(name = "alt_description")
    val altDescription: String? = null,
    @ColumnInfo(name = "thumbnail_url")
    val thumbnailUrl: String,
    @ColumnInfo(name = "full_size_url")
    val fullSizeUrl: String,
    @ColumnInfo(name = "detail_url")
    val detailUrl: String,
    @ColumnInfo(name = "web_link_url")
    val webLinkUrl: String,
    @ColumnInfo(name = "owner_id")
    val owner: String,
) {
    fun toDomainEntityPhoto(): Photo {
        return Photo(
            id = id,
            createdAt = createdAt,
            width = width,
            height = height,
            color = color,
            likes = likes,
            description = description,
            altDescription = altDescription,
            thumbnailUrl = thumbnailUrl,
            imageUrl = fullSizeUrl,
            apiUrl = detailUrl,
            htmlUrl = webLinkUrl,
            owner = owner,
        )
    }
}