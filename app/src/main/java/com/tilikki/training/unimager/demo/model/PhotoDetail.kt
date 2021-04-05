package com.tilikki.training.unimager.demo.model

import java.util.*

data class PhotoDetail(
    val id: String,
    val createdAt: Date,
    val width: Int,
    val height: Int,
    val color: String,
    val likes: Int?,
    val description: String?,
    val altDescription: String?,
    val thumbnailUrl: String,
    val imageUrl: String,
    val apiUrl: String,
    val htmlUrl: String,
    val user: User,
)