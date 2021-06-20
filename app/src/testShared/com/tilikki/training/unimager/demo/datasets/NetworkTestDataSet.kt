package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.network.model.LinkUrl
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import com.tilikki.training.unimager.demo.network.model.PhotoUrl
import java.util.*

object NetworkTestDataSet {
    fun generateSamplePhotoDataList(
        photoId: String = TestDataConstants.DEMO_PHOTO_ID,
        numOfData: Int = 10
    ): List<NetworkPhoto> {
        val photoList = mutableListOf<NetworkPhoto>()
        for (i in 1..numOfData) {
            val singlePhotoId = "$photoId-$i"
            photoList.add(generateSamplePhotoData(singlePhotoId))
        }
        return photoList
    }

    fun generateSamplePhotoData(photoId: String): NetworkPhoto {
        val photoLink = generatePhotoLink(photoId)
        return NetworkPhoto(
            id = photoId,
            createdAt = Date(),
            width = 400,
            height = 400,
            color = "red",
            likes = 100,
            description = "description",
            altDescription = "alt description",
            imageUrl = photoLink.first,
            linkUrl = photoLink.second,
            user = generateSampleUserData(),
            exif = null
        )
    }

    fun generateSampleUserData(): NetworkUser {
        return NetworkUser(
            id = TestDataConstants.DEMO_USER_ID,
            username = TestDataConstants.DEMO_USERNAME,
            name = TestDataConstants.DEMO_USERNAME,
            profileUrl = NetworkUser.ProfileLinks(
                htmlLink = TestDataConstants.WEB_URL + TestDataConstants.DEMO_USERNAME,
                apiLink = TestDataConstants.API_URL + TestDataConstants.DEMO_USERNAME,
                photosLink = TestDataConstants.API_URL + TestDataConstants.DEMO_USERNAME + "/apiPhotos",
            ),
            profileImage = NetworkUser.ProfileImage(
                imageUrl = TestDataConstants.API_URL + TestDataConstants.DEMO_USERNAME + "/avatar"
            ),
            totalPhotos = 10,
            following = 10,
            followers = 10
        )
    }

    private fun generatePhotoLink(photoId: String): Pair<PhotoUrl, LinkUrl> {
        val imageUrl = PhotoUrl(
            thumbnailSize = TestDataConstants.WEB_URL + "thumb/$photoId.jpg",
            smallSize = TestDataConstants.WEB_URL + "preview/$photoId.jpg",
            regularSize = TestDataConstants.WEB_URL + "preview/$photoId.jpg",
            fullSize = TestDataConstants.WEB_URL + "full/$photoId.jpg",
        )
        val linkUrl = LinkUrl(
            apiLink = TestDataConstants.API_URL + "img/$photoId",
            webLink = TestDataConstants.WEB_URL + "img/$photoId",
            downloadLink = TestDataConstants.API_URL + "img/dl/$photoId",
            apiDownloadLink = TestDataConstants.API_URL + "img/dl/$photoId",
        )
        return Pair(imageUrl, linkUrl)
    }
}
