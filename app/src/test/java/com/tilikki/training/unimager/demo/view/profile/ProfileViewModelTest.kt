package com.tilikki.training.unimager.demo.view.profile

import androidx.lifecycle.Observer
import com.tilikki.training.unimager.demo.datasets.EntityTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.User
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
class ProfileViewModelTest : GenericViewModelTest() {
    @Mock
    private lateinit var photoObserver: Observer<in List<Photo>?>

    @Mock
    private lateinit var userObserver: Observer<User>

    @Mock
    private lateinit var fetchStatusObserver: Observer<FetchResponse>

    private val profileViewModel = ProfileViewModel(unsplashRepository)
    private val user = TestDataConstants.DEMO_USERNAME

    @Before
    override fun setup() {
        profileViewModel.userPhotos.observeForever(photoObserver)
        profileViewModel.userProfile.observeForever(userObserver)
        profileViewModel.successResponse.observeForever(fetchStatusObserver)
    }

    @Test
    fun fetchUserProfile_fetchSuccess() {
        val userProfile = EntityTestDataSet.generateSampleUserData()
        val userPhoto = EntityTestDataSet.generateSamplePhotoDataList()
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.just(userProfile))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.just(userPhoto))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)
        Mockito.verify(unsplashRepository).getUserPhotos(user)

        Assert.assertTrue(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(userProfile, profileViewModel.userProfile.value)
        Assert.assertEquals(userPhoto, profileViewModel.userPhotos.value)
    }

    @Test
    fun fetchUserProfile_fetchPartialError() {
        val userProfile = EntityTestDataSet.generateSampleUserData()
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.just(userProfile))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.error(error))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)

        Assert.assertFalse(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(error, profileViewModel.successResponse.value!!.error)
        Assert.assertNull(profileViewModel.userProfile.value)
        Assert.assertNull(profileViewModel.userPhotos.value)
    }

    @Test
    fun fetchUserProfile_fetchError() {
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.error(error))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.error(error))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)

        Assert.assertFalse(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(error, profileViewModel.successResponse.value!!.error)
        Assert.assertNull(profileViewModel.userProfile.value)
        Assert.assertNull(profileViewModel.userPhotos.value)
    }

    @Test
    fun fetchUserProfile_fetchEmptyPhotos() {
        val userProfile = EntityTestDataSet.generateSampleUserData()
        val userPhoto = emptyList<Photo>()
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.just(userProfile))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.just(userPhoto))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)

        Assert.assertTrue(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(userProfile, profileViewModel.userProfile.value)
        Assert.assertEquals(userPhoto, profileViewModel.userPhotos.value)
    }

    @Test
    fun fetchUserProfile_addMorePage_success() {
        val photoList = generateSamplePhotoDataList(60)
        val partedPhotoList = photoList.chunked(30)
        runAndValidateAddedPhotoListPage(partedPhotoList, photoList)
    }

    @Test
    fun fetchUserProfile_addMorePage_mixedResult() {
        val photoList = generateSamplePhotoDataList(45)
        val partedPhotoList = photoList.chunked(15)
        val mergedPhotoListPart = mutableListOf<List<Photo>>().apply {
            add(partedPhotoList[0] + partedPhotoList[1])
            add(partedPhotoList[1] + partedPhotoList[2])
        }
        runAndValidateAddedPhotoListPage(mergedPhotoListPart, photoList)
    }

    @Test
    fun fetchUserProfile_addMorePage_addedComprehensive() {
        val photoList = generateSamplePhotoDataList(45)
        val partedPhotoList = photoList.chunked(15)
        val mergedPhotoListPart = mutableListOf<List<Photo>>().apply {
            add(partedPhotoList[0] + partedPhotoList[1])
            add(photoList)
        }
        runAndValidateAddedPhotoListPage(mergedPhotoListPart, photoList)
    }

    @Test
    fun fetchUserProfile_addMorePage_tooFewData() {
        val photoList = generateSamplePhotoDataList(35)
        val partedPhotoList = photoList.chunked(20)
        runAndValidateAddedPhotoListPage(partedPhotoList, photoList, false)
    }

    @Test
    fun fetchUserProfile_addMorePage_tooManyData() {
        val photoList = generateSamplePhotoDataList(60)
        val partedPhotoList = photoList.chunked(35)
        runAndValidateAddedPhotoListPage(partedPhotoList, photoList, false)
    }

    @Test
    fun fetchUserProfile_addMorePage_noUserPhotoData() {
        val userProfile = EntityTestDataSet.generateSampleUserData()
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.just(userProfile))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.error(error))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)
        Mockito.verify(unsplashRepository).getUserPhotos(user)

        Assert.assertFalse(profileViewModel.successResponse.value!!.success)
        Assert.assertNull(profileViewModel.userProfile.value)
        validateError(1, error)

        profileViewModel.addMorePhotos(user)
        Mockito.verify(unsplashRepository, Mockito.never()).getUserPhotos(user, 2)
        validateError(2, error)
    }

    @Test
    fun fetchUserProfile_addMorePage_fetchError() {
        val error = NullPointerException()
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.error(error))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.error(error))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)
        Mockito.verify(unsplashRepository).getUserPhotos(user)

        Assert.assertFalse(profileViewModel.successResponse.value!!.success)
        Assert.assertNull(profileViewModel.userProfile.value)
        validateError(1, error)

        profileViewModel.addMorePhotos(user)
        Mockito.verify(unsplashRepository, Mockito.never()).getUserPhotos(user, 2)
        validateError(2, error)
    }

    @Test
    fun fetchUserProfile_addMorePage_successMultiPage() {
        val userProfile = EntityTestDataSet.generateSampleUserData()
        val photoList = generateSamplePhotoDataList(130)
        val partedPhotoList = photoList.chunked(30)

        partedPhotoList.forEachIndexed { index, list ->
            Mockito.`when`(unsplashRepository.getUserPhotos(user, index + 1))
                .thenReturn(Observable.just(list))
        }
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.just(userProfile))

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)
        Mockito.verify(unsplashRepository).getUserPhotos(user)

        Assert.assertTrue(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(userProfile, profileViewModel.userProfile.value)
        validateResponse(1, partedPhotoList[0], true)

        for (index in 2..5) {
            profileViewModel.addMorePhotos(user)
            Mockito.verify(unsplashRepository).getUserPhotos(user, index)
        }
        validateResponse(5, photoList, false)
    }

    private fun runAndValidateAddedPhotoListPage(
        photoListSet: List<List<Photo>>,
        photoList: List<Photo>,
        mustBeAdded: Boolean = true
    ) {
        val userProfile = EntityTestDataSet.generateSampleUserData()
        val verificationMode = if (mustBeAdded) Mockito.times(1) else Mockito.never()
        setupUserPhotoPaginationMock(userProfile, photoListSet)

        profileViewModel.fetchUserProfile(user)
        Mockito.verify(unsplashRepository).getUserProfile(user)
        Mockito.verify(unsplashRepository).getUserPhotos(user)

        Assert.assertTrue(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(userProfile, profileViewModel.userProfile.value)
        validateResponse(1, photoListSet[0], true)

        profileViewModel.addMorePhotos(user)
        Mockito.verify(unsplashRepository, verificationMode).getUserPhotos(user, 2)
        if (mustBeAdded) {
            validateResponse(2, photoList, false)
        }
    }

    private fun setupUserPhotoPaginationMock(
        userProfile: User,
        photoListSet: List<List<Photo>>
    ) {
        Mockito.`when`(unsplashRepository.getUserProfile(user))
            .thenReturn(Observable.just(userProfile))
        Mockito.`when`(unsplashRepository.getUserPhotos(user))
            .thenReturn(Observable.just(photoListSet[0]))
        Mockito.`when`(unsplashRepository.getUserPhotos(user, 2))
            .thenReturn(Observable.just(photoListSet[1]))
    }

    private fun validateResponse(
        expectedPage: Int,
        expectedPhotoList: List<Photo>,
        newData: Boolean
    ) {
        Assert.assertTrue(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(newData, profileViewModel.updateFragment.value)
        Assert.assertEquals(expectedPage, profileViewModel.pages.value!!.page)
        Assert.assertEquals(expectedPhotoList.size, profileViewModel.userPhotos.value!!.size)
        Assert.assertEquals(expectedPhotoList, profileViewModel.userPhotos.value)
    }

    private fun validateError(
        expectedPage: Int,
        expectedError: Exception
    ) {
        Assert.assertFalse(profileViewModel.successResponse.value!!.success)
        Assert.assertEquals(expectedError, profileViewModel.successResponse.value!!.error)
        Assert.assertEquals(expectedPage, profileViewModel.pages.value!!.page)
        Assert.assertNull(profileViewModel.userPhotos.value)
    }

    private fun generateSamplePhotoDataList(numOfData: Int): List<Photo> {
        return EntityTestDataSet.generateSamplePhotoDataList("demo", numOfData)
    }
}
