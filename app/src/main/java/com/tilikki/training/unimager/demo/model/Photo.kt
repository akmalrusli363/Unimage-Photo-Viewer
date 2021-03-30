package com.tilikki.training.unimager.demo.model

import com.google.gson.annotations.SerializedName
import java.util.*

data class Photo(
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
    @SerializedName("like")
    val like: Int,
    @SerializedName("description")
    val description: String,
    @SerializedName("urls")
    val imageUrl: PhotoUrl,
    @SerializedName("link_url")
    val linkUrl: String
) {
    data class PhotoUrl(
        @SerializedName("full")
        val fullSize: String,
        @SerializedName("regular")
        val defaultSize: String,
        @SerializedName("small")
        val thumbnailSize: String
    )
}
