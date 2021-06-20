package com.tilikki.training.unimager.demo.mocks

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User

object DataMapper {
    fun createPhotoDetail(photo: Photo, user: User): PhotoDetail {
        photo.run {
            return PhotoDetail(
                id = id,
                createdAt = createdAt,
                width = width,
                height = height,
                color = color,
                likes = likes,
                description = description,
                altDescription = altDescription,
                thumbnailUrl = thumbnailUrl,
                previewUrl = previewUrl,
                fullSizeUrl = fullSizeUrl,
                apiUrl = apiUrl,
                htmlUrl = htmlUrl,
                user = user,
                exif = null
            )
        }
    }
}
