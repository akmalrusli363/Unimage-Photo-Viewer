package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants.DEMO_PHOTO_ID
import com.tilikki.training.unimager.demo.utility.asDatabaseEntityPhotos
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import java.io.IOException

class PhotoUserEntityRelationshipTest {
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
        val photo = EntityTestDataSet.generateSamplePhotoData(DEMO_PHOTO_ID)
        photosDao.insertPhoto(photo.toDatabaseEntityPhoto())
        val user = EntityTestDataSet.generateSampleUserData()
        userDao.insertUser(user.toDatabaseEntityUser())

        val retrievedPhotoDetail = photosDao.getPhotoDetailById(DEMO_PHOTO_ID)
        Assert.assertEquals(photo, retrievedPhotoDetail.photos.first().toDomainEntityPhoto())
        Assert.assertEquals(user, retrievedPhotoDetail.user.toDomainEntityUser())
    }

    @Test
    fun getUserPhotosByUsername_success() {
        val photoList = EntityTestDataSet.generateSamplePhotoDataList().asDatabaseEntityPhotos()
        photosDao.insertAll(photoList)
        val user = EntityTestDataSet.generateSampleUserData()
        userDao.insertUser(user.toDatabaseEntityUser())

        val retrievedUserRelationship = userDao.getUserPhotosByUsername("username")
        Assert.assertArrayEquals(
            photoList.toTypedArray(),
            retrievedUserRelationship.photos.toTypedArray()
        )
    }
}