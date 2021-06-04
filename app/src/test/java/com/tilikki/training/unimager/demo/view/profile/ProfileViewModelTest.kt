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

    @Before
    override fun setup() {
        profileViewModel.userPhotos.observeForever(photoObserver)
        profileViewModel.userProfile.observeForever(userObserver)
        profileViewModel.successResponse.observeForever(fetchStatusObserver)
    }

    @Test
    fun fetchUserProfile_fetchSuccess() {
        val user = TestDataConstants.DEMO_USERNAME
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
        val user = TestDataConstants.DEMO_USERNAME
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
}