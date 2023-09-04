package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_USERNAME
import com.tilikki.training.unimager.demo.mocks.DataMapper
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import java.util.Date

object EntityTestDataSet {
    fun generateSamplePhotoDataList(
        photoId: String = TestDataConstants.DEMO_PHOTO_ID,
        numOfData: Int = TestDataConstants.MAX_ITEMS_PER_PAGE
    ): List<Photo> {
        val photoList = mutableListOf<Photo>()
        for (i in 1..numOfData) {
            val singlePhotoId = generateIndexedPhotoId(photoId, i)
            photoList.add(generateSamplePhotoData(singlePhotoId))
        }
        return photoList
    }

    fun generateSamplePhotoData(photoId: String): Photo {
        val lipsumPhoto = PicsumPhotoProvider(photoId)
        TestDataConstants.run {
            return Photo(
                id = photoId,
                createdAt = Date(),
                width = 400,
                height = 400,
                color = DEMO_COLOR,
                likes = DEMO_LIKES,
                description = DEMO_DESCRIPTION,
                altDescription = generatePhotoAltDescription(photoId),
                thumbnailUrl = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.SMALL),
                previewUrl = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.MEDIUM),
                fullSizeUrl = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.LARGE),
                apiUrl = API_URL + "img/$photoId",
                htmlUrl = WEB_URL + "img/$photoId",
                owner = DEMO_USER_ID,
            )
        }
    }

    fun generateSampleUserData(username: String = DEMO_USERNAME): User {
        TestDataConstants.run {
            return User(
                id = DEMO_USER_ID,
                username = username,
                name = username,
                htmlUrl = WEB_URL + username,
                apiUrl = API_URL + username,
                apiPhotosUrl = API_URL + username + "/apiPhotos",
                profileImageUrl = API_URL + username + "/avatar",
                totalPhotos = DEMO_USER_TOTAL_PHOTOS,
                following = DEMO_USER_FOLLOWING,
                followers = DEMO_USER_FOLLOWERS
            )
        }
    }

    fun generateNewUserData(username: String = DEMO_USERNAME): User {
        TestDataConstants.run {
            return User(
                id = DEMO_USER_ID,
                username = username,
                name = username,
                htmlUrl = WEB_URL + username,
                apiUrl = API_URL + username,
                apiPhotosUrl = API_URL + username + "/apiPhotos",
                profileImageUrl = API_URL + username + "/avatar",
                totalPhotos = 0,
                following = 0,
                followers = 0
            )
        }
    }

    fun generateSampleUserPhotoDetail(
        photoId: String,
        username: String = DEMO_USERNAME
    ): PhotoDetail {
        return DataMapper.createPhotoDetail(
            generateSamplePhotoData(photoId),
            generateSampleUserData(username)
        )
    }
}
