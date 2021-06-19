package com.tilikki.training.unimager.demo.view.photogrid

import androidx.lifecycle.Observer
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.view.base.GenericViewModelTest
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PhotoGridViewModelTest : GenericViewModelTest() {
    @Mock
    private lateinit var photoObserver: Observer<in List<Photo>?>

    private val photoGridViewModel = PhotoGridViewModel()

    @Before
    override fun setup() {
        photoGridViewModel.photos.observeForever(photoObserver)
    }

    @Test
    fun postPhotos_success() {
        val photos = EntityTestDataSet.generateSamplePhotoDataList()
        photoGridViewModel.postPhotos(photos)

        Assert.assertEquals(photos, photoGridViewModel.photos.value)
    }
}