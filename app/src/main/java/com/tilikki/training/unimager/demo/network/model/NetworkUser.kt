package com.tilikki.training.unimager.demo.network.model

import com.google.gson.annotations.SerializedName
import com.tilikki.training.unimager.demo.database.EntityUser
import com.tilikki.training.unimager.demo.model.User

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
    @SerializedName("total_photos")
    val totalPhotos: Int,
    @SerializedName("following_count")
    val following: Int?,
    @SerializedName("followers_count")
    val followers: Int?,
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

    fun toDomainEntityUser(): User {
        return User(
            id = id,
            username = username,
            name = name,
            htmlUrl = profileUrl.htmlLink,
            apiUrl = profileUrl.apiLink,
            apiPhotosUrl = profileUrl.photosLink,
            profileImageUrl = profileImage.imageUrl,
            totalPhotos = totalPhotos,
            following = following,
            followers = followers,
        )
    }

    fun toDatabaseEntityUser(): EntityUser {
        return EntityUser(
            id = id,
            username = username,
            name = name,
            htmlUrl = profileUrl.htmlLink,
            apiUrl = profileUrl.apiLink,
            apiPhotosUrl = profileUrl.photosLink,
            profileImageUrl = profileImage.imageUrl,
            totalPhotos = totalPhotos,
            following = following,
            followers = followers,
        )
    }
}
