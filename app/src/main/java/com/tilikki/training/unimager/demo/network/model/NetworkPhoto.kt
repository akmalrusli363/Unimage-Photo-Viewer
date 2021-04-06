package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName
import com.tilikki.training.unimager.demo.database.EntityPhoto
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
) {
    data class PhotoUrl(
        @SerializedName("full")
        val fullSize: String,
        @SerializedName("thumb")
        val thumbnailSize: String
    )

    data class LinkUrl(
        @SerializedName("self")
        val apiLink: String,
        @SerializedName("html")
        val webLink: String
    )

    fun toDatabaseEntityPhoto(searchQuery: String): EntityPhoto {
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
            fullSizeUrl = imageUrl.fullSize,
            detailUrl = linkUrl.apiLink,
            webLinkUrl = linkUrl.webLink,
            owner = user.username,
            searchQuery = searchQuery
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
            imageUrl = imageUrl.fullSize,
            apiUrl = linkUrl.apiLink,
            htmlUrl = linkUrl.webLink,
            owner = user.username,
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
            imageUrl = imageUrl.fullSize,
            apiUrl = linkUrl.apiLink,
            htmlUrl = linkUrl.webLink,
            user = user.toDomainEntityUser()
        )
    }
}