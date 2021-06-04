package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
import java.util.*

object EntityTestDataSet {
    fun generateSamplePhotoDataList(
        photoId: String = TestDataConstants.DEMO_PHOTO_ID,
        numOfData: Int = 10
    ): List<Photo> {
        val photoList = mutableListOf<Photo>()
        for (i in 1..numOfData) {
            val singlePhotoId = "$photoId-$i"
            photoList.add(generateSamplePhotoData(singlePhotoId))
        }
        return photoList
    }

    fun generateSamplePhotoData(photoId: String): Photo {
        TestDataConstants.run {
            return Photo(
                id = photoId,
                createdAt = Date(),
                width = 400,
                height = 400,
                color = "red",
                likes = 100,
                description = "description",
                altDescription = "alt description",
                thumbnailUrl = WEB_URL + "thumb/$photoId.jpg",
                previewUrl = WEB_URL + "preview/$photoId.jpg",
                fullSizeUrl = WEB_URL + "full/$photoId.jpg",
                apiUrl = API_URL + "img/$photoId",
                htmlUrl = WEB_URL + "img/$photoId",
                owner = "owner",
            )
        }
    }

    fun generateSampleUserData(): User {
        TestDataConstants.run {
            return User(
                id = DEMO_USER_ID,
                username = DEMO_USERNAME,
                name = DEMO_USERNAME,
                htmlUrl = WEB_URL + DEMO_USERNAME,
                apiUrl = API_URL + DEMO_USERNAME,
                apiPhotosUrl = API_URL + DEMO_USERNAME + "/apiPhotos",
                profileImageUrl = API_URL + DEMO_USERNAME + "/avatar",
                totalPhotos = 10,
                following = 10,
                followers = 10
            )
        }
    }
}