package com.tilikki.training.unimager.demo.model

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

}
