package com.tilikki.training.unimager.demo.model

import com.tilikki.training.unimager.demo.database.EntityUser

data class User(
    val id: String,
    val username: String,
    val name: String,
    val htmlUrl: String,
    val apiUrl: String,
    val apiPhotosUrl: String,
    val profileImageUrl: String,
    val totalPhotos: Int,
    val following: Int?,
    val followers: Int?,
) {
    fun toDatabaseEntityUser(): EntityUser {
        return EntityUser(
            id = this.id,
            username = this.username,
            name = this.name,
            htmlUrl = this.htmlUrl,
            apiUrl = this.apiUrl,
            apiPhotosUrl = this.apiPhotosUrl,
            profileImageUrl = this.profileImageUrl,
            totalPhotos = this.totalPhotos,
            following = this.following,
            followers = this.followers,
        )
    }
}
