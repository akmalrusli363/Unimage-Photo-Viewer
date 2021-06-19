package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName
import com.tilikki.training.unimager.demo.database.EntityPhoto
import com.tilikki.training.unimager.demo.database.SearchQuery
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import java.util.*

data class NetworkPhoto(
    @SerializedName("id")
    val id: String,
    @SerializedName("created_at")
    val createdAt: Date,
    @SerializedName("width")
    val width: Int,
    @SerializedName("height")
    val height: Int,
    @SerializedName("color")
    val color: String,
    @SerializedName("likes")
    val likes: Int?,
    @SerializedName("description")
    val description: String?,
    @SerializedName("alt_description")
    val altDescription: String?,
    @SerializedName("urls")
    val imageUrl: PhotoUrl,
    @SerializedName("links")
    val linkUrl: LinkUrl,
    @SerializedName("user")
    val user: NetworkUser,
    @SerializedName("exif")
    val exif: Exif?,
) {
    fun bindSearchQuery(searchQuery: String): SearchQuery {
        return SearchQuery(photoId = id, searchQuery = searchQuery)
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
            thumbnailUrl = imageUrl.thumbnailSize,
            previewUrl = imageUrl.regularSize,
            fullSizeUrl = imageUrl.fullSize,
            detailUrl = linkUrl.apiLink,
            webLinkUrl = linkUrl.webLink,
            owner = user.id,
        )
    }

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
            thumbnailUrl = imageUrl.thumbnailSize,
            previewUrl = imageUrl.regularSize,
            fullSizeUrl = imageUrl.fullSize,
            apiUrl = linkUrl.apiLink,
            htmlUrl = linkUrl.webLink,
            owner = user.id,
        )
    }

    fun toDomainEntityPhotoDetail(): PhotoDetail {
        return PhotoDetail(
            id = id,
            createdAt = createdAt,
            width = width,
            height = height,
            color = color,
            likes = likes,
            description = description,
            altDescription = altDescription,
            thumbnailUrl = imageUrl.thumbnailSize,
            previewUrl = imageUrl.regularSize,
            fullSizeUrl = imageUrl.fullSize,
            apiUrl = linkUrl.apiLink,
            htmlUrl = linkUrl.webLink,
            user = user.toDomainEntityUser(),
            exif = exif?.asDomainEntityExif(),
        )
    }
}
