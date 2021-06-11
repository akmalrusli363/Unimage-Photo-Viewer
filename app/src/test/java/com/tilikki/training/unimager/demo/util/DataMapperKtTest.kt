package com.tilikki.training.unimager.demo.util

import com.tilikki.training.unimager.demo.database.PhotoSearches
import com.tilikki.training.unimager.demo.database.SearchQuery
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.NetworkTestDataSet
import com.tilikki.training.unimager.demo.utility.asDatabaseEntityPhotos
import org.junit.Assert.assertEquals
import org.junit.Test

class DataMapperKtTest {

    @Test
    fun fromNetworkPhotoAsDomainEntityPhoto() {
        val networkPhotoList = NetworkTestDataSet.generateSamplePhotoDataList()
        val photoList = networkPhotoList.asDomainEntityPhotos()
        photoList.forEachAssert(networkPhotoList) { expected, value ->
            assertEquals(expected.id, value.id)
            assertEquals(expected.user.id, value.owner)
            assertEquals(expected.createdAt, value.createdAt)
            assertEquals(expected.width, value.width)
            assertEquals(expected.height, value.height)
            assertEquals(expected.color, value.color)
            assertEquals(expected.likes, value.likes)
            assertEquals(expected.description, value.description)
            assertEquals(expected.altDescription, value.altDescription)
            assertEquals(expected.imageUrl.thumbnailSize, value.thumbnailUrl)
            assertEquals(expected.imageUrl.regularSize, value.previewUrl)
            assertEquals(expected.imageUrl.fullSize, value.fullSizeUrl)
            assertEquals(expected.linkUrl.apiLink, value.apiUrl)
            assertEquals(expected.linkUrl.webLink, value.htmlUrl)
        }
    }

    @Test
    fun fromNetworkPhotoAsDatabaseEntityPhotos() {
        val networkPhotoList = NetworkTestDataSet.generateSamplePhotoDataList()
        val photoList = networkPhotoList.asDatabaseEntityPhotos()
        photoList.forEachAssert(networkPhotoList) { expected, value ->
            assertEquals(expected.id, value.id)
            assertEquals(expected.createdAt, value.createdAt)
            assertEquals(expected.width, value.width)
            assertEquals(expected.height, value.height)
            assertEquals(expected.color, value.color)
            assertEquals(expected.likes, value.likes)
            assertEquals(expected.description, value.description)
            assertEquals(expected.altDescription, value.altDescription)
            assertEquals(expected.imageUrl.thumbnailSize, value.thumbnailUrl)
            assertEquals(expected.imageUrl.regularSize, value.previewUrl)
            assertEquals(expected.imageUrl.fullSize, value.fullSizeUrl)
            assertEquals(expected.linkUrl.apiLink, value.detailUrl)
            assertEquals(expected.linkUrl.webLink, value.webLinkUrl)
            assertEquals(expected.user.id, value.owner)
        }
    }

    @Test
    fun fromNetworkPhotoMapToSearchResults() {
        val search = "search"
        val networkPhotoList = NetworkTestDataSet.generateSamplePhotoDataList()
        val searchResult = networkPhotoList.mapToSearchResults(search)
        searchResult.forEachAssert(networkPhotoList) { expected, value ->
            assertEquals(expected.id, value.photoId)
            assertEquals(search, value.searchQuery)
        }
    }

    @Test
    fun fromEntityPhotoAsDomainEntityPhoto() {
        val dbPhotoList = NetworkTestDataSet.generateSamplePhotoDataList()
            .asDatabaseEntityPhotos()
        val photoList = dbPhotoList.asDomainEntityPhotos()
        photoList.forEachAssert(dbPhotoList) { expected, value ->
            assertEquals(expected.id, value.id)
            assertEquals(expected.createdAt, value.createdAt)
            assertEquals(expected.width, value.width)
            assertEquals(expected.height, value.height)
            assertEquals(expected.color, value.color)
            assertEquals(expected.likes, value.likes)
            assertEquals(expected.description, value.description)
            assertEquals(expected.altDescription, value.altDescription)
            assertEquals(expected.thumbnailUrl, value.thumbnailUrl)
            assertEquals(expected.previewUrl, value.previewUrl)
            assertEquals(expected.fullSizeUrl, value.fullSizeUrl)
            assertEquals(expected.detailUrl, value.apiUrl)
            assertEquals(expected.webLinkUrl, value.htmlUrl)
            assertEquals(expected.owner, value.owner)
        }
    }

    @Test
    fun fromPhotoSearchesGetPhotos() {
        val search = "search"
        val photoList = EntityTestDataSet.generateSamplePhotoDataList().asDatabaseEntityPhotos()
        val photoSearches = photoList.map {
            PhotoSearches(it, SearchQuery(it.id, search))
        }
        val fetchedPhotoList = photoSearches.getPhotos()
        fetchedPhotoList.forEachAssert(photoList) { expected, value ->
            assertEquals(expected, value)
        }
    }

    private inline fun <V, R> List<R>.forEachAssert(
        expected: List<V>,
        assertAction: (expected: V, value: R) -> Unit
    ) {
        val errorMessage = "Asserted value & returned list count are not same"
        assertEquals(errorMessage, expected.count(), this.count())
        for (index in expected.indices) {
            assertAction(expected[index], this[index])
        }
    }
}