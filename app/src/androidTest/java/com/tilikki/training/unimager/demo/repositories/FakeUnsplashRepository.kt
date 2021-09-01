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
    private var shouldReturnError = false

    fun setReturnError(value: Boolean) {
        shouldReturnError = value
    }

    override fun getPhotos(query: String): Observable<List<Photo>> {
        if (shouldReturnError) {
            return Observable.just(emptyList())
        }
        val photoList = NetworkTestDataSet.generateSamplePhotoDataList(query)
        return Observable.just(photoList.asDomainEntityPhotos())
    }

    override fun getPhotoDetail(photoId: String): Observable<PhotoDetail> {
        return getPhotoDetail(photoId, TestDataConstants.DEMO_USERNAME)
    }

    fun getPhotoDetail(photoId: String, username: String): Observable<PhotoDetail> {
        if (shouldReturnError) {
            return Observable.empty()
        }
        val photo = NetworkTestDataSet.generateSamplePhotoData(photoId, username)
        return Observable.just(photo.toDomainEntityPhotoDetail())
    }

    override fun getUserProfile(username: String): Observable<User> {
        if (shouldReturnError) {
            return Observable.empty()
        }
        val userData = NetworkTestDataSet.generateSampleUserData(username)
        return Observable.just(userData.toDomainEntityUser())
    }

    override fun getUserPhotos(username: String): Observable<List<Photo>> {
        if (shouldReturnError) {
            return Observable.empty()
        }
        val userPhotos = NetworkTestDataSet.generateSamplePhotoDataList(username)
        return Observable.just(userPhotos.asDomainEntityPhotos())
    }
}
