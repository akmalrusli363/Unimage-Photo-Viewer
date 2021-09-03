package com.tilikki.training.unimager.demo.repositories

import com.tilikki.training.unimager.demo.datasets.NetworkTestDataSet
import com.tilikki.training.unimager.demo.datasets.TestDataConstants
import com.tilikki.training.unimager.demo.model.Photo
import com.tilikki.training.unimager.demo.model.PhotoDetail
import com.tilikki.training.unimager.demo.model.User
import com.tilikki.training.unimager.demo.util.asDomainEntityPhotos
import io.reactivex.Observable
import javax.inject.Inject

open class FakeUnsplashRepository @Inject constructor() : UnsplashRepository {
    private var repositoryStatus = FakeRepositoryStatus.FETCH_SUCCESS

    fun setRepositoryStatus(status: FakeRepositoryStatus) {
        repositoryStatus = status
    }

    override fun getPhotos(query: String): Observable<List<Photo>> {
        return returnListOrEmptyOrError(NullPointerException()) {
            val photoList = NetworkTestDataSet.generateSamplePhotoDataList(query)
            Observable.just(photoList.asDomainEntityPhotos())
        }
    }

    override fun getPhotoDetail(photoId: String): Observable<PhotoDetail> {
        return getPhotoDetail(photoId, TestDataConstants.DEMO_USERNAME)
    }

    fun getPhotoDetail(photoId: String, username: String): Observable<PhotoDetail> {
        return returnDataOrError {
            val photo = NetworkTestDataSet.generateSamplePhotoData(photoId, username)
            Observable.just(photo.toDomainEntityPhotoDetail())
        }
    }

    override fun getUserProfile(username: String): Observable<User> {
        return returnDataOrError {
            val userData = if (username == TestDataConstants.DEMO_USERNAME_NO_PHOTO)
                NetworkTestDataSet.generateNewUserData(username)
            else NetworkTestDataSet.generateSampleUserData(username)
            Observable.just(userData.toDomainEntityUser())
        }
    }

    override fun getUserPhotos(username: String): Observable<List<Photo>> {
        return getUserPhotos(username, TestDataConstants.DEMO_USER_TOTAL_PHOTOS)
    }

    fun getUserPhotos(username: String, numOfPhotos: Int): Observable<List<Photo>> {
        return returnListOrEmptyOrError {
            val userPhotos = NetworkTestDataSet.generateSamplePhotoDataList(
                photoId = username,
                numOfData = numOfPhotos,
                username = username
            )
            Observable.just(userPhotos.asDomainEntityPhotos())
        }
    }

    private fun <T : Any> returnListOrEmptyOrError(
        error: Exception = Exception(),
        successData: () -> Observable<List<T>>
    ): Observable<List<T>> {
        return when (repositoryStatus) {
            FakeRepositoryStatus.FETCH_ERROR -> Observable.error(error)
            FakeRepositoryStatus.FETCH_EMPTY -> Observable.just(emptyList())
            else -> successData()
        }
    }

    private fun <T : Any> returnDataOrError(
        error: Exception = Exception(),
        emptyException: Exception = NullPointerException(),
        successData: () -> Observable<T>
    ): Observable<T> {
        return when (repositoryStatus) {
            FakeRepositoryStatus.FETCH_ERROR -> Observable.error(error)
            FakeRepositoryStatus.FETCH_EMPTY -> Observable.error(emptyException)
            else -> successData()
        }
    }
}
