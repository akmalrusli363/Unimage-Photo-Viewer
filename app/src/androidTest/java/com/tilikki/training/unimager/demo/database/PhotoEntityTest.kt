package com.tilikki.training.unimager.demo.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.utility.asDatabaseEntityPhotos
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class PhotoEntityTest {
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
        val photo = EntityTestDataSet.generateSamplePhotoData(TestDataConstants.DEMO_PHOTO_ID)
        photosDao.insertPhoto(photo.toDatabaseEntityPhoto())
        val photoById = photosDao.getPhotoById(TestDataConstants.DEMO_PHOTO_ID)
        Assert.assertEquals(photo, photoById.toDomainEntityPhoto())
    }

    @Test
    fun insertAllPhotos_success() {
        val photoList = mutableListOf<EntityPhoto>()
        for (i in 1..10) {
            val photoId = "${TestDataConstants.DEMO_PHOTO_ID}-$i"
            photoList.add(
                EntityTestDataSet.generateSamplePhotoData(photoId).toDatabaseEntityPhoto()
            )
        }
        photosDao.insertAll(photoList)
        for (i in 1..10) {
            val photoId = "${TestDataConstants.DEMO_PHOTO_ID}-$i"
            val photoById = photosDao.getPhotoById(photoId)
            Assert.assertEquals(photoId, photoById.id)
        }
    }

    @Test
    fun insertAndGetSearchResults_success() {
        val photoList = EntityTestDataSet.generateSamplePhotoDataList().asDatabaseEntityPhotos()
        val searchQueryItems = mutableListOf<SearchQuery>()
        val search = "TEST-SEARCH"
        photosDao.insertAll(photoList)
        photoList.map {
            searchQueryItems.add(SearchQuery(it.id, search))
        }

        photosDao.insertSearchResults(searchQueryItems)
        val fetchedItems = photosDao.getPhotoSearchResult(search)
        val retrievedItem = fetchedItems.map {
            it.photo
        }
        Assert.assertArrayEquals(photoList.toTypedArray(), retrievedItem.toTypedArray())
    }
}