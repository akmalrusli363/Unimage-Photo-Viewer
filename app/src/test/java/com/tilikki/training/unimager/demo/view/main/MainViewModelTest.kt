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
    private val searchQuery = "search"

    @Before
    override fun setup() {
        mainViewModel.photoList.observeForever(photoObserver)
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
        Assert.assertEquals(photoList, mainViewModel.photoList.value)
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
        Assert.assertNull(mainViewModel.photoList.value)
    }

    @Test
    fun fetchPhotos_fetchEmpty() {
        val photoList = listOf<Photo>()
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery))
            .thenReturn(Observable.just(photoList))

        mainViewModel.fetchPhotos(searchQuery)

        Assert.assertTrue(mainViewModel.successResponse.value!!.success)
        Assert.assertEquals(photoList, mainViewModel.photoList.value)
    }

    @Test
    fun fetchPhotos_addMorePage_success() {
        val photoList = generateSamplePhotoDataList(60)
        val partedPhotoList = photoList.chunked(30)
        runAndValidateAddedPhotoListPage(partedPhotoList, photoList)
    }

    @Test
    fun fetchPhotos_addMorePage_mixedResult() {
        val photoList = generateSamplePhotoDataList(45)
        val partedPhotoList = photoList.chunked(15)
        val mergedPhotoListPart = mutableListOf<List<Photo>>().apply {
            add(partedPhotoList[0] + partedPhotoList[1])
            add(partedPhotoList[1] + partedPhotoList[2])
        }
        runAndValidateAddedPhotoListPage(mergedPhotoListPart, photoList)
    }

    @Test
    fun fetchPhotos_addMorePage_addedComprehensive() {
        val photoList = generateSamplePhotoDataList(45)
        val partedPhotoList = photoList.chunked(15)
        val mergedPhotoListPart = mutableListOf<List<Photo>>().apply {
            add(partedPhotoList[0] + partedPhotoList[1])
            add(photoList)
        }
        runAndValidateAddedPhotoListPage(mergedPhotoListPart, photoList)
    }

    @Test
    fun fetchPhotos_addMorePage_tooFewData() {
        val photoList = generateSamplePhotoDataList(35)
        val partedPhotoList = photoList.chunked(20)
        runAndValidateAddedPhotoListPage(partedPhotoList, photoList, false)
    }

    @Test
    fun fetchPhotos_addMorePage_tooManyData() {
        val photoList = generateSamplePhotoDataList(60)
        val partedPhotoList = photoList.chunked(35)
        runAndValidateAddedPhotoListPage(partedPhotoList, photoList, false)
    }

    @Test
    fun fetchPhotos_addMorePage_noData() {
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery))
            .thenReturn(Observable.error(error))

        mainViewModel.fetchPhotos(searchQuery)
        Mockito.verify(unsplashRepository).getPhotos(searchQuery)
        validateError(1, error)

        mainViewModel.addMorePhotos()
        Mockito.verify(unsplashRepository, Mockito.never()).getPhotos(searchQuery, 2)
    }

    @Test
    fun fetchPhotos_addMorePage_successMultiPage() {
        val photoList = generateSamplePhotoDataList(130)
        val partedPhotoList = photoList.chunked(30)
        partedPhotoList.forEachIndexed { index, list ->
            Mockito.`when`(unsplashRepository.getPhotos(searchQuery, index + 1))
                .thenReturn(Observable.just(list))
        }

        mainViewModel.fetchPhotos(searchQuery)
        Mockito.verify(unsplashRepository).getPhotos(searchQuery)
        validateResponse(1, partedPhotoList[0], true)

        for (index in 2..5) {
            mainViewModel.addMorePhotos()
            Mockito.verify(unsplashRepository).getPhotos(searchQuery, index)
        }
        validateResponse(5, photoList, false)
    }

    private fun runAndValidateAddedPhotoListPage(
        photoListSet: List<List<Photo>>,
        photoList: List<Photo>,
        mustBeAdded: Boolean = true
    ) {
        val verificationMode = if (mustBeAdded) Mockito.times(1) else Mockito.never()
        setupSearchPaginationMock(photoListSet)

        mainViewModel.fetchPhotos(searchQuery)
        Mockito.verify(unsplashRepository).getPhotos(searchQuery)
        validateResponse(1, photoListSet[0], true)

        mainViewModel.addMorePhotos()
        Mockito.verify(unsplashRepository, verificationMode).getPhotos(searchQuery, 2)
        if (mustBeAdded) {
            validateResponse(2, photoList, false)
        }
    }

    private fun setupSearchPaginationMock(photoListSet: List<List<Photo>>) {
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery))
            .thenReturn(Observable.just(photoListSet[0]))
        Mockito.`when`(unsplashRepository.getPhotos(searchQuery, 2))
            .thenReturn(Observable.just(photoListSet[1]))
    }

    private fun validateResponse(
        expectedPage: Int,
        expectedPhotoList: List<Photo>,
        newData: Boolean
    ) {
        Assert.assertTrue(mainViewModel.successResponse.value!!.success)
        Assert.assertEquals(newData, mainViewModel.updateFragment.value)
        Assert.assertEquals(expectedPage, mainViewModel.pages.value!!.page)
        Assert.assertEquals(expectedPhotoList.size, mainViewModel.photoList.value!!.size)
        Assert.assertEquals(expectedPhotoList, mainViewModel.photoList.value)
    }

    private fun validateError(
        expectedPage: Int,
        expectedError: Exception
    ) {
        Assert.assertFalse(mainViewModel.successResponse.value!!.success)
        Assert.assertEquals(expectedError, mainViewModel.successResponse.value!!.error)
        Assert.assertEquals(expectedPage, mainViewModel.pages.value!!.page)
        Assert.assertNull(mainViewModel.photoList.value)
    }

    private fun generateSamplePhotoDataList(numOfData: Int): List<Photo> {
        return EntityTestDataSet.generateSamplePhotoDataList("demo", numOfData)
    }
}
