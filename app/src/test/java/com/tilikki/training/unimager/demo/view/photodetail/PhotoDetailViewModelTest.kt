package com.tilikki.training.unimager.demo.view.photodetail

import androidx.lifecycle.Observer
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.mocks.DataMapper
import com.tilikki.training.unimager.demo.model.PhotoDetail
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
class PhotoDetailViewModelTest : GenericViewModelTest() {
    @Mock
    private lateinit var photoIdObserver: Observer<String>

    @Mock
    private lateinit var photoDetailObserver: Observer<PhotoDetail>

    @Mock
    private lateinit var fetchStatusObserver: Observer<FetchResponse>

    private val photoDetailViewModel = PhotoDetailViewModel(unsplashRepository)

    @Before
    override fun setup() {
        photoDetailViewModel.photoId.observeForever(photoIdObserver)
        photoDetailViewModel.photo.observeForever(photoDetailObserver)
        photoDetailViewModel.successResponse.observeForever(fetchStatusObserver)
    }

    @Test
    fun attachPhoto_fetchSuccess() {
        val photoId = "photo-01"
        val photo = EntityTestDataSet.generateSamplePhotoData(photoId)
        val user = EntityTestDataSet.generateSampleUserData()
        val photoDetail = DataMapper.createPhotoDetail(photo, user)

        Mockito.`when`(unsplashRepository.getPhotoDetail(photoId))
            .thenReturn(Observable.just(photoDetail))

        photoDetailViewModel.attachPhoto(photoId)
        Mockito.verify(unsplashRepository).getPhotoDetail(photoId)

        Assert.assertTrue(photoDetailViewModel.successResponse.value!!.success)
        Assert.assertEquals(photoId, photoDetailViewModel.photoId.value)
        Assert.assertEquals(photoDetail, photoDetailViewModel.photo.value)
    }

    @Test
    fun attachPhoto_fetchFailed() {
        val photoId = "photo-01"
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getPhotoDetail(photoId))
            .thenReturn(Observable.error(error))

        photoDetailViewModel.attachPhoto(photoId)

        Assert.assertFalse(photoDetailViewModel.successResponse.value!!.success)
        Assert.assertEquals(error, photoDetailViewModel.successResponse.value!!.error)
        Assert.assertEquals(photoId, photoDetailViewModel.photoId.value)
        Assert.assertNull(photoDetailViewModel.photo.value)
    }
}