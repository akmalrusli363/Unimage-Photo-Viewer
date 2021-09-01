package com.tilikki.training.unimager.demo.datasets

import com.tilikki.training.unimager.demo.network.model.LinkUrl
import com.tilikki.training.unimager.demo.network.model.NetworkPhoto
import com.tilikki.training.unimager.demo.network.model.NetworkUser
import com.tilikki.training.unimager.demo.network.model.PhotoUrl
import java.util.*

object NetworkTestDataSet {
    fun generateSamplePhotoDataList(
        photoId: String = TestDataConstants.DEMO_PHOTO_ID,
        numOfData: Int = TestDataConstants.MAX_ITEMS_PER_PAGE,
        username: String = TestDataConstants.DEMO_USERNAME
    ): List<NetworkPhoto> {
        val photoList = mutableListOf<NetworkPhoto>()
        for (i in 1..numOfData) {
            val singlePhotoId = generateIndexedPhotoId(photoId, i)
            photoList.add(generateSamplePhotoData(singlePhotoId, username))
        }
        return photoList
    }

    fun generateSamplePhotoData(
        photoId: String,
        username: String = TestDataConstants.DEMO_USERNAME
    ): NetworkPhoto {
        val user = generateSampleUserData(username)
        val photoLink = generatePhotoLink(photoId)
        val containsExif = photoId.contains("exif", true)
        return NetworkPhoto(
            id = photoId,
            createdAt = Date(),
            width = 400,
            height = 400,
            color = TestDataConstants.DEMO_COLOR,
            likes = TestDataConstants.DEMO_LIKES,
            description = TestDataConstants.DEMO_DESCRIPTION,
            altDescription = generatePhotoAltDescription(photoId),
            imageUrl = photoLink.first,
            linkUrl = photoLink.second,
            user = user,
            exif = if (containsExif) generateExif() else null
        )
    }

    fun generateSampleUserData(username: String = TestDataConstants.DEMO_USERNAME): NetworkUser {
        return NetworkUser(
            id = TestDataConstants.DEMO_USER_ID,
            username = username,
            name = username,
            profileUrl = NetworkUser.ProfileLinks(
                htmlLink = TestDataConstants.WEB_URL + username,
                apiLink = TestDataConstants.API_URL + username,
                photosLink = TestDataConstants.API_URL + username + "/apiPhotos",
            ),
            profileImage = NetworkUser.ProfileImage(
                imageUrl = TestDataConstants.API_URL + username + "/avatar"
            ),
            totalPhotos = 10,
            following = 10,
            followers = 10
        )
    }

    private fun generatePhotoLink(photoId: String): Pair<PhotoUrl, LinkUrl> {
        val imageUrl = generateSamplePhotoUrl(photoId)
        val linkUrl = LinkUrl(
            apiLink = TestDataConstants.API_URL + "img/$photoId",
            webLink = TestDataConstants.WEB_URL + "img/$photoId",
            downloadLink = TestDataConstants.API_URL + "img/dl/$photoId",
            apiDownloadLink = TestDataConstants.API_URL + "img/dl/$photoId",
        )
        return Pair(imageUrl, linkUrl)
    }

    private fun generateSamplePhotoUrl(photoId: String): PhotoUrl {
        val lipsumPhoto = PicsumPhotoProvider(photoId)
        return PhotoUrl(
            thumbnailSize = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.SMALL),
            smallSize = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.MEDIUM),
            regularSize = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.MEDIUM),
            fullSize = lipsumPhoto.generatePicsumPhotoUrl(PhotoSize.LARGE),
        )
    }

}
