package com.tilikki.training.unimager.demo.util

import com.tilikki.training.unimager.demo.model.ExifDetail
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import java.util.Date

object SampleComposePreviewData {
    fun createPhotoDetail(photo: Photo, user: User, exif: ExifDetail? = null): PhotoDetail {
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
                exif = exif
            )
        }
    }

    fun generateSamplePhotoData(photoId: String): Photo {
        return Photo(
            id = photoId,
            createdAt = Date(),
            width = 400,
            height = 400,
            color = DEMO_COLOR,
            likes = DEMO_LIKES,
            description = DEMO_DESCRIPTION,
            altDescription = "alt description for $photoId containing lorem ipsum dolor sit amet, typically written in latin",
            thumbnailUrl = "",
            previewUrl = "",
            fullSizeUrl = "",
            apiUrl = API_URL + "img/$photoId",
            htmlUrl = WEB_URL + "img/$photoId",
            owner = DEMO_USER_ID,
        )
    }

    fun generateSampleUserData(username: String = DEMO_USERNAME): User {
        return User(
            id = DEMO_USER_ID,
            username = username,
            name = username,
            htmlUrl = WEB_URL + username,
            apiUrl = API_URL + username,
            apiPhotosUrl = "$API_URL$username/apiPhotos",
            profileImageUrl = "$API_URL$username/avatar",
            totalPhotos = DEMO_USER_TOTAL_PHOTOS,
            following = DEMO_USER_FOLLOWING,
            followers = DEMO_USER_FOLLOWERS
        )
    }

    const val WEB_URL = "http://test.t/"
    const val API_URL = "http://api.test.t/"

    const val DEMO_PHOTO_ID = "photo-demo-id"
    const val DEMO_PHOTO_EXIF_ID = "photo-demo-exif-id"
    const val DEMO_USER_ID = "owner"
    const val DEMO_USERNAME = "username"

    const val DEMO_DESCRIPTION = "description"
    const val DEMO_COLOR = "red"
    const val DEMO_LIKES = 100

    const val DEMO_EXIF_BRAND = "BRAND"
    const val DEMO_EXIF_MODEL = "MODEL"
    const val DEMO_EXIF_EXPOSURE_TIME = "1/1000"
    const val DEMO_EXIF_APERTURE: Float = 1.0f
    const val DEMO_EXIF_FOCAL_LENGTH: Float = 10f
    const val DEMO_EXIF_ISO: Int = 100

    const val DEMO_USER_FOLLOWING = 10
    const val DEMO_USER_FOLLOWERS = 10
    const val DEMO_USER_TOTAL_PHOTOS = 15
}