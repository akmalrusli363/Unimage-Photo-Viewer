package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tilikki.training.unimager.demo.mocks.TestDataConstants
import com.tilikki.training.unimager.demo.model.Photo
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.*

@RunWith(AndroidJUnit4::class)
class PhotoEntityTest {
    private companion object {
        const val DEMO_PHOTO_ID = "photo-demo-id"
    }

    private lateinit var photosDao: PhotosDao
    private lateinit var db: RoomDB

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, RoomDB::class.java
        ).build()
        photosDao = db.photosDao
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun insertAndGetPhoto_success() {
        val photo = generateSamplePhotoData(DEMO_PHOTO_ID)
        photosDao.insertPhoto(photo.toDatabaseEntityPhoto())
        val photoById = photosDao.getPhotoById(DEMO_PHOTO_ID)
        Assert.assertEquals(photo, photoById.toDomainEntityPhoto())
    }

    @Test
    fun insertAllPhotos_success() {
        val photoList = mutableListOf<EntityPhoto>()
        for (i in 1..10) {
            val photoId = "${DEMO_PHOTO_ID}-$i"
            photoList.add(generateSamplePhotoData(photoId).toDatabaseEntityPhoto())
        }
        photosDao.insertAll(photoList)
        for (i in 1..10) {
            val photoId = "${DEMO_PHOTO_ID}-$i"
            val photoById = photosDao.getPhotoById(photoId)
            Assert.assertEquals(photoId, photoById.id)
        }
    }

    @Test
    fun insertAndGetSearchResults_success() {
        val photoList = mutableListOf<EntityPhoto>()
        val searchQueryItems = mutableListOf<SearchQuery>()
        val search = "TEST-SEARCH"
        for (i in 1..10) {
            val photoId = "${DEMO_PHOTO_ID}-$i"
            photoList.add(generateSamplePhotoData(photoId).toDatabaseEntityPhoto())
        }
        photosDao.insertAll(photoList)
        for (i in 1..10) {
            val photoId = "${DEMO_PHOTO_ID}-$i"
            searchQueryItems.add(SearchQuery(photoId, search))
        }
        photosDao.insertSearchResults(searchQueryItems)
        val fetchedItems = photosDao.getPhotoSearchResult(search)
        val retrievedItem = fetchedItems.map {
            it.photo
        }
        Assert.assertArrayEquals(photoList.toTypedArray(), retrievedItem.toTypedArray())
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
}