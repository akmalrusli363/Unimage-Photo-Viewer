package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.database.PhotosDao
import com.tilikki.training.unimager.demo.database.RoomDB
import com.tilikki.training.unimager.demo.database.UserDao
import com.tilikki.training.unimager.demo.datasets.NetworkTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.network.interfaces.UnsplashApiInterface
import com.tilikki.training.unimager.demo.repositories.response.PhotoList
import com.tilikki.training.unimager.demo.testUtility.RxSchedulerRule
import com.tilikki.training.unimager.demo.util.asDatabaseEntityPhotos
import com.tilikki.training.unimager.demo.util.asDomainEntityPhotos
import io.reactivex.Observable
import io.reactivex.observers.TestObserver
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner
import retrofit2.Response

@RunWith(MockitoJUnitRunner::class)
class UnsplashRepositoryImplTest {
    @get:Rule
    val rxSchedulerRule = RxSchedulerRule()

    @InjectMocks
    private val unsplashApiInterface: UnsplashApiInterface =
        Mockito.mock(UnsplashApiInterface::class.java)

    @InjectMocks
    private val roomDB: RoomDB = Mockito.mock(RoomDB::class.java)

    @InjectMocks
    private val unsplashRepository: UnsplashRepository =
        UnsplashRepositoryImpl(unsplashApiInterface, roomDB)

    private val realPhotosDao = Mockito.mock(PhotosDao::class.java)
    private val realUserDao = Mockito.mock(UserDao::class.java)

    @Before
    fun setup() {
        Mockito.`when`(roomDB.photosDao).thenReturn(realPhotosDao)
        Mockito.`when`(roomDB.userDao).thenReturn(realUserDao)
    }

    @After
    fun teardown() {
        Mockito.verifyNoMoreInteractions(unsplashApiInterface)
        Mockito.verifyNoMoreInteractions(realPhotosDao)
        Mockito.verifyNoMoreInteractions(realUserDao)
    }

    @Test
    fun testGetPhotos_successOnline() {
        val searchQuery = "search"
        val photoList = NetworkTestDataSet.generateSamplePhotoDataList()
        val response = Observable.just(Response.success(PhotoList(10, 1, photoList)))
        Mockito.`when`(unsplashApiInterface.getPhotos(searchQuery))
            .thenReturn(response)

        val returnedValue = unsplashRepository.getPhotos(searchQuery)
        val testObserver = TestObserver<List<Photo>>()
        returnedValue.subscribeWith(testObserver)
            .assertValue(photoList.asDomainEntityPhotos())

        Mockito.verify(unsplashApiInterface).getPhotos(searchQuery)
        Mockito.verify(roomDB.photosDao).insertAll(photoList.asDatabaseEntityPhotos())
        Mockito.verify(roomDB.photosDao).insertSearchResults(Mockito.anyList())
        Mockito.verify(roomDB.userDao, Mockito.times(10))
            .insertUser(photoList[0].user.toDatabaseEntityUser())
        Mockito.verify(roomDB.photosDao, Mockito.never()).getPhotoSearchResult(searchQuery)
    }

    @Test
    fun testGetPhotoDetail_successOnline() {
        val photoId = TestDataConstants.DEMO_PHOTO_ID
        val photo = NetworkTestDataSet.generateSamplePhotoData(photoId)
        Mockito.`when`(unsplashApiInterface.getPhotoDetail(photoId))
            .thenReturn(Observable.just(photo))

        val photoDetail = photo.toDomainEntityPhotoDetail()
        val returnedValue = unsplashRepository.getPhotoDetail(photoId)
        val testObserver = TestObserver<PhotoDetail>()
        returnedValue.subscribeWith(testObserver)
            .assertValue(photoDetail)

        Mockito.verify(unsplashApiInterface).getPhotoDetail(photoId)
        Mockito.verify(roomDB.photosDao, Mockito.never()).getPhotoDetailById(photoId)
    }

    @Test
    fun testGetUserProfile_successOnline() {
        val username = TestDataConstants.DEMO_USERNAME
        val userProfile = NetworkTestDataSet.generateSampleUserData()
        Mockito.`when`(unsplashApiInterface.getUserProfile(username))
            .thenReturn(Observable.just(userProfile))

        val returnedValue = unsplashRepository.getUserProfile(username)
        val testObserver = TestObserver<User>()
        returnedValue.subscribeWith(testObserver)
            .assertValue(userProfile.toDomainEntityUser())

        Mockito.verify(unsplashApiInterface).getUserProfile(username)
        Mockito.verify(roomDB.userDao).insertUser(userProfile.toDatabaseEntityUser())
        Mockito.verify(roomDB.userDao, Mockito.never()).getUserByUsername(username)
    }

    @Test
    fun testGetUserPhotos_successOnline() {
        val username = TestDataConstants.DEMO_USERNAME
        val userPhotos = NetworkTestDataSet.generateSamplePhotoDataList()
        Mockito.`when`(unsplashApiInterface.getUserPhotos(username))
            .thenReturn(Observable.just(userPhotos))

        val returnedValue = unsplashRepository.getUserPhotos(username)
        val testObserver = TestObserver<List<Photo>>()
        returnedValue.subscribeWith(testObserver)
            .assertValue(userPhotos.asDomainEntityPhotos())

        Mockito.verify(unsplashApiInterface).getUserPhotos(username)
        Mockito.verify(roomDB.photosDao).insertAll(userPhotos.asDatabaseEntityPhotos())
        Mockito.verify(roomDB.userDao, Mockito.never()).getUserPhotosByUsername(username)
    }

}