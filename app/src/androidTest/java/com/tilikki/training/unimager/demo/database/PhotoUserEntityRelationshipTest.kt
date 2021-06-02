package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tilikki.training.unimager.demo.mocks.TestDataConstants
import com.tilikki.training.unimager.demo.model.Photo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException
import java.util.*

class PhotoUserEntityRelationshipTest {
    private companion object {
        const val DEMO_PHOTO_ID = "photo-demo-id"
        const val DEMO_USER_ID = "owner"
        const val DEMO_USERNAME = "username"
    }

    private lateinit var photosDao: PhotosDao
    private lateinit var userDao: UserDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        userDao = db.userDao
        photosDao = db.photosDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun getPhotoDetailById_success() {
        val photo = generateSamplePhotoData(DEMO_PHOTO_ID)
        photosDao.insertPhoto(photo.toDatabaseEntityPhoto())
        val user = generateSampleUserData()
        userDao.insertUser(user)

        val retrievedPhotoDetail = photosDao.getPhotoDetailById(DEMO_PHOTO_ID)
        Assert.assertEquals(photo, retrievedPhotoDetail.photos.first().toDomainEntityPhoto())
        Assert.assertEquals(user, retrievedPhotoDetail.user)
    }

    @Test
    fun getUserPhotosByUsername_success() {
        val photoList = mutableListOf<EntityPhoto>()
        for (i in 1..5) {
            val photoId = "${DEMO_PHOTO_ID}-$i"
            photoList.add(generateSamplePhotoData(photoId).toDatabaseEntityPhoto())
        }
        photosDao.insertAll(photoList)

        val user = generateSampleUserData()
        userDao.insertUser(user)
        val retrievedUserRelationship = userDao.getUserPhotosByUsername("username")
        Assert.assertArrayEquals(
            photoList.toTypedArray(),
            retrievedUserRelationship.photos.toTypedArray()
        )
    }

    private fun generateSamplePhotoData(photoId: String): Photo {
        return Photo(
            id = photoId,
            createdAt = Date(),
            width = 400,
            height = 400,
            color = "red",
            likes = 100,
            description = "description",
            altDescription = "alt description",
            thumbnailUrl = TestDataConstants.WEB_URL + "thumb/$photoId.jpg",
            previewUrl = TestDataConstants.WEB_URL + "preview/$photoId.jpg",
            fullSizeUrl = TestDataConstants.WEB_URL + "full/$photoId.jpg",
            apiUrl = TestDataConstants.API_URL + "img/$photoId",
            htmlUrl = TestDataConstants.WEB_URL + "img/$photoId",
            owner = "owner",
        )
    }

    private fun generateSampleUserData(): EntityUser {
        return EntityUser(
            id = DEMO_USER_ID,
            username = DEMO_USERNAME,
            name = DEMO_USERNAME,
            htmlUrl = TestDataConstants.WEB_URL + DEMO_USERNAME,
            apiUrl = TestDataConstants.API_URL + DEMO_USERNAME,
            apiPhotosUrl = TestDataConstants.API_URL + DEMO_USERNAME + "/apiPhotos",
            profileImageUrl = TestDataConstants.API_URL + DEMO_USERNAME + "/avatar",
            totalPhotos = 10,
            following = 10,
            followers = 10
        )
    }
}