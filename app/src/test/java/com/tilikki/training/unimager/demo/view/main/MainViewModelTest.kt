package com.tilikki.training.unimager.demo.view.main

import androidx.lifecycle.Observer
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.network.FetchResponse
import com.tilikki.training.unimager.demo.view.base.GenericViewModelTest
import io.reactivex.Observable
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class MainViewModelTest : GenericViewModelTest() {
    @Mock
    private lateinit var photoObserver: Observer<in List<Photo>?>

    @Mock
    private lateinit var fetchStatusObserver: Observer<FetchResponse>

    private val mainViewModel = MainViewModel(unsplashRepository)

    @Before
    override fun setup() {
        mainViewModel.photos.observeForever(photoObserver)
        mainViewModel.successResponse.observeForever(fetchStatusObserver)
    }

    @Test
    fun fetchPhotos_fetchSuccess() {
        val searchQuery = "search"
        val photoList = EntityTestDataSet.generateSamplePhotoDataList()
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery))
            .thenReturn(Observable.just(photoList.toList()))

        mainViewModel.fetchPhotos(searchQuery)
        Mockito.verify(unsplashRepository).getPhotos(searchQuery)

        Assert.assertTrue(mainViewModel.successResponse.value!!.success)
        Assert.assertEquals(photoList, mainViewModel.photos.value)
    }

    @Test
    fun fetchPhotos_fetchFailed() {
        val searchQuery = "search"
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery))
            .thenReturn(Observable.error(error))

        mainViewModel.fetchPhotos(searchQuery)

        Assert.assertFalse(mainViewModel.successResponse.value!!.success)
        Assert.assertEquals(error, mainViewModel.successResponse.value!!.error)
        Assert.assertNull(mainViewModel.photos.value)
    }

    @Test
    fun fetchPhotos_fetchEmpty() {
        val searchQuery = "search"
        val photoList = listOf<Photo>()
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery))
            .thenReturn(Observable.just(photoList))

        mainViewModel.fetchPhotos(searchQuery)

        Assert.assertTrue(mainViewModel.successResponse.value!!.success)
        Assert.assertEquals(photoList, mainViewModel.photos.value)
    }
}