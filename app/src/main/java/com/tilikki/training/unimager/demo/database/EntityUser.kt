package com.tilikki.training.unimager.demo.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class EntityUser(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: String,
    @ColumnInfo(name = "username")
    val username: String,
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "html_url")
    val htmlUrl: String,
    @ColumnInfo(name = "api_url")
    val apiUrl: String,
    @ColumnInfo(name = "api_photos_url")
    val apiPhotosUrl: String,
    @ColumnInfo(name = "profile_image_url")
    val profileImageUrl: String,
    @ColumnInfo(name = "total_photos")
    val totalPhotos: Int,
    @ColumnInfo(name = "following")
    val following: Int?,
    @ColumnInfo(name = "followers")
    val followers: Int?,
)
