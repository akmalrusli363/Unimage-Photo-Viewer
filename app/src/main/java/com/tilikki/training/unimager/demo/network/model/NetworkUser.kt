package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName

data class NetworkUser(
    @SerializedName("id")
    val id: String,
    @SerializedName("username")
    val username: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("links")
    val profileUrl: ProfileLinks,
    @SerializedName("profile_image")
    val profileImage: ProfileImage,
) {
    data class ProfileImage(
        @SerializedName("medium")
        val imageUrl: String
    )

    data class ProfileLinks(
        @SerializedName("self")
        val apiLink: String,
        @SerializedName("html")
        val htmlLink: String,
        @SerializedName("photos")
        val photosLink: String,
    )
}
